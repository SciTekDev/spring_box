package com.sandbox.devicecontrol;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/devices/{deviceId}/power")
public class PowerControlController {

    private static final Logger log = LogManager.getLogger(PowerControlController.class);

    @PostMapping("/on")
    public PowerState powerOn(@PathVariable String deviceId) {
        log.info("Sending power ON command to deviceId={}", deviceId);
        return new PowerState(deviceId, true);
    }

    @PostMapping("/off")
    public PowerState powerOff(@PathVariable String deviceId) {
        log.info("Sending power OFF command to deviceId={}", deviceId);
        return new PowerState(deviceId, false);
    }

    public record PowerState(String deviceId, boolean on) {
    }
}
