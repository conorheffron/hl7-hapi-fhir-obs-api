package ie.rcsi.example.service;

import ca.uhn.fhir.rest.client.api.IGenericClient;
import ie.rcsi.example.client.HapiFhirClient;
import ie.rcsi.example.enums.HapiClientConfig;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

@Service
public class ClientWrapperService {

    private final Environment environment;

    public ClientWrapperService(Environment environment) {
        this.environment = environment;
    }

    public IGenericClient getClientInstance() {
        return HapiFhirClient.getInstance(environment.getRequiredProperty(HapiClientConfig.BASE_URL.getKey()));
    }
}
