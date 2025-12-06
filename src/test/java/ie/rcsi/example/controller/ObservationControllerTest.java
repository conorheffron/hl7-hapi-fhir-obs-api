package ie.rcsi.example.controller;

import module java.base;

import ca.uhn.fhir.rest.client.api.IGenericClient;
import ca.uhn.fhir.rest.gclient.ICriterion;
import ca.uhn.fhir.rest.gclient.IQuery;
import ca.uhn.fhir.rest.gclient.IUntypedQuery;
import com.fasterxml.jackson.databind.ObjectMapper;
import ie.rcsi.example.dto.ObservationDetails;
import ie.rcsi.example.service.ClientWrapperService;
import ie.rcsi.example.service.ObservationMapper;
import org.hl7.fhir.r4.model.Bundle;
import org.hl7.fhir.r4.model.Observation;
import org.hl7.fhir.r4.model.Coding;
import org.hl7.fhir.r4.model.CodeableConcept;
import org.hl7.fhir.r4.model.Identifier;
import org.hl7.fhir.r4.model.Quantity;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ObservationControllerTest {

    private ObservationController observationController;// controller under test

    @Mock
    private ClientWrapperService clientWrapperService;

    @Mock
    private ObservationMapper observationMapperService;

    @Mock
    private IGenericClient client;

    @Mock
    private Bundle bundle;

    @Mock
    private IUntypedQuery observationIUntypedQuery;

    @Mock
    private IQuery<Bundle> bundleIQueryMock;

    @BeforeEach
    void setUp() {
        observationController = new ObservationController(new ObjectMapper(), observationMapperService, clientWrapperService);
        when(clientWrapperService.getClientInstance()).thenReturn(client);
    }

    @Test
    void getObservationResourceTypesByCode() {
        // Given
        String code = "39156-5";
        Observation observation = createTestObservation(code);
        Bundle bundleResult = new Bundle();
        bundleResult.addEntry().setResource(observation);

        when(client.search()).thenReturn(observationIUntypedQuery);
        when(observationIUntypedQuery.forResource(eq(Observation.class))).thenReturn(bundleIQueryMock);
        when(bundleIQueryMock.where(any(ICriterion.class))).thenReturn(bundleIQueryMock);
        when(bundleIQueryMock.returnBundle(eq(Bundle.class))).thenReturn(bundleIQueryMock);
        when(bundleIQueryMock.execute()).thenReturn(bundleResult);

        // When (Unit / Method Run)
        ResponseEntity<String> response = observationController.getObservationResourceTypesByCode(code);

        // Then Assert
        verify(observationIUntypedQuery).forResource(eq(Observation.class));
        verify(bundleIQueryMock).where(any(ICriterion.class));
        verify(bundleIQueryMock).returnBundle(eq(Bundle.class));
        verify(bundleIQueryMock).execute();
        verify(observationMapperService, never()).mapBundleToObservationDetails(any(Bundle.class), any(Boolean.class));

        assertThat(response.getStatusCode().value()).isEqualTo(200);
        assertThat(response.getBody()).isEqualTo("""
                {
                  "resourceType": "Bundle",
                  "entry": [ {
                    "resource": {
                      "resourceType": "Observation",
                      "identifier": [ {
                        "use": "official",
                        "system": "http://example.com/patient",
                        "value": "Patient/49262"
                      } ],
                      "code": {
                        "coding": [ {
                          "system": "http://loinc.org",
                          "code": "39156-5",
                          "display": "Body mass index (BMI)"
                        } ]
                      },
                      "valueQuantity": {
                        "value": 31.0,
                        "unit": "kg/m2",
                        "system": "http://unitsofmeasure.org",
                        "code": "kg/m2"
                      }
                    }
                  } ]
                }""");
    }

    @Test
    void getObservationDetailsByCodeWherePatientObese() {
        // Given
        List<ObservationDetails> expectedDetails = List.of(
                new ObservationDetails(41.0, "Patient/789", "Obs/22222")
        );
        String code = "75430-9";
        Observation observation = createTestObservation(code);
        Bundle bundleResult = new Bundle();
        bundleResult.addEntry().setResource(observation);

        when(client.search()).thenReturn(observationIUntypedQuery);
        when(observationIUntypedQuery.forResource(eq(Observation.class))).thenReturn(bundleIQueryMock);
        when(bundleIQueryMock.where(any(ICriterion.class))).thenReturn(bundleIQueryMock);
        when(bundleIQueryMock.returnBundle(eq(Bundle.class))).thenReturn(bundleIQueryMock);
        when(bundleIQueryMock.execute()).thenReturn(bundleResult);
        when(observationMapperService.mapBundleToObservationDetails(any(Bundle.class), any(Boolean.class))).thenReturn(expectedDetails);

        // When (Unit / Method Run)
        ResponseEntity<String> response = observationController.getObservationDetailsByCodeWherePatientObese(code);

        // Then Verify / Assert
        verify(observationIUntypedQuery).forResource(eq(Observation.class));
        verify(bundleIQueryMock).where(any(ICriterion.class));
        verify(bundleIQueryMock).returnBundle(eq(Bundle.class));
        verify(bundleIQueryMock).execute();
        verify(observationMapperService).mapBundleToObservationDetails(any(Bundle.class), any(Boolean.class));

        assertThat(response.getStatusCode().value()).isEqualTo(200);
        assertThat(response.getBody()).isEqualTo("""
                                                                      [ {
                                                                        "bmi" : 41.0,
                                                                        "patientId" : "Patient/789",
                                                                        "observationId" : "Obs/22222"
                                                                      } ]""");
    }

    @Test
    void getAllObservationDetailsByCode() {
        // Given
        List<ObservationDetails> expectedDetails = List.of(
                new ObservationDetails(31.0, "Patient/123", "Observation/49262"),
                new ObservationDetails(25.0, "Patient/456", "Obs/2")
        );
        String code = "39156-5";
        Observation observation = createTestObservation(code);
        Bundle bundleResult = new Bundle();
        bundleResult.addEntry().setResource(observation);

        when(client.search()).thenReturn(observationIUntypedQuery);
        when(observationIUntypedQuery.forResource(eq(Observation.class))).thenReturn(bundleIQueryMock);
        when(bundleIQueryMock.where(any(ICriterion.class))).thenReturn(bundleIQueryMock);
        when(bundleIQueryMock.returnBundle(eq(Bundle.class))).thenReturn(bundleIQueryMock);
        when(bundleIQueryMock.execute()).thenReturn(bundleResult);
        when(observationMapperService.mapBundleToObservationDetails(any(Bundle.class), any(Boolean.class))).thenReturn(expectedDetails);

        // When (Unit / Method Run)
        ResponseEntity<String> response = observationController.getAllObservationDetailsByCode(code);

        // assert
        verify(client).search();
        verify(observationIUntypedQuery).forResource(eq(Observation.class));
        verify(bundleIQueryMock).where(any(ICriterion.class));
        verify(bundleIQueryMock).returnBundle(eq(Bundle.class));
        verify(bundleIQueryMock).execute();
        verify(observationMapperService).mapBundleToObservationDetails(any(Bundle.class), any(Boolean.class));

        assertThat(response.getStatusCode().value()).isEqualTo(200);
        assertThat(response.getBody()).isEqualTo("""
                                                          [ {
                                                            "bmi" : 31.0,
                                                            "patientId" : "Patient/123",
                                                            "observationId" : "Observation/49262"
                                                          }, {
                                                            "bmi" : 25.0,
                                                            "patientId" : "Patient/456",
                                                            "observationId" : "Obs/2"
                                                          } ]""");
    }

    @NotNull
    private static Observation createTestObservation(String code) {
        // TODO ADD Relevant Pre-Canned Data Objects for Service Tests
        Observation observation = new Observation();
        String system = "http://loinc.org";
        String display = "Body mass index (BMI)";

        CodeableConcept codeableConcept = new CodeableConcept()
                .addCoding(new Coding()
                        .setSystem(system)
                        .setCode(code)
                        .setDisplay(display));

        // valueQuantity for BMI
        double bmiValue = 31.0;
        String ucumSystem = "http://unitsofmeasure.org";
        String unit = "kg/m2";
        observation.setCode(codeableConcept);

        Identifier identifier = new Identifier();
        identifier.setSystem("http://example.com/patient");
        identifier.setValue("Patient/49262");
        identifier.setUse(Identifier.IdentifierUse.OFFICIAL);
        observation.setIdentifier(List.of(identifier));

        Quantity bmiQuantity = new Quantity()
                .setValue(bmiValue)
                .setSystem(ucumSystem)
                .setCode(unit)
                .setUnit(unit);
        observation.setValue(bmiQuantity);
        return observation;
    }
}