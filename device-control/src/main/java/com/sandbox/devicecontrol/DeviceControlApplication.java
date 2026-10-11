package com.sandbox.devicecontrol;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import jakarta.annotation.PreDestroy;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

@SpringBootApplication
public class DeviceControlApplication {

    private static final Logger log = LogManager.getLogger(DeviceControlApplication.class);

    public static void main(String[] args) {
        try {
            SpringApplication.run(DeviceControlApplication.class, args);
        } catch (Exception ex) {
            log.error("device-control failed to start", ex);
            throw ex;
        }
    }

    @EventListener(ApplicationReadyEvent.class)
    public void onReady() {
        log.info("device-control application started and ready");
    }

    @PreDestroy
    public void onShutdown() {
        log.info("device-control application shutting down");
    }
}
