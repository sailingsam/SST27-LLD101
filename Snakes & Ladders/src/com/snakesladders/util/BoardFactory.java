package com.snakesladders.util;

import com.snakesladders.model.*;
import java.util.*;

/**
 * Factory class to create pre-configured boards
 */
public class BoardFactory {

    /**
     * Create a standard 100-square board with classic snakes and ladders
     */
    public static Board createStandardBoard() {
        return createStandardBoard(100);
    }

    /**
     * Create a board with standard snake and ladder configuration scaled to size
     */
    public static Board createStandardBoard(int size) {
        Board board = new Board(size);

        // Add snakes and ladders based on board size
        if (size >= 100) {
            addStandardSnakesAndLadders(board);
        } else {
            addScaledSnakesAndLadders(board, size);
        }

        return board;
    }

    /**
     * Add standard snakes and ladders for 100-square board
     */
    private static void addStandardSnakesAndLadders(Board board) {
        // Standard snakes (head -> tail)
        board.addSnake(new Snake(99, 78));
        board.addSnake(new Snake(95, 75));
        board.addSnake(new Snake(92, 88));
        board.addSnake(new Snake(87, 24));
        board.addSnake(new Snake(64, 60));
        board.addSnake(new Snake(62, 19));
        board.addSnake(new Snake(56, 53));
        board.addSnake(new Snake(49, 11));
        board.addSnake(new Snake(47, 26));
        board.addSnake(new Snake(16, 6));

        // Standard ladders (bottom -> top)
        board.addLadder(new Ladder(1, 38));
        board.addLadder(new Ladder(4, 14));
        board.addLadder(new Ladder(9, 21));
        board.addLadder(new Ladder(21, 42));
        board.addLadder(new Ladder(28, 84));
        board.addLadder(new Ladder(36, 44));
        board.addLadder(new Ladder(51, 67));
        board.addLadder(new Ladder(71, 91));
        board.addLadder(new Ladder(80, 100));
    }

    /**
     * Add scaled snakes and ladders for smaller boards
     */
    private static void addScaledSnakesAndLadders(Board board, int size) {
        Random random = new Random(42); // Fixed seed for consistent generation

        // Calculate number of snakes and ladders based on board size
        int numSnakes = Math.max(2, size / 15);
        int numLadders = Math.max(2, size / 15);

        Set<Integer> usedPositions = new HashSet<>();

        // Add snakes
        for (int i = 0; i < numSnakes; i++) {
            int head = random.nextInt(size - size / 4) + size / 4; // Upper 3/4 of board
            int tail = random.nextInt(head - 1) + 1; // Below head

            if (!usedPositions.contains(head) && !usedPositions.contains(tail)) {
                board.addSnake(new Snake(head, tail));
                usedPositions.add(head);
                usedPositions.add(tail);
            }
        }

        // Add ladders
        for (int i = 0; i < numLadders; i++) {
            int bottom = random.nextInt(size - size / 4) + 1; // Lower 3/4 of board
            int top = random.nextInt(size - bottom) + bottom + 1; // Above bottom

            if (!usedPositions.contains(bottom) && !usedPositions.contains(top)) {
                board.addLadder(new Ladder(bottom, top));
                usedPositions.add(bottom);
                usedPositions.add(top);
            }
        }
    }

    /**
     * Create a simple board with minimal snakes and ladders for testing
     */
    public static Board createSimpleBoard(int size) {
        Board board = new Board(size);

        // Add a few simple snakes and ladders
        if (size >= 20) {
            board.addSnake(new Snake(size - 2, size / 4));
            board.addLadder(new Ladder(size / 4, size - 5));
        }

        if (size >= 50) {
            board.addSnake(new Snake(size - 10, size / 3));
            board.addLadder(new Ladder(size / 3, size - 15));
        }

        return board;
    }
}
