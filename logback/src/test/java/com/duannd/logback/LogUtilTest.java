package com.duannd.logback;


import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;

public class LogUtilTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(LogUtilTest.class);

    @Test
    public void log() {
        LOGGER.info("Current Timestamp: {}", LocalDateTime.now());
        LOGGER.error("Error level");
    }

}
