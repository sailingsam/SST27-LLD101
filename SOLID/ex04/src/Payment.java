public class Payment {
    private String provider; 
    private double amount;
    
    public Payment(String provider, double amount) { 
        this.provider = provider; 
        this.amount = amount; 
    }
    
    public String getProvider() {
        return provider;
    }
    
    public double getAmount() {
        return amount;
    }
}