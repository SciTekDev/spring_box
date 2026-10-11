package com.sandbox.datastore;

import org.junit.jupiter.api.Test;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DeviceRecordControllerTest {

    private final DeviceRecordController controller = new DeviceRecordController();

    @Test
    void registerDevice_thenList_includesDevice() {
        controller.registerDevice(new DeviceRecordController.DeviceRecordRequest("device-123", "sensor"));
        Collection<DeviceRecordController.DeviceRecord> records = controller.listDevices();
        assertTrue(records.stream().anyMatch(r -> r.deviceId().equals("device-123") && r.type().equals("sensor")));
    }

    @Test
    void getDevice_returnsRegisteredRecord() {
        controller.registerDevice(new DeviceRecordController.DeviceRecordRequest("device-456", "actuator"));
        DeviceRecordController.DeviceRecord record = controller.getDevice("device-456");
        assertEquals("actuator", record.type());
    }

    @Test
    void getDevice_throwsWhenNotFound() {
        assertThrows(DeviceRecordController.DeviceNotFoundException.class,
                () -> controller.getDevice("does-not-exist"));
    }
}
