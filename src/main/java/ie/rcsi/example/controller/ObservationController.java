package ie.rcsi.example.controller;

import module java.base;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.parser.IParser;
import ca.uhn.fhir.rest.gclient.ICriterion;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import ie.rcsi.example.service.ClientWrapperService;
import ie.rcsi.example.service.ObservationMapper;
import lombok.extern.slf4j.Slf4j;
import org.hl7.fhir.r4.model.Bundle;
import org.hl7.fhir.r4.model.Observation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@Slf4j
public class ObservationController {

    private final ObjectMapper objectMapper;

    private final ObservationMapper observationMapper;

    private final ClientWrapperService clientWrapperService;

    public ObservationController(ObjectMapper objectMapper, ObservationMapper observationMapper,
                                 ClientWrapperService clientWrapperService) {
        this.objectMapper = objectMapper;
        this.objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        this.observationMapper = observationMapper;
        this.clientWrapperService = clientWrapperService;
    }

    @GetMapping(value = "/api/observation")
    public ResponseEntity<String> getObservationResourceTypesByCode(@RequestParam(name = "code") String obvTypeCode) {
        // Search Observations with the given code
        ICriterion<?> searchCriteria = Observation.CODE.exactly().codes(obvTypeCode);
        Bundle bundle = clientWrapperService.getClientInstance()
                .search()
                .forResource(Observation.class)
                .returnBundle(Bundle.class)
                .where(searchCriteria)
                .execute();

        try {
            FhirContext ctx = FhirContext.forR4();
            IParser jsonParser = ctx.newJsonParser().setPrettyPrint(true);
            String jsonOutput = jsonParser.encodeResourceToString(bundle);
            return ResponseEntity.ok().body(jsonOutput);
        } catch (Exception ex) {
            log.error("Error while parsing JSON", ex);
            return ResponseEntity
                    .badRequest()
                    .body("An error occurred while processing your request.");
        }
    }

    @GetMapping(value = "/api/obese/observation")
    public ResponseEntity<String> getObservationDetailsByCodeWherePatientObese(@RequestParam(name = "code") String obvTypeCode) {
        // Search for Observations (you can filter by patient, code, etc.)
        ICriterion<?> searchCriteria = Observation.CODE.exactly().codes(obvTypeCode);
        Bundle bundle = clientWrapperService.getClientInstance()
                .search()
                .forResource(Observation.class)
                .returnBundle(Bundle.class)
                .where(searchCriteria) // OBX-5 Segment Data related to Obesity
                .execute();

        try {
            String prettyJson = objectMapper.writerWithDefaultPrettyPrinter()
                    .writeValueAsString(observationMapper.mapBundleToObservationDetails(bundle, false));
            return ResponseEntity.ok()
                    .body(prettyJson);
        } catch (Exception ex) {
            log.error("Error while parsing JSON", ex);
            return ResponseEntity
                    .badRequest()
                    .body("An error occurred while processing your request.");
        }
    }

    @GetMapping(value = "/api/all/observation")
    public ResponseEntity<String> getAllObservationDetailsByCode(@RequestParam(name = "code") String obvTypeCode) {
        // Search for Observations (you can filter by patient, code, etc.)
        ICriterion<?> searchCriteria = Observation.CODE.exactly().codes(obvTypeCode);
        Bundle bundle = clientWrapperService.getClientInstance()
                .search()
                .forResource(Observation.class)
                .returnBundle(Bundle.class)
                .where(searchCriteria)
                .execute();

        try {
            String prettyJson = objectMapper.writerWithDefaultPrettyPrinter()
                    .writeValueAsString(observationMapper.mapBundleToObservationDetails(bundle, true));
            return ResponseEntity.ok()
                    .body(prettyJson);
        } catch (Exception ex) {
            log.error("Error while parsing JSON", ex);
            return ResponseEntity
                    .badRequest()
                    .body("An error occurred while processing your request.");
        }
    }
}
