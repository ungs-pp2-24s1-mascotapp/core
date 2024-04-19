package com.mascotapp.core.logger;

import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class LoggerTest {

    @Test
    public void testErrorLogsMessage() {
        // Arrange
        ArrayList<String> logList = new ArrayList<>();
        Logger.setErrorOutput(new TestOutputWriter(logList));

        // Act
        Logger.error("Test error message");

        // Assert
        assertEquals("ERROR LOG", logList.get(0));
        assertEquals("Test error message", logList.get(1));
    }

    private static class TestOutputWriter implements OutputWriter {
        private final ArrayList<String> logList;

        public TestOutputWriter(ArrayList<String> logList) {
            this.logList = logList;
        }

        @Override
        public void write(String message) {
            logList.add(message);
        }
    }
}
