public class FileLogger implements ILogger {
    public void log(String message) {
        // Simulate file logging
        System.out.println("[FILE] Writing to log.txt: " + message);
    }
}
