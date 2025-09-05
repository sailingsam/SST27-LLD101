package com.example.video;

/**
 * Adapter class that adapts the LegacySharpen API (which works with string handles)
 * to work with Frame arrays, bridging the incompatible interfaces.
 */
public class SharpenAdapter {
    
    private final LegacySharpen legacySharpen;
    
    public SharpenAdapter() {
        this.legacySharpen = new LegacySharpen();
    }
    
    /**
     * Adapts the legacy sharpen API to work with Frame arrays.
     * Converts Frame[] -> String handle -> calls legacy API -> converts back to Frame[]
     * 
     * @param frames the frames to sharpen
     * @param strength the sharpen strength (1-100)
     * @return the sharpened frames
     */
    public Frame[] sharpen(Frame[] frames, int strength) {
        if (frames == null || frames.length == 0) {
            return frames;
        }
        
        // Convert Frame[] to string handle (simulate serialization)
        String framesHandle = convertFramesToHandle(frames);
        
        // Call the legacy API with string handle
        String resultHandle = legacySharpen.applySharpen(framesHandle, strength);
        
        // Convert result handle back to Frame[] (simulate deserialization)
        return convertHandleToFrames(resultHandle, frames);
    }
    
    /**
     * Convert Frame array to a string handle (simulated serialization)
     */
    private String convertFramesToHandle(Frame[] frames) {
        StringBuilder handle = new StringBuilder("FRAMES:");
        for (int i = 0; i < frames.length; i++) {
            handle.append(frames[i].w).append("x").append(frames[i].h);
            if (i < frames.length - 1) {
                handle.append(",");
            }
        }
        return handle.toString();
    }
    
    /**
     * Convert string handle back to Frame array (simulated deserialization)
     * In reality, this would reconstruct the actual frame data
     */
    private Frame[] convertHandleToFrames(String handle, Frame[] originalFrames) {
        // For simulation, just return the original frames (pretending they were sharpened)
        // In real implementation, this would parse the handle and reconstruct frames
        return originalFrames;
    }
}
