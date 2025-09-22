package com.snakesladders.model;

import java.util.*;

/**
 * Represents the game board with snakes and ladders
 */
public class Board {
    private final int size;
    private final Map<Integer, Snake> snakes;
    private final Map<Integer, Ladder> ladders;

    public Board(int size) {
        if (size < 1) {
            throw new IllegalArgumentException("Board size must be positive");
        }
        this.size = size;
        this.snakes = new HashMap<>();
        this.ladders = new HashMap<>();
    }

    public void addSnake(Snake snake) {
        if (snake.getHead() > size || snake.getTail() < 1) {
            throw new IllegalArgumentException("Snake positions must be within board boundaries");
        }
        if (ladders.containsKey(snake.getHead()) || ladders.containsKey(snake.getTail())) {
            throw new IllegalArgumentException("Cannot place snake on ladder positions");
        }
        snakes.put(snake.getHead(), snake);
    }

    public void addLadder(Ladder ladder) {
        if (ladder.getTop() > size || ladder.getBottom() < 1) {
            throw new IllegalArgumentException("Ladder positions must be within board boundaries");
        }
        if (snakes.containsKey(ladder.getBottom()) || snakes.containsKey(ladder.getTop())) {
            throw new IllegalArgumentException("Cannot place ladder on snake positions");
        }
        ladders.put(ladder.getBottom(), ladder);
    }

    /**
     * Get the final position after considering snakes and ladders
     */
    public int getFinalPosition(int position) {
        if (position > size) {
            return position; // Invalid position, let game handle it
        }

        // Check for snake
        if (snakes.containsKey(position)) {
            return snakes.get(position).getTail();
        }

        // Check for ladder
        if (ladders.containsKey(position)) {
            return ladders.get(position).getTop();
        }

        return position;
    }

    public boolean isValidPosition(int position) {
        return position >= 1 && position <= size;
    }

    public boolean isWinningPosition(int position) {
        return position == size;
    }

    public int getSize() {
        return size;
    }

    public Map<Integer, Snake> getSnakes() {
        return new HashMap<>(snakes);
    }

    public Map<Integer, Ladder> getLadders() {
        return new HashMap<>(ladders);
    }

    /**
     * Get display representation of the board
     */
    public void displayBoard() {
        System.out.println("Board Configuration:");
        System.out.println("Size: " + size);
        System.out.println("Snakes:");
        for (Snake snake : snakes.values()) {
            System.out.println("  " + snake.getHead() + " -> " + snake.getTail());
        }
        System.out.println("Ladders:");
        for (Ladder ladder : ladders.values()) {
            System.out.println("  " + ladder.getBottom() + " -> " + ladder.getTop());
        }
    }

    @Override
    public String toString() {
        return "Board{" +
                "size=" + size +
                ", snakes=" + snakes.size() +
                ", ladders=" + ladders.size() +
                '}';
    }
}
