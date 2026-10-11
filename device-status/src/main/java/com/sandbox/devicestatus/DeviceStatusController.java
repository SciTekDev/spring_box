package com.sandbox.devicestatus;

import java.time.Instant;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/devices")
public class DeviceStatusController {

    private static final Logger log = LogManager.getLogger(DeviceStatusController.class);

    // in-memory placeholder store; replace with data-store integration later
    private final Map<String, DeviceStatus> statuses = new ConcurrentHashMap<>();

    @GetMapping("/{deviceId}/status")
    public DeviceStatus getStatus(@PathVariable String deviceId) {
        log.info("Fetching status for deviceId={}", deviceId);
        return statuses.getOrDefault(deviceId, new DeviceStatus(deviceId, "UNKNOWN", Instant.now()));
    }

    @PutMapping("/{deviceId}/status")
    public DeviceStatus updateStatus(@PathVariable String deviceId, @RequestBody StatusUpdateRequest request) {
        try {
            DeviceStatus updated = new DeviceStatus(deviceId, request.state(), Instant.now());
            statuses.put(deviceId, updated);
            log.info("Updated status for deviceId={} to state={}", deviceId, request.state());
            return updated;
        } catch (Exception ex) {
            log.error("Failed to update status for deviceId={}", deviceId, ex);
            throw ex;
        }
    }

    public record DeviceStatus(String deviceId, String state, Instant lastUpdated) {
    }

    public record StatusUpdateRequest(String state) {
    }
}
