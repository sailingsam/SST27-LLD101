package com.snakesladders.util;

import com.snakesladders.model.*;
import java.util.*;

/**
 * Utility class to handle game input and setup
 */
public class GameInputHandler {
    private final Scanner scanner;

    public GameInputHandler() {
        this.scanner = new Scanner(System.in);
    }

    /**
     * Get number of players from user input
     */
    public int getNumberOfPlayers() {
        while (true) {
            System.out.print("Enter number of players (2-6): ");
            try {
                int numPlayers = scanner.nextInt();
                scanner.nextLine(); // consume newline
                if (numPlayers >= 2 && numPlayers <= 6) {
                    return numPlayers;
                } else {
                    System.out.println("Please enter a number between 2 and 6.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Please enter a valid number.");
                scanner.nextLine(); // consume invalid input
            }
        }
    }

    /**
     * Get player names from user input
     */
    public List<Player> getPlayers(int numPlayers) {
        List<Player> players = new ArrayList<>();
        Set<String> usedNames = new HashSet<>();

        for (int i = 0; i < numPlayers; i++) {
            while (true) {
                System.out.print("Enter name for player " + (i + 1) + ": ");
                String name = scanner.nextLine().trim();

                if (name.isEmpty()) {
                    System.out.println("Name cannot be empty.");
                } else if (usedNames.contains(name.toLowerCase())) {
                    System.out.println("Name already used. Please enter a different name.");
                } else {
                    players.add(new Player(name, i + 1));
                    usedNames.add(name.toLowerCase());
                    break;
                }
            }
        }

        return players;
    }

    /**
     * Get board size from user input
     */
    public int getBoardSize() {
        while (true) {
            System.out.print("Enter board size (10-100, default 100): ");
            String input = scanner.nextLine().trim();

            if (input.isEmpty()) {
                return 100; // default size
            }

            try {
                int size = Integer.parseInt(input);
                if (size >= 10 && size <= 100) {
                    return size;
                } else {
                    System.out.println("Please enter a size between 10 and 100.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }

    /**
     * Ask if user wants to continue to next turn
     */
    public boolean askContinue() {
        System.out.print("Press Enter to continue to next turn (or 'q' to quit): ");
        String input = scanner.nextLine().trim().toLowerCase();
        return !input.equals("q") && !input.equals("quit");
    }

    /**
     * Ask if user wants to play again
     */
    public boolean askPlayAgain() {
        while (true) {
            System.out.print("Do you want to play again? (y/n): ");
            String input = scanner.nextLine().trim().toLowerCase();
            if (input.equals("y") || input.equals("yes")) {
                return true;
            } else if (input.equals("n") || input.equals("no")) {
                return false;
            } else {
                System.out.println("Please enter 'y' for yes or 'n' for no.");
            }
        }
    }

    public void close() {
        scanner.close();
    }
}
