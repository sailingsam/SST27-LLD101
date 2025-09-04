package com.example.imports;

import java.nio.file.Path;
import java.util.List;
import java.util.Objects;

public class CsvProfileImporter implements ProfileImporter {
    private final NaiveCsvReader csvReader;
    private final ProfileService profileService;
    
    public CsvProfileImporter(NaiveCsvReader csvReader, ProfileService profileService) {
        this.csvReader = Objects.requireNonNull(csvReader, "csvReader");
        this.profileService = Objects.requireNonNull(profileService, "profileService");
    }
    
    @Override
    public int importFrom(Path csvFile) {
        Objects.requireNonNull(csvFile, "csvFile");
        
        List<String[]> rows = csvReader.read(csvFile);
        int successCount = 0;
        int rowNumber = 0;
        
        for (String[] row : rows) {
            rowNumber++;
            
            if (isValidRow(row)) {
                try {
                    String id = row[0].trim();
                    String email = row[1].trim();
                    String displayName = row.length > 2 ? row[2].trim() : "";
                    
                    profileService.createProfile(id, email, displayName);
                    successCount++;
                } catch (Exception e) {
                    System.err.println("Row " + rowNumber + ": Failed to create profile - " + e.getMessage());
                }
            } else {
                String reason = getValidationError(row);
                System.err.println("Row " + rowNumber + ": Invalid row - skipping (" + reason + ")");
            }
        }
        
        return successCount;
    }
    
    private boolean isValidRow(String[] row) {
        // Must have at least 2 columns (id, email)
        if (row.length < 2) {
            return false;
        }
        
        String id = row[0].trim();
        String email = row[1].trim();
        
        // ID and email must not be empty
        if (id.isEmpty() || email.isEmpty()) {
            return false;
        }
        
        // Basic email validation (ProfileService will do more detailed validation)
        if (!email.contains("@")) {
            return false;
        }
        
        return true;
    }
    
    private String getValidationError(String[] row) {
        if (row.length < 2) {
            return "insufficient columns";
        }
        
        String id = row[0].trim();
        String email = row[1].trim();
        
        if (id.isEmpty()) {
            return "missing id";
        }
        
        if (email.isEmpty()) {
            return "missing email";
        }
        
        if (!email.contains("@")) {
            return "invalid email format";
        }
        
        return "unknown validation error";
    }
}
