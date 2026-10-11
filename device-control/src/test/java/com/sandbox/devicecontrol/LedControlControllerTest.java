package com.sandbox.devicecontrol;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class LedControlControllerTest {

    private final LedControlController controller = new LedControlController();

    @Test
    void setLed_returnsRequestedState() {
        LedControlController.LedState state = controller.setLed("device-123",
                new LedControlController.LedCommand("green", true));
        assertEquals("green", state.color());
        assertTrue(state.on());
    }
}
