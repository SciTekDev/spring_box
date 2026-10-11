package com.sandbox.devicecontrol;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MovementControlControllerTest {

    private final MovementControlController controller = new MovementControlController();

    @Test
    void sendVelocity_echoesCommand() {
        MovementControlController.VelocityCommand command =
                new MovementControlController.VelocityCommand(1, 0, 0, 0, 0, 0.5);
        MovementControlController.VelocityCommand result = controller.sendVelocity("device-123", command);
        assertEquals(command, result);
    }

    @Test
    void stop_returnsZeroVelocity() {
        MovementControlController.VelocityCommand result = controller.stop("device-123");
        assertEquals(0, result.linearX());
        assertEquals(0, result.angularZ());
    }
}
