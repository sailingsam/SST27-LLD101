package com.example.video;

import java.nio.file.Path;
import java.util.Objects;

/**
 * Facade class that simplifies the complex video processing pipeline.
 * Orchestrates Decoder, FilterEngine, SharpenAdapter, and Encoder to provide
 * a single, simple interface for video processing.
 */
public class VideoPipelineFacade {
    
    private final Decoder decoder;
    private final FilterEngine filterEngine;
    private final SharpenAdapter sharpenAdapter;
    private final Encoder encoder;
    
    public VideoPipelineFacade() {
        this.decoder = new Decoder();
        this.filterEngine = new FilterEngine();
        this.sharpenAdapter = new SharpenAdapter();
        this.encoder = new Encoder();
    }
    
    /**
     * Process a video file through the complete pipeline.
     * Sequence: decode → optional grayscale → optional scale → optional sharpen → encode
     * 
     * @param src the source video file path
     * @param out the output video file path
     * @param gray whether to apply grayscale filter
     * @param scale the scaling factor (null to skip scaling)
     * @param sharpenStrength the sharpen strength (null to skip sharpening)
     * @return Path to the processed video file
     */
    public Path process(Path src, Path out, boolean gray, Double scale, Integer sharpenStrength) {
        // Validate inputs
        Objects.requireNonNull(src, "src cannot be null");
        Objects.requireNonNull(out, "out cannot be null");
        
        // Step 1: Decode video to frames
        Frame[] frames = decoder.decode(src);
        
        // Step 2: Optional grayscale filter
        if (gray) {
            frames = filterEngine.grayscale(frames);
        }
        
        // Step 3: Optional scaling
        if (scale != null) {
            frames = filterEngine.scale(frames, scale);
        }
        
        // Step 4: Optional sharpening (using adapter for legacy API)
        if (sharpenStrength != null) {
            frames = sharpenAdapter.sharpen(frames, sharpenStrength);
        }
        
        // Step 5: Encode frames to output video
        return encoder.encode(frames, out);
    }
}
