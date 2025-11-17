package ie.rcsi.example.service;

import ca.uhn.fhir.rest.client.api.IGenericClient;
import ie.rcsi.example.client.HapiFhirClient;
import ie.rcsi.example.enums.HapiClientConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.env.Environment;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ClientWrapperServiceTest {

    private Environment environment;
    private ClientWrapperService clientWrapperService;

    @BeforeEach
    void setUp() {
        environment = mock(Environment.class);
        clientWrapperService = new ClientWrapperService(environment);
    }

    @Test
    void getClientInstance_usesBaseUrlFromEnvironmentAndReturnsClient() {
        // arrange
        String baseUrlKey = HapiClientConfig.BASE_URL.getKey();
        String baseUrlValue = "http://example-fhir-server";
        IGenericClient expectedClient = mock(IGenericClient.class);

        when(environment.getRequiredProperty(baseUrlKey)).thenReturn(baseUrlValue);

        try (MockedStatic<HapiFhirClient> hapiFhirClientMockedStatic = Mockito.mockStatic(HapiFhirClient.class)) {
            hapiFhirClientMockedStatic
                    .when(() -> HapiFhirClient.getInstance(baseUrlValue))
                    .thenReturn(expectedClient);

            // act
            IGenericClient actualClient = clientWrapperService.getClientInstance();

            // assert
            verify(environment).getRequiredProperty(baseUrlKey);
            hapiFhirClientMockedStatic.verify(() -> HapiFhirClient.getInstance(baseUrlValue));

            assertThat(actualClient).isSameAs(expectedClient);
        }
    }
}