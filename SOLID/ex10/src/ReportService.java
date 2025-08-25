public class ReportService implements IReportGenerator {
    private final ILogger logger;
    
    public ReportService(ILogger logger) {
        this.logger = logger;
    }
    
    public void generate() {
        logger.log("Generating daily report...");
        System.out.println("Report contents...");
    }
}