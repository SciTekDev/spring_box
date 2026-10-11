package com.sandbox.datastore;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import jakarta.annotation.PreDestroy;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

@SpringBootApplication
public class DataStoreApplication {

    private static final Logger log = LogManager.getLogger(DataStoreApplication.class);

    public static void main(String[] args) {
        try {
            SpringApplication.run(DataStoreApplication.class, args);
        } catch (Exception ex) {
            log.error("data-store failed to start", ex);
            throw ex;
        }
    }

    @EventListener(ApplicationReadyEvent.class)
    public void onReady() {
        log.info("data-store application started and ready!!");
    }

    @PreDestroy
    public void onShutdown() {
        log.info("data-store application shutting down");
    }
}
