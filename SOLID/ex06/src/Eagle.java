public class Eagle implements IBird, IFlyable {
    public void fly() {
        System.out.println("Soaring high!");
    }
    
    public void makeSound() {
        System.out.println("Screech!");
    }
    
    public String getName() {
        return "Eagle";
    }
}
