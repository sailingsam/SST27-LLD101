public class Demo06 {
    public static void main(String[] args) {
        Aviary aviary = new Aviary();
        
        // Original behavior - but now with proper method calls (no more crashes!)
        aviary.releaseFlyingBird(new Bird());  // Only accepts flyable birds
        aviary.releaseBird(new Penguin());     // Handles non-flying birds properly
        
        // Demo: LSP compliance - all IBird implementations work
        System.out.println();
        System.out.println("--- Demo: LSP compliance ---");
        
        // All birds can be used polymorphically
        IBird[] birds = {new Bird(), new Penguin(), new Eagle()};
        for (IBird bird : birds) {
            bird.makeSound();  // All IBird implementations support this
        }
        
        System.out.println();
        System.out.println("--- Demo: Different release methods ---");
        aviary.releaseBird(new Eagle());      // Flying bird via general method
        aviary.releaseFlyingBird(new Eagle()); // Flying bird via specific method
    }
}
