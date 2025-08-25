public class Demo10 {
    public static void main(String[] args) {
        // Original behavior - dependency injection setup
        ILogger logger = new ConsoleLogger();
        IReportGenerator reportService = new ReportService(logger);
        
        // Original test - same output expected
        reportService.generate();
        
        // Demo: Different logging strategies
        System.out.println();
        System.out.println("--- Demo: Different logging strategies ---");
        
        // File logging
        IReportGenerator fileReportService = new ReportService(new FileLogger());
        fileReportService.generate();
        
        System.out.println();
        
        // Database logging  
        IReportGenerator dbReportService = new ReportService(new DatabaseLogger());
        dbReportService.generate();
    }
}
