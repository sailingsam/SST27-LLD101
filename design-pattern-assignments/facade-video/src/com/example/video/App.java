package com.example.video;
import java.nio.file.Path;

public class App {
    public static void main(String[] args) {
        // Use Facade pattern - single call replaces complex pipeline orchestration
        VideoPipelineFacade pipeline = new VideoPipelineFacade();
        
        // Process with grayscale, scaling, and sharpening (now possible with adapter!)
        Path out = pipeline.process(
            Path.of("in.mp4"),      // source
            Path.of("out.mp4"),     // output
            true,                   // apply grayscale
            0.5,                    // scale to 50%
            75                      // sharpen strength (now works with legacy API!)
        );
        
        System.out.println("Wrote " + out);
    }
}
