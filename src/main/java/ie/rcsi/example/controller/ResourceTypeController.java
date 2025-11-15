package ie.rcsi.example.controller;

import ie.rcsi.example.client.HapiFhirClient;
import lombok.extern.slf4j.Slf4j;
import org.hl7.fhir.r4b.model.CarePlan;
import org.hl7.fhir.r4b.model.Device;
import org.hl7.fhir.r4b.model.DeviceRequest;
import org.hl7.fhir.r4b.model.DiagnosticReport;
import org.hl7.fhir.r4b.model.NutritionOrder;
import org.hl7.fhir.r4b.model.Patient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(value = "/api")
public class ResourceTypeController {

    @GetMapping(value = "/care/plan")
    public ResponseEntity<String> getCarePlanByResourceId(@RequestParam(name = "resourceId") String resourceId) {
        // Read a care plan with the given ID
        CarePlan carePlan = HapiFhirClient.getInstance().read().resource(CarePlan.class).withId(resourceId).execute();
        String carePlanStr = HapiFhirClient.getFhirContext().newJsonParser().setPrettyPrint(true).encodeResourceToString(carePlan);
        log.info("Care Plan Data Plan: {}", carePlanStr);
        return ResponseEntity.ok()
                .body(carePlanStr);
    }

    @GetMapping(value = "/device")
    public ResponseEntity<String> getDeviceByResourceId(@RequestParam(name = "resourceId") String resourceId) {
        // Read a device with the given ID
        Device device = HapiFhirClient.getInstance().read().resource(Device.class).withId(resourceId).execute();
        String deviceStr = HapiFhirClient.getFhirContext().newJsonParser().setPrettyPrint(true).encodeResourceToString(device);
        log.info("Device: {}", deviceStr);
        return ResponseEntity.ok()
                .body(deviceStr);
    }

    @GetMapping(value = "/device/request")
    public ResponseEntity<String> getDeviceRequestByResourceId(@RequestParam(name = "resourceId") String resourceId) {
        // Read a device request with the given ID
        DeviceRequest deviceRequest = HapiFhirClient.getInstance().read().resource(DeviceRequest.class).withId(resourceId).execute();
        String deviceRequestStr = HapiFhirClient.getFhirContext().newJsonParser().setPrettyPrint(true).encodeResourceToString(deviceRequest);
        log.info("Device Request: {}", deviceRequestStr);
        return ResponseEntity.ok()
                .body(deviceRequestStr);
    }

    @GetMapping(value = "/diagnostic/report")
    public ResponseEntity<String> getDiagnosticReportByResourceId(@RequestParam(name = "resourceId") String resourceId) {
        // Read a patient obj with the given ID
        DiagnosticReport diagnosticReport = HapiFhirClient.getInstance().read().resource(DiagnosticReport.class).withId(resourceId).execute();
        String diagnosticReportStr = HapiFhirClient.getFhirContext().newJsonParser().setPrettyPrint(true).encodeResourceToString(diagnosticReport);
        log.info("DiagnosticReport: {}", diagnosticReportStr);
        return ResponseEntity.ok()
                .body(diagnosticReportStr);
    }

    @GetMapping(value = "/nutrition/order")
    public ResponseEntity<String> getNutritionOrderByResourceId(@RequestParam(name = "resourceId") String resourceId) {
        // Read a patient obj with the given ID
        NutritionOrder nutritionOrder = HapiFhirClient.getInstance().read().resource(NutritionOrder.class).withId(resourceId).execute();
        String nutritionOrderStr = HapiFhirClient.getFhirContext().newJsonParser().setPrettyPrint(true).encodeResourceToString(nutritionOrder);
        log.info("Nutrition Order: {}", nutritionOrderStr);
        return ResponseEntity.ok()
                .body(nutritionOrderStr);
    }

    @GetMapping(value = "/patient")
    public ResponseEntity<String> getPatientByResourceId(@RequestParam(name = "resourceId") String resourceId) {
        // Read a patient obj with the given ID
        Patient patient = HapiFhirClient.getInstance().read().resource(Patient.class).withId(resourceId).execute();
        String patientStr = HapiFhirClient.getFhirContext().newJsonParser().setPrettyPrint(true).encodeResourceToString(patient);
        log.info("Patient: {}", patientStr);
        return ResponseEntity.ok()
                .body(patientStr);
    }
}
