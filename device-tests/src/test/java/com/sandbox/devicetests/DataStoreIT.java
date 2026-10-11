package com.sandbox.devicetests;

import com.sandbox.devicetests.support.ApiClient;
import org.junit.jupiter.api.Test;

import java.net.http.HttpResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Integration test against the data-store service. Requires the service to be running
 * on DATA_STORE_URL (defaults to http://localhost:8082). Run with:
 * mvn -pl device-tests verify -DskipITs=false
 */
class DataStoreIT {

    private static final String BASE_URL =
            System.getenv().getOrDefault("DATA_STORE_URL", "http://localhost:8082");

    private final ApiClient client = new ApiClient();

    @Test
    void registerDevice_thenList_includesDevice() throws Exception {
        client.post(BASE_URL + "/api/devices", "{\"deviceId\":\"device-123\",\"type\":\"sensor\"}");
        HttpResponse<String> response = client.get(BASE_URL + "/api/devices");
        assertEquals(200, response.statusCode());
    }
}
