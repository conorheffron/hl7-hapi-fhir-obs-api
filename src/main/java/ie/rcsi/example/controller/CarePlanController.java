package ie.rcsi.example.controller;

import ie.rcsi.example.client.HapiFhirClient;
import lombok.extern.slf4j.Slf4j;
import org.hl7.fhir.r4.model.CarePlan;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Slf4j
public class CarePlanController {

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
}
