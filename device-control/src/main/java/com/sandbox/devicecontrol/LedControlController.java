package com.sandbox.devicecontrol;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/devices/{deviceId}/led")
public class LedControlController {

    private static final Logger log = LogManager.getLogger(LedControlController.class);

    @PostMapping
    public LedState setLed(@PathVariable String deviceId, @RequestBody LedCommand command) {
        try {
            log.info("Setting LED for deviceId={} color={} on={}", deviceId, command.color(), command.on());
            return new LedState(deviceId, command.color(), command.on());
        } catch (Exception ex) {
            log.error("Failed to set LED for deviceId={}", deviceId, ex);
            throw ex;
        }
    }

    public record LedCommand(String color, boolean on) {
    }

    public record LedState(String deviceId, String color, boolean on) {
    }
}
