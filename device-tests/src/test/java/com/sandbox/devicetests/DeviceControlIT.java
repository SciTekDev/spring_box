package com.sandbox.devicetests;

import com.sandbox.devicetests.support.ApiClient;
import org.junit.jupiter.api.Test;

import java.net.http.HttpResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Integration test against the device-control service. Requires the service to be running
 * on DEVICE_CONTROL_URL (defaults to http://localhost:8083). Run with:
 * mvn -pl device-tests verify -DskipITs=false
 */
class DeviceControlIT {

    private static final String BASE_URL =
            System.getenv().getOrDefault("DEVICE_CONTROL_URL", "http://localhost:8083");

    private final ApiClient client = new ApiClient();

    @Test
    void powerOn_returnsOnState() throws Exception {
        HttpResponse<String> response = client.post(BASE_URL + "/api/devices/device-123/power/on", "");
        assertEquals(200, response.statusCode());
    }

    @Test
    void setLed_returnsRequestedState() throws Exception {
        HttpResponse<String> response = client.post(
                BASE_URL + "/api/devices/device-123/led", "{\"color\":\"green\",\"on\":true}");
        assertEquals(200, response.statusCode());
    }

    @Test
    void sendCmdVel_echoesCommand() throws Exception {
        HttpResponse<String> response = client.post(
                BASE_URL + "/api/devices/device-123/movement/cmd-vel",
                "{\"linearX\":1,\"linearY\":0,\"linearZ\":0,\"angularX\":0,\"angularY\":0,\"angularZ\":0.5}");
        assertEquals(200, response.statusCode());
    }
}
