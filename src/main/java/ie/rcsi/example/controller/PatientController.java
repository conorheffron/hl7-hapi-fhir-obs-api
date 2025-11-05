package ie.rcsi.example.controller;

import ie.rcsi.example.client.HapiFhirClient;
import lombok.extern.slf4j.Slf4j;
import org.hl7.fhir.r4.model.Patient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Slf4j
public class PatientController {

    @GetMapping(value = "/api/patient")
    public ResponseEntity<String> getPatient() {
        // Read a patient obj with the given ID
        Patient patient = HapiFhirClient.getInstance().read().resource(Patient.class).withId("113798").execute();
        String patientStr = HapiFhirClient.getFhirContext().newJsonParser().setPrettyPrint(true).encodeResourceToString(patient);
        log.info("Patient: {}", patientStr);
        return ResponseEntity.ok()
                .header("Content-Type", "application/json")
                .body(patientStr);
    }
}
