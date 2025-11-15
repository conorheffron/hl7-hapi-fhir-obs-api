package ie.rcsi.example.controller;

import ie.rcsi.example.client.HapiFhirClient;
import lombok.extern.slf4j.Slf4j;
import org.hl7.fhir.r4.model.CarePlan;
import org.hl7.fhir.r4.model.Device;
import org.hl7.fhir.r4.model.DeviceRequest;
import org.hl7.fhir.r4.model.DiagnosticReport;
import org.hl7.fhir.r4.model.NutritionOrder;
import org.hl7.fhir.r4.model.Patient;
import org.springframework.stereotype.Controller;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@Slf4j
public class ResourceTypeController {

    @GetMapping(value = "/api/care/plan")
    public ResponseEntity<String> getCarePlan() {
        // Read a care plan with the given ID
        CarePlan carePlan = HapiFhirClient.getInstance().read().resource(CarePlan.class).withId("1566160").execute();
        String carePlanStr = HapiFhirClient.getFhirContext().newJsonParser().setPrettyPrint(true).encodeResourceToString(carePlan);
        log.info("Care Plan Data Plan: {}", carePlanStr);
        return ResponseEntity.ok()
                .header("Content-Type", "application/json")
                .body(carePlanStr);
    }

    @GetMapping(value = "/api/device")
    public ResponseEntity<String> getDevice() {
        // Read a device with the given ID
        Device device = HapiFhirClient.getInstance().read().resource(Device.class).withId("581517").execute();
        String deviceStr = HapiFhirClient.getFhirContext().newJsonParser().setPrettyPrint(true).encodeResourceToString(device);
        log.info("Device: {}", deviceStr);
        return ResponseEntity.ok()
                .header("Content-Type", "application/json")
                .body(deviceStr);
    }

    @GetMapping(value = "/api/device/request")
    public ResponseEntity<String> getDeviceRequest() {
        // Read a device request with the given ID
        DeviceRequest deviceRequest = HapiFhirClient.getInstance().read().resource(DeviceRequest.class).withId("1723173").execute();
        String deviceRequestStr = HapiFhirClient.getFhirContext().newJsonParser().setPrettyPrint(true).encodeResourceToString(deviceRequest);
        log.info("Device Request: {}", deviceRequestStr);
        return ResponseEntity.ok()
                .header("Content-Type", "application/json")
                .body(deviceRequestStr);
    }

    @RequestMapping(value = "/api/diagnostic/report", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<String> getDiagnosticReportByResourceId(@RequestParam(name = "resourceId") String resourceId) {
        // Read a patient obj with the given ID
        DiagnosticReport diagnosticReport = HapiFhirClient.getInstance().read().resource(DiagnosticReport.class).withId(resourceId).execute();
        String diagnosticReportStr = HapiFhirClient.getFhirContext().newJsonParser().setPrettyPrint(true).encodeResourceToString(diagnosticReport);
        log.info("DiagnosticReport: {}", diagnosticReportStr);
        return ResponseEntity.ok()
                .header("Content-Type", "application/json")
                .body(diagnosticReportStr);
    }

    @RequestMapping(value = "/api/nutrition/order", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<String> getNutritionOrderByResourceId(@RequestParam(name = "resourceId") String resourceId) {
        // Read a patient obj with the given ID
        NutritionOrder nutritionOrder = HapiFhirClient.getInstance().read().resource(NutritionOrder.class).withId(resourceId).execute();
        String nutritionOrderStr = HapiFhirClient.getFhirContext().newJsonParser().setPrettyPrint(true).encodeResourceToString(nutritionOrder);
        log.info("Patient: {}", nutritionOrderStr);
        return ResponseEntity.ok()
                .header("Content-Type", "application/json")
                .body(nutritionOrderStr);
    }

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
