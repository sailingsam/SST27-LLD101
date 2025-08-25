public class Demo02 {
    public static void main(String[] args) {
        // Dependency injection setup
        Decoder decoder = new SimpleDecoder();
        UIDisplay uiDisplay = new ConsoleUIDisplay();
        FrameCache frameCache = new InMemoryFrameCache();
        
        // Create Player with dependencies
        Player player = new Player(decoder, uiDisplay, frameCache);
        
        // Original test - same output expected
        player.play(new byte[]{1,2,3,4});
        
        // Tiny demo/test as required by README
        System.out.println("\n--- Demo: Different file size ---");
        player.play(new byte[]{10,20,30,40,50,60});
    }
}
