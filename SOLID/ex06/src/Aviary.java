public class Aviary {
    // Method for flying birds only
    public void releaseFlyingBird(IFlyable flyingBird) {
        flyingBird.fly(); 
        System.out.println("Flying bird released");
    }
    
    // Method for any bird (general release)
    public void releaseBird(IBird bird) {
        System.out.println("Releasing " + bird.getName());
        bird.makeSound();
        
        // Check specific capabilities and act accordingly
        if (bird instanceof IFlyable) {
            ((IFlyable) bird).fly();
            System.out.println("Flew away");
        } else if (bird instanceof ISwimmable) {
            ((ISwimmable) bird).swim();
            System.out.println("Swam to freedom");
        } else {
            System.out.println("Released safely");
        }
    }
}