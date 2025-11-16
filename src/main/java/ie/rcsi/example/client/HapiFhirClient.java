package ie.rcsi.example.client;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.rest.client.api.IGenericClient;
import lombok.Getter;

public final class HapiFhirClient {

    private static IGenericClient clientInstance;

    private HapiFhirClient() { }

    @Getter
    private static final FhirContext fhirContext = FhirContext.forR4B();

    public static synchronized IGenericClient getInstance(String serverBase) {
        if(clientInstance == null) {
            clientInstance = createClient(serverBase);
        }

        return clientInstance;
    }

    private static IGenericClient createClient(String serverBase) {
        // Connect to the public test server
        return fhirContext.newRestfulGenericClient(serverBase);
    }
}
