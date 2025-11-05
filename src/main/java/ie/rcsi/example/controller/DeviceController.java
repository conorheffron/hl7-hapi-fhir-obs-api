package ie.rcsi.example.controller;

import ie.rcsi.example.client.HapiFhirClient;
import lombok.extern.slf4j.Slf4j;
import org.hl7.fhir.r4.model.Device;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Slf4j
public class DeviceController {

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
}
