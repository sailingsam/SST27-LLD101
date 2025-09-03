package exercise;

/**
 * Adapter class to adapt EmployeeLDAP to the Employee interface
 */
public class EmployeeLDAPAdapter implements Employee {
    private final EmployeeLDAP employeeLDAP;
    
    public EmployeeLDAPAdapter(EmployeeLDAP employeeLDAP) {
        this.employeeLDAP = employeeLDAP;
    }
    
    @Override
    public String getId() {
        String uid = employeeLDAP.get("uid");
        return uid != null ? uid : "";
    }
    
    @Override
    public String getFirstName() {
        String givenName = employeeLDAP.get("givenName");
        return givenName != null ? givenName : "";
    }
    
    @Override
    public String getLastName() {
        String sn = employeeLDAP.get("sn");
        return sn != null ? sn : "";
    }
    
    @Override
    public String getEmail() {
        String mail = employeeLDAP.get("mail");
        return mail != null ? mail : "";
    }
}
