package ie.rcsi.example.service;

import ie.rcsi.example.client.HapiFhirClient;
import ie.rcsi.example.dto.ObservationDetails;
import lombok.extern.slf4j.Slf4j;
import org.hl7.fhir.r5.model.Bundle;
import org.hl7.fhir.r5.model.Observation;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class ObservationMapperService implements ObservationMapper {

    @NotNull
    @Override
    public List<ObservationDetails> mapBundleToObservationDetails(Bundle bundle, boolean isAll) {
        List<ObservationDetails> observationDetailsList = new ArrayList<>();
        // Iterate through the results and extract OBX-5 (Observation Value)
        for (Bundle.BundleEntryComponent entry : bundle.getEntry()) {
            Observation observation = (Observation) entry.getResource();
            // Extract the value
            if (observation.hasValue()) {
                String observationId = observation.getIdElement().getIdPart();
                BigDecimal bmi = observation.getValueQuantity().getValue();
                String patientId = observation.getSubject().getReference();
                if (bmi != null && bmi.compareTo(BigDecimal.valueOf(30.0)) > 0) {
                    log.info("Patient is classified as obese. {}, Obs.={}, BMI={}",
                            observation.getSubject().getReference(), observationId, bmi);
                    String string = HapiFhirClient.getFhirContext().newJsonParser().setPrettyPrint(true).encodeResourceToString(observation);
                    log.info("Observation Value (OBX-5): {}", string);
                    if (!isAll) {
                        observationDetailsList.add(new ObservationDetails(bmi.doubleValue(), patientId, observationId));
                    }
                } else {
                    log.info("Patient is NOT classified as obese. {}, Obs.={}",
                            observation.getSubject().getReference(), observationId);
                }
                if (isAll) {
                    observationDetailsList.add(new ObservationDetails(bmi != null ? bmi.doubleValue() : 0.0, patientId, observationId));
                }
            }
        }
        return observationDetailsList;
    }
}
