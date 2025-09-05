package com.example.render;

/**
 * Immutable TextStyle class representing intrinsic state that can be shared
 * across multiple Glyph instances using the Flyweight pattern.
 */
public class TextStyle {
    private final String font;
    private final int size;
    private final boolean bold;

    public TextStyle(String font, int size, boolean bold) {
        this.font = font;
        this.size = size;
        this.bold = bold;
    }

    public String getFont() {
        return font;
    }

    public int getSize() {
        return size;
    }

    public boolean isBold() {
        return bold;
    }

    /**
     * Calculate drawing cost based on style properties
     */
    public int getDrawCost() {
        return size + (bold ? 10 : 0);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        TextStyle textStyle = (TextStyle) obj;
        return size == textStyle.size &&
               bold == textStyle.bold &&
               font.equals(textStyle.font);
    }

    @Override
    public int hashCode() {
        return font.hashCode() * 31 * 31 + size * 31 + (bold ? 1 : 0);
    }
}
