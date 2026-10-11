package com.sandbox.datastore;

import java.time.Instant;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/devices")
public class DeviceRecordController {

    private static final Logger log = LogManager.getLogger(DeviceRecordController.class);

    // in-memory placeholder store; replace with real persistence later
    private final Map<String, DeviceRecord> records = new ConcurrentHashMap<>();

    @GetMapping
    public Collection<DeviceRecord> listDevices() {
        log.info("Listing {} device records", records.size());
        return records.values();
    }

    @GetMapping("/{deviceId}")
    public DeviceRecord getDevice(@PathVariable String deviceId) {
        log.info("Fetching device record deviceId={}", deviceId);
        DeviceRecord record = records.get(deviceId);
        if (record == null) {
            log.warn("Device record not found deviceId={}", deviceId);
            throw new DeviceNotFoundException(deviceId);
        }
        return record;
    }

    @PostMapping
    public DeviceRecord registerDevice(@RequestBody DeviceRecordRequest request) {
        try {
            DeviceRecord record = new DeviceRecord(request.deviceId(), request.type(), Instant.now());
            records.put(record.deviceId(), record);
            log.info("Registered device record deviceId={} type={}", record.deviceId(), record.type());
            return record;
        } catch (Exception ex) {
            log.error("Failed to register device deviceId={}", request.deviceId(), ex);
            throw ex;
        }
    }

    @ExceptionHandler(DeviceNotFoundException.class)
    @ResponseStatus(org.springframework.http.HttpStatus.NOT_FOUND)
    public String handleNotFound(DeviceNotFoundException ex) {
        return ex.getMessage();
    }

    public record DeviceRecord(String deviceId, String type, Instant registeredAt) {
    }

    public record DeviceRecordRequest(String deviceId, String type) {
    }

    public static class DeviceNotFoundException extends RuntimeException {
        public DeviceNotFoundException(String deviceId) {
            super("Device not found: " + deviceId);
        }
    }
}
