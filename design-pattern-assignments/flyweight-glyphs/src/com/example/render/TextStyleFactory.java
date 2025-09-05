package com.example.render;

import java.util.HashMap;
import java.util.Map;

/**
 * Factory class that implements the Flyweight pattern by caching and reusing
 * TextStyle instances based on their properties.
 */
public class TextStyleFactory {
    private static final Map<String, TextStyle> styleCache = new HashMap<>();

    /**
     * Get a TextStyle instance, creating and caching it if it doesn't exist.
     * Uses the suggested key format: "font|size|B" (B for bold, empty for regular)
     */
    public static TextStyle getTextStyle(String font, int size, boolean bold) {
        String key = font + "|" + size + "|" + (bold ? "B" : "");
        
        // Return existing instance if available
        TextStyle existingStyle = styleCache.get(key);
        if (existingStyle != null) {
            return existingStyle;
        }
        
        // Create new instance and cache it
        TextStyle newStyle = new TextStyle(font, size, bold);
        styleCache.put(key, newStyle);
        return newStyle;
    }

}
