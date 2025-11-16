package ie.rcsi.example.enums;

import lombok.Getter;

public enum HapiClientConfig {
    BASE_URL("ie.rcsi.example.client.base-url");

    @Getter
    private final String key;

    HapiClientConfig(String key) {
        this.key = key;
    }
}
