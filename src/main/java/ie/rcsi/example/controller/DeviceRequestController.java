package ie.rcsi.example.controller;

import ie.rcsi.example.client.HapiFhirClient;
import lombok.extern.slf4j.Slf4j;
import org.hl7.fhir.r4.model.DeviceRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Slf4j
public class DeviceRequestController {

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
}
