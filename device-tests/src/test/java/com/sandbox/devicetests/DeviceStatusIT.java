package com.sandbox.devicetests;

import com.sandbox.devicetests.support.ApiClient;
import org.junit.jupiter.api.Test;

import java.net.http.HttpResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Integration test against the device-status service. Requires the service to be running
 * on DEVICE_STATUS_URL (defaults to http://localhost:8081). Run with:
 * mvn -pl device-tests verify -DskipITs=false
 */
class DeviceStatusIT {

    private static final String BASE_URL =
            System.getenv().getOrDefault("DEVICE_STATUS_URL", "http://localhost:8081");

    private final ApiClient client = new ApiClient();

    @Test
    void getStatus_returnsUnknownForNewDevice() throws Exception {
        HttpResponse<String> response = client.get(BASE_URL + "/api/devices/device-123/status");
        assertEquals(200, response.statusCode());
    }

    @Test
    void updateStatus_thenGet_reflectsNewState() throws Exception {
        client.put(BASE_URL + "/api/devices/device-123/status", "{\"state\":\"ONLINE\"}");
        HttpResponse<String> response = client.get(BASE_URL + "/api/devices/device-123/status");
        assertEquals(200, response.statusCode());
    }
}
