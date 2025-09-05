package com.example.report;

import java.nio.file.Path;
import java.util.Map;
import java.util.Objects;

/**
 * Facade class that simplifies the complex process of creating a report bundle.
 * Orchestrates JsonWriter, Zipper, and AuditLog to provide a single, simple interface.
 */
public class ReportBundleFacade {
    
    private final JsonWriter jsonWriter;
    private final Zipper zipper;
    private final AuditLog auditLog;
    
    public ReportBundleFacade() {
        this.jsonWriter = new JsonWriter();
        this.zipper = new Zipper();
        this.auditLog = new AuditLog();
    }
    
    /**
     * Export data as a zipped JSON report bundle.
     * 
     * @param data the data to export as JSON
     * @param outDir the output directory for the zip file
     * @param baseName the base name for the files (will create baseName.zip)
     * @return Path to the created zip file
     * @throws UncheckedIOException if any IO errors occur
     */
    public Path export(Map<String, Object> data, Path outDir, String baseName) {
        // Validate inputs
        Objects.requireNonNull(data, "data cannot be null");
        Objects.requireNonNull(outDir, "outDir cannot be null");
        Objects.requireNonNull(baseName, "baseName cannot be null");
        
        // Step 1: Write JSON file
        Path jsonFile = jsonWriter.write(data, outDir, baseName);
        
        // Step 2: Zip the JSON file
        Path zipFile = outDir.resolve(baseName + ".zip");
        Path resultZip = zipper.zip(jsonFile, zipFile);
        
        // Step 3: Log the success
        auditLog.log("exported " + resultZip);
        
        return resultZip;
    }
}
