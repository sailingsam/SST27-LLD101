package com.snakesladders;

import com.snakesladders.game.Game;
import com.snakesladders.model.*;
import com.snakesladders.util.*;
import java.util.List;

/**
 * Main client class to run the Snakes and Ladders game
 */
public class SnakesAndLaddersGame {
    private static final String GAME_TITLE = """
            ╔═══════════════════════════════════════╗
            ║        SNAKES AND LADDERS GAME        ║
            ║                                       ║
            ║    🐍 Beware of the Snakes! 🐍       ║
            ║    🪜 Climb the Ladders! 🪜          ║
            ╚═══════════════════════════════════════╝
            """;

    private final GameInputHandler inputHandler;

    public SnakesAndLaddersGame() {
        this.inputHandler = new GameInputHandler();
    }

    /**
     * Main game loop
     */
    public void run() {
        System.out.println(GAME_TITLE);
        System.out.println("Welcome to Snakes and Ladders!");
        System.out.println();

        boolean playAgain = true;

        while (playAgain) {
            playOneGame();
            playAgain = inputHandler.askPlayAgain();
            System.out.println();
        }

        System.out.println("Thanks for playing Snakes and Ladders! Goodbye! 👋");
        inputHandler.close();
    }

    /**
     * Play one complete game
     */
    private void playOneGame() {
        try {
            // Get game configuration
            int boardSize = inputHandler.getBoardSize();
            int numPlayers = inputHandler.getNumberOfPlayers();
            List<Player> players = inputHandler.getPlayers(numPlayers);

            // Create board and game
            Board board = BoardFactory.createStandardBoard(boardSize);
            Game game = new Game(board, players);

            System.out.println("\n" + "=".repeat(50));
            System.out.println("Starting new game!");
            System.out.println("=".repeat(50));

            // Start the game
            game.startGame();

            // Game loop - play turn by turn with user interaction
            while (!game.isGameFinished()) {
                if (!inputHandler.askContinue()) {
                    System.out.println("Game ended by user.");
                    break;
                }

                game.playTurn();

                if (!game.isGameFinished()) {
                    game.displayGameStatus();
                }
            }

            // Display final results
            if (game.isGameFinished() && game.getWinner() != null) {
                System.out.println("\n" + "=".repeat(50));
                System.out.println("🎉 GAME OVER 🎉");
                System.out.println("Winner: " + game.getWinner().getName());
                System.out.println("=".repeat(50));

                // Display final positions
                System.out.println("\nFinal Positions:");
                List<Player> finalPlayers = game.getPlayers();
                finalPlayers.sort((p1, p2) -> Integer.compare(p2.getPosition(), p1.getPosition()));

                for (int i = 0; i < finalPlayers.size(); i++) {
                    Player player = finalPlayers.get(i);
                    String position = (i == 0) ? "🥇 Winner" : "#" + (i + 1);
                    System.out.println(position + ": " + player.getName() +
                            " (Position: " + player.getPosition() + ")");
                }
            }

        } catch (Exception e) {
            System.err.println("An error occurred during the game: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Demonstration method to show automated gameplay
     */
    public static void runDemo() {
        System.out.println(GAME_TITLE);
        System.out.println("DEMO MODE - Automated Gameplay");
        System.out.println("=".repeat(50));

        // Create demo players
        List<Player> players = List.of(
                new Player("Alice", 1),
                new Player("Bob", 2),
                new Player("Charlie", 3));

        // Create board and game
        Board board = BoardFactory.createStandardBoard(100);
        Game game = new Game(board, players);

        // Play the complete game automatically
        Player winner = game.playGame();

        System.out.println("Demo completed!");
        System.out.println("Winner: " + winner.getName());
    }

    /**
     * Main method - entry point of the application
     */
    public static void main(String[] args) {
        if (args.length > 0 && args[0].equals("--demo")) {
            runDemo();
        } else {
            SnakesAndLaddersGame gameApp = new SnakesAndLaddersGame();
            gameApp.run();
        }
    }
}
