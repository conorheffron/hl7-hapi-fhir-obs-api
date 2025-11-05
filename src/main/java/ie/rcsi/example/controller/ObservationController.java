package ie.rcsi.example.controller;

import module java.base;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import ie.rcsi.example.client.HapiFhirClient;
import ie.rcsi.example.record.ObservationDetails;
import lombok.extern.slf4j.Slf4j;
import org.hl7.fhir.r4.model.Bundle;
import org.hl7.fhir.r4.model.Observation;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@Slf4j
public class ObservationController {

    private final ObjectMapper objectMapper;

    public ObservationController(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
        this.objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
    }

    @RequestMapping(value = "/api/obese/observation", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<String> getObservationsWherePatientObese(@RequestParam(name = "code") String obvTypeCode) {
        // Search for Observations (you can filter by patient, code, etc.)
        Bundle bundle = HapiFhirClient.getInstance()
                .search()
                .forResource(Observation.class)
                .returnBundle(Bundle.class)
                .where(Observation.CODE.exactly().codes(obvTypeCode)) // OBX-5 Segment Data related to Obesity
                .execute();

        try {
            String prettyJson = objectMapper.writerWithDefaultPrettyPrinter()
                    .writeValueAsString(getObservationDetails(bundle, false));
            return ResponseEntity.ok()
                    .header("Content-Type", "application/json")
                    .body(prettyJson);
        } catch (Exception ex) {
            log.error("Error while parsing JSON", ex);
            return ResponseEntity
                    .badRequest()
                    .body("Error: " + ex.getMessage());
        }
    }

    @RequestMapping(value = "/api/all/observation", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<String> getAllObservations(@RequestParam(name = "code") String obvTypeCode) {
        // Search for Observations (you can filter by patient, code, etc.)
        Bundle bundle = HapiFhirClient.getInstance()
                .search()
                .forResource(Observation.class)
                .returnBundle(Bundle.class)
                .where(Observation.CODE.exactly().codes(obvTypeCode)) // OBX-5 Segment Data related to Obesity
                .execute();

        try {
            String prettyJson = objectMapper.writerWithDefaultPrettyPrinter()
                    .writeValueAsString(getObservationDetails(bundle, true));
            return ResponseEntity.ok()
                    .header("Content-Type", "application/json")
                    .body(prettyJson);
        } catch (Exception ex) {
            log.error("Error while parsing JSON", ex);
            return ResponseEntity
                    .badRequest()
                    .body("Error: " + ex.getMessage());
        }
    }

    @NotNull
    private static List<ObservationDetails> getObservationDetails(Bundle bundle, boolean isAll) {
        List<ObservationDetails> observationDetailsList = new ArrayList<>();
        // Iterate through the results and extract OBX-5 (Observation Value)
        for (Bundle.BundleEntryComponent entry : bundle.getEntry()) {
            Observation observation = (Observation) entry.getResource();

            // Extract the value (OBX-5)
            if (observation.hasValue()) {
                String observationId = observation.getIdElement().getIdPart();
                BigDecimal bmi = observation.getValueQuantity().getValue();
                String patientId = observation.getSubject().getReference();
                if (bmi != null && bmi.compareTo(BigDecimal.valueOf(30.0)) > 0) {
                    log.info("Patient is classified as obese. {}, Obs.={}, BMI={} %n",
                            observation.getSubject().getReference(), observationId, bmi);
                    String string = HapiFhirClient.getFhirContext().newJsonParser().setPrettyPrint(true).encodeResourceToString(observation);
                    log.info("Observation Value (OBX-5): {}", string);
                    if (!isAll) {
                        observationDetailsList.add(new ObservationDetails(bmi.doubleValue(), patientId, observationId));
                    }
                } else {
                    log.info("Patient is NOT classified as obese. {}, Obs.={} %n",
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
