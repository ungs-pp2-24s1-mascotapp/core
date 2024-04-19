package com.mascotapp.core.logger;

public abstract class Logger {
    private static OutputWriter outputWriter = new ConsoleOutputWriter(); // Por defecto, escribirá en la consola real

    public static void setErrorOutput(OutputWriter outputWriter) {
        Logger.outputWriter = outputWriter;
    }

    public static void error(String message) {
        outputWriter.write("ERROR LOG");
        outputWriter.write(message);
    }
}

interface OutputWriter {
    void write(String message);
}

class ConsoleOutputWriter implements OutputWriter {
    @Override
    public void write(String message) {
        System.out.println(message);
    }
}
