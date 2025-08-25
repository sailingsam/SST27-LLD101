public class DatabaseLogger implements ILogger {
    public void log(String message) {
        // Simulate database logging
        System.out.println("[DB] Inserting log record: " + message);
    }
}
