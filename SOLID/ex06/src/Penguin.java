public class Penguin implements IBird, ISwimmable {
    public void swim() {
        System.out.println("Swimming gracefully!");
    }
    
    public void makeSound() {
        System.out.println("Squawk!");
    }
    
    public String getName() {
        return "Penguin";
    }
}