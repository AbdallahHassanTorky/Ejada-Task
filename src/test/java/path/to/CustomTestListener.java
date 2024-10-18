package path.to;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CustomTestListener implements ITestListener {
    private BufferedWriter logWriter;

    // ANSI escape codes for coloring
    private static final String RESET = "\u001B[0m";
    private static final String GREEN = "\u001B[32m";
    private static final String RED = "\u001B[31m";
    private static final String YELLOW = "\u001B[33m";
    private static final String BLUE = "\u001B[34m";

    // Initialize log file
    public CustomTestListener() {
        try {
            logWriter = new BufferedWriter(new FileWriter("test-log.txt", true)); // Append mode
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Log the test start
    @Override
    public void onTestStart(ITestResult result) {
        log(formatMessage("STARTED", result, BLUE));
    }

    // Log the test success
    @Override
    public void onTestSuccess(ITestResult result) {
        log(formatMessage("PASSED", result, GREEN));
    }

    // Log the test failure
    @Override
    public void onTestFailure(ITestResult result) {
        String message = formatMessage("FAILED", result, RED);
        message += " | Reason: " + result.getThrowable();
        log(message);
    }

    // Log the test skip
    @Override
    public void onTestSkipped(ITestResult result) {
        log(formatMessage("SKIPPED", result, YELLOW));
    }

    // Log when the test suite finishes
    @Override
    public void onFinish(ITestContext context) {
        String summary = String.format("%-10s | %d | %d | %d",
                "SUMMARY",
                context.getPassedTests().size(),
                context.getFailedTests().size(),
                context.getSkippedTests().size());
        log(summary);
        closeLog();
    }

    // Helper method to format the log message
    private String formatMessage(String status, ITestResult result, String color) {
        return String.format("%s%s | %-7s | %-30s | %-30s | %-30s%s",
                color,
                getCurrentTimestamp(),
                status,
                result.getName(),
                result.getTestClass().getName(),
                result.getMethod().getMethodName(),
                RESET);
    }

    // Log message to console and file
    private void log(String message) {
        System.out.println(message);
        try {
            logWriter.write(message);
            logWriter.newLine();
            logWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Get the current timestamp
    private String getCurrentTimestamp() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return dtf.format(LocalDateTime.now());
    }

    // Close the log writer
    private void closeLog() {
        try {
            if (logWriter != null) {
                logWriter.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
