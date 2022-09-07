package utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * The Class LoggerSingleton.
 */
public class LoggerSingleton {

    /** The log file path. */
    private final String logFilePath = "SkillsFootball/log_file.txt";

    /** The writer. */
    private PrintWriter writer;

    /** The logger. */
    // Singleton
    private static LoggerSingleton logger = null;

    /**
     * Instantiates a new logger singleton.
     */
    private LoggerSingleton() {
        try {
            File file = new File(logFilePath);
            file.getParentFile().mkdirs();
            FileWriter fw = new FileWriter(file, true);
            writer = new PrintWriter(fw, true);
        } catch (IOException e) {
        }
    }

    /**
     * Gets the single instance of LoggerSingleton.
     *
     * @return single instance of LoggerSingleton
     */
    public static synchronized LoggerSingleton getInstance() {
        if (logger == null) {
            logger = new LoggerSingleton();
        }
        return logger;
    }

    /**
     * Info.
     *
     * @param message the message
     */
    public void info(String message) {
        writer.println("### INFO ### : " + message);
    }

    /**
     * Debug.
     *
     * @param message the message
     */
    public void debug(String message) {
        writer.println("### DEBUG ### : " + message);
    }

    /**
     * Warning.
     *
     * @param message the message
     */
    public void warning(String message) {
        writer.println("### WARNING ### : " + message);
    }

    /**
     * Error.
     *
     * @param message the message
     */
    public void error(String message) {
        writer.println("### ERROR ### : " + message);
    }
}