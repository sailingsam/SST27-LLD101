package exercise;

public class EmployeeCSVAdapter implements Employee {
    private final EmployeeCSV employeeCSV;
    
    public EmployeeCSVAdapter(EmployeeCSV employeeCSV) {
        this.employeeCSV = employeeCSV;
    }
    
    @Override
    public String getId() {
        String[] tokens = employeeCSV.tokens();
        return tokens.length > 0 ? tokens[0] : "";
    }
    
    @Override
    public String getFirstName() {
        String[] tokens = employeeCSV.tokens();
        return tokens.length > 1 ? tokens[1] : "";
    }
    
    @Override
    public String getLastName() {
        String[] tokens = employeeCSV.tokens();
        return tokens.length > 2 ? tokens[2] : "";
    }
    
    @Override
    public String getEmail() {
        String[] tokens = employeeCSV.tokens();
        return tokens.length > 3 ? tokens[3] : "";
    }
}
