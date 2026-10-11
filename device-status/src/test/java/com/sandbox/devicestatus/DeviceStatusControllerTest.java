package com.sandbox.devicestatus;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DeviceStatusControllerTest {

    private final DeviceStatusController controller = new DeviceStatusController();

    @Test
    void getStatus_returnsUnknownForNewDevice() {
        DeviceStatusController.DeviceStatus status = controller.getStatus("device-123");
        assertEquals("device-123", status.deviceId());
        assertEquals("UNKNOWN", status.state());
    }

    @Test
    void updateStatus_thenGet_reflectsNewState() {
        controller.updateStatus("device-123", new DeviceStatusController.StatusUpdateRequest("ONLINE"));
        DeviceStatusController.DeviceStatus status = controller.getStatus("device-123");
        assertEquals("ONLINE", status.state());
    }
}
