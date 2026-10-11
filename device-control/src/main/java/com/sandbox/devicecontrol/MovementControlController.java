package com.sandbox.devicecontrol;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.*;

/**
 * Simulated ROS2-style movement control (cmd_vel-like linear/angular velocity commands).
 */
@RestController
@RequestMapping("/api/devices/{deviceId}/movement")
public class MovementControlController {

    private static final Logger log = LogManager.getLogger(MovementControlController.class);

    @PostMapping("/cmd-vel")
    public VelocityCommand sendVelocity(@PathVariable String deviceId, @RequestBody VelocityCommand command) {
        try {
            log.info("Sending cmd_vel to deviceId={} linear=({}, {}, {}) angular=({}, {}, {})",
                    deviceId, command.linearX(), command.linearY(), command.linearZ(),
                    command.angularX(), command.angularY(), command.angularZ());
            return command;
        } catch (Exception ex) {
            log.error("Failed to send movement command to deviceId={}", deviceId, ex);
            throw ex;
        }
    }

    @PostMapping("/stop")
    public VelocityCommand stop(@PathVariable String deviceId) {
        log.info("Sending stop command to deviceId={}", deviceId);
        return new VelocityCommand(0, 0, 0, 0, 0, 0);
    }

    public record VelocityCommand(
            double linearX, double linearY, double linearZ,
            double angularX, double angularY, double angularZ) {
    }
}
