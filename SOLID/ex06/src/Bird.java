public class Bird implements IBird, IFlyable {
    public void fly() {
        System.out.println("Flap!"); 
    }
    
    public void makeSound() {
        System.out.println("Tweet!");
    }
    
    public String getName() {
        return "Bird";
    }
}