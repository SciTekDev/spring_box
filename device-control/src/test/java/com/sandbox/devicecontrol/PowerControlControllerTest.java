package com.sandbox.devicecontrol;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PowerControlControllerTest {

    private final PowerControlController controller = new PowerControlController();

    @Test
    void powerOn_returnsOnState() {
        PowerControlController.PowerState state = controller.powerOn("device-123");
        assertTrue(state.on());
    }

    @Test
    void powerOff_returnsOffState() {
        PowerControlController.PowerState state = controller.powerOff("device-123");
        assertFalse(state.on());
    }
}
