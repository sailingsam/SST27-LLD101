package com.snakesladders.model;

import java.util.Random;

/**
 * Represents a dice in the game
 */
public class Dice {
    private final int numberOfSides;
    private final Random random;

    public Dice() {
        this(6); // Standard 6-sided dice
    }

    public Dice(int numberOfSides) {
        if (numberOfSides < 1) {
            throw new IllegalArgumentException("Number of sides must be positive");
        }
        this.numberOfSides = numberOfSides;
        this.random = new Random();
    }

    /**
     * Roll the dice and return a value between 1 and numberOfSides (inclusive)
     */
    public int roll() {
        return random.nextInt(numberOfSides) + 1;
    }

    public int getNumberOfSides() {
        return numberOfSides;
    }

    @Override
    public String toString() {
        return "Dice{" +
                "numberOfSides=" + numberOfSides +
                '}';
    }
}
