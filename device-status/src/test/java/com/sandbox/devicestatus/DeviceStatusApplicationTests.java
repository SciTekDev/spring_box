package com.sandbox.devicestatus;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(properties = "spring.cloud.consul.discovery.enabled=false")
class DeviceStatusApplicationTests {

    @Test
    void contextLoads() {
    }
}
