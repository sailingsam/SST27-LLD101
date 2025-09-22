package com.snakesladders.model;

/**
 * Represents a snake on the board that moves player down
 */
public class Snake {
    private final int head;
    private final int tail;

    public Snake(int head, int tail) {
        if (head <= tail) {
            throw new IllegalArgumentException("Snake head must be greater than tail");
        }
        this.head = head;
        this.tail = tail;
    }

    public int getHead() {
        return head;
    }

    public int getTail() {
        return tail;
    }

    /**
     * Check if player landed on snake head
     */
    public boolean hasSnakeHead(int position) {
        return position == head;
    }

    /**
     * Get the tail position where player should move
     */
    public int getNewPosition(int currentPosition) {
        if (currentPosition == head) {
            return tail;
        }
        return currentPosition;
    }

    @Override
    public String toString() {
        return "Snake{" +
                "head=" + head +
                ", tail=" + tail +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        Snake snake = (Snake) obj;
        return head == snake.head && tail == snake.tail;
    }

    @Override
    public int hashCode() {
        return head * 31 + tail;
    }
}
