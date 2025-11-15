package ie.rcsi.example.client;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.rest.client.api.IGenericClient;
import lombok.Getter;

public final class HapiFhirClient {

    private static final String SERVER_BASE = "http://hapi.fhir.org/baseR4";

    private static IGenericClient clientInstance;

    private HapiFhirClient() { }

    @Getter
    private static final FhirContext fhirContext = FhirContext.forR4();

    public static synchronized IGenericClient getInstance() {
        if(clientInstance == null) {
            clientInstance = createClient();
        }

        return clientInstance;
    }

    private static IGenericClient createClient() {
        // Connect to the public test server
        return fhirContext.newRestfulGenericClient(SERVER_BASE);
    }
}
