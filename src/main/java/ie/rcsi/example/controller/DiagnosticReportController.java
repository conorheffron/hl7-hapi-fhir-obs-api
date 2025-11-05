package ie.rcsi.example.controller;

import ie.rcsi.example.client.HapiFhirClient;
import lombok.extern.slf4j.Slf4j;
import org.hl7.fhir.r4.model.DiagnosticReport;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@Slf4j
public class DiagnosticReportController {

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
}
