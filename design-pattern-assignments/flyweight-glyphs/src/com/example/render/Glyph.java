package com.example.render;

public class Glyph {
    // Flyweight pattern: intrinsic state (TextStyle) shared, extrinsic state (char) unique
    private final char ch;
    private final TextStyle textStyle;

    public Glyph(char ch, TextStyle textStyle) {
        this.ch = ch;
        this.textStyle = textStyle;
    }

    public int drawCost() { 
        return textStyle.getDrawCost(); 
    }
    
    public char getCh() { 
        return ch; 
    }
    
    public TextStyle getTextStyle() { 
        return textStyle; 
    }
    
    // Convenience methods for backward compatibility
    public String getFont() { 
        return textStyle.getFont(); 
    }
    
    public int getSize() { 
        return textStyle.getSize(); 
    }
    
    public boolean isBold() { 
        return textStyle.isBold(); 
    }
}
