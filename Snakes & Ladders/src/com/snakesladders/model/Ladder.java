package com.snakesladders.model;

/**
 * Represents a ladder on the board that moves player up
 */
public class Ladder {
    private final int bottom;
    private final int top;

    public Ladder(int bottom, int top) {
        if (bottom >= top) {
            throw new IllegalArgumentException("Ladder bottom must be less than top");
        }
        this.bottom = bottom;
        this.top = top;
    }

    public int getBottom() {
        return bottom;
    }

    public int getTop() {
        return top;
    }

    /**
     * Check if player landed on ladder bottom
     */
    public boolean hasLadderBottom(int position) {
        return position == bottom;
    }

    /**
     * Get the top position where player should move
     */
    public int getNewPosition(int currentPosition) {
        if (currentPosition == bottom) {
            return top;
        }
        return currentPosition;
    }

    @Override
    public String toString() {
        return "Ladder{" +
                "bottom=" + bottom +
                ", top=" + top +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        Ladder ladder = (Ladder) obj;
        return bottom == ladder.bottom && top == ladder.top;
    }

    @Override
    public int hashCode() {
        return bottom * 31 + top;
    }
}
