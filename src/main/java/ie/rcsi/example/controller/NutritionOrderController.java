package ie.rcsi.example.controller;

import ie.rcsi.example.client.HapiFhirClient;
import lombok.extern.slf4j.Slf4j;
import org.hl7.fhir.r4.model.NutritionOrder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@Slf4j
public class NutritionOrderController {

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
}
