package com.snakesladders.game;

import com.snakesladders.model.*;
import java.util.*;

/**
 * Main game engine that manages the Snakes and Ladders game
 */
public class Game {
    private final Board board;
    private final List<Player> players;
    private final Dice dice;
    private int currentPlayerIndex;
    private GameState gameState;
    private Player winner;

    public Game(Board board, List<Player> players) {
        this(board, players, new Dice());
    }

    public Game(Board board, List<Player> players, Dice dice) {
        if (board == null) {
            throw new IllegalArgumentException("Board cannot be null");
        }
        if (players == null || players.isEmpty()) {
            throw new IllegalArgumentException("Players list cannot be null or empty");
        }
        if (players.size() < 2) {
            throw new IllegalArgumentException("At least 2 players required");
        }
        if (dice == null) {
            throw new IllegalArgumentException("Dice cannot be null");
        }

        this.board = board;
        this.players = new ArrayList<>(players);
        this.dice = dice;
        this.currentPlayerIndex = 0;
        this.gameState = GameState.NOT_STARTED;
        this.winner = null;
    }

    /**
     * Start the game
     */
    public void startGame() {
        if (gameState != GameState.NOT_STARTED) {
            throw new IllegalStateException("Game has already been started");
        }
        gameState = GameState.IN_PROGRESS;
        System.out.println("Game Started!");
        System.out.println("Players: " + players.size());
        for (Player player : players) {
            System.out.println("- " + player.getName());
        }
        board.displayBoard();
        System.out.println();
    }

    /**
     * Play one turn for the current player
     */
    public boolean playTurn() {
        if (gameState != GameState.IN_PROGRESS) {
            throw new IllegalStateException("Game is not in progress");
        }

        Player currentPlayer = players.get(currentPlayerIndex);
        int diceRoll = dice.roll();

        System.out.println(currentPlayer.getName() + "'s turn:");
        System.out.println("Current position: " + currentPlayer.getPosition());
        System.out.println("Dice roll: " + diceRoll);

        // Calculate new position
        int newPosition = currentPlayer.getPosition() + diceRoll;

        // Check if player exceeds board size (some variations allow this, others don't)
        if (newPosition > board.getSize()) {
            System.out.println("Cannot move - would exceed board size!");
            System.out.println("Staying at position: " + currentPlayer.getPosition());
        } else {
            currentPlayer.setPosition(newPosition);
            System.out.println("Moved to position: " + newPosition);

            // Check for snakes and ladders
            int finalPosition = board.getFinalPosition(newPosition);
            if (finalPosition != newPosition) {
                currentPlayer.setPosition(finalPosition);
                if (finalPosition < newPosition) {
                    System.out.println("Oops! Snake bite! Moved down to: " + finalPosition);
                } else {
                    System.out.println("Great! Climbed ladder! Moved up to: " + finalPosition);
                }
            }

            // Check for win condition
            if (board.isWinningPosition(currentPlayer.getPosition())) {
                winner = currentPlayer;
                gameState = GameState.FINISHED;
                System.out.println("🎉 " + currentPlayer.getName() + " WINS! 🎉");
                return true;
            }
        }

        System.out.println("Final position: " + currentPlayer.getPosition());
        System.out.println();

        // Move to next player
        currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
        return false;
    }

    /**
     * Play the complete game until someone wins
     */
    public Player playGame() {
        startGame();

        while (gameState == GameState.IN_PROGRESS) {
            boolean gameEnded = playTurn();
            if (gameEnded) {
                break;
            }
        }

        return winner;
    }

    /**
     * Get current game status
     */
    public void displayGameStatus() {
        System.out.println("=== Game Status ===");
        System.out.println("State: " + gameState);
        System.out.println("Players:");
        for (int i = 0; i < players.size(); i++) {
            Player player = players.get(i);
            String marker = (i == currentPlayerIndex && gameState == GameState.IN_PROGRESS) ? " <- Current" : "";
            System.out.println("  " + player.getName() + ": Position " + player.getPosition() + marker);
        }
        if (winner != null) {
            System.out.println("Winner: " + winner.getName());
        }
        System.out.println("==================");
    }

    // Getters
    public Board getBoard() {
        return board;
    }

    public List<Player> getPlayers() {
        return new ArrayList<>(players);
    }

    public Dice getDice() {
        return dice;
    }

    public Player getCurrentPlayer() {
        if (gameState != GameState.IN_PROGRESS) {
            return null;
        }
        return players.get(currentPlayerIndex);
    }

    public GameState getGameState() {
        return gameState;
    }

    public Player getWinner() {
        return winner;
    }

    public boolean isGameFinished() {
        return gameState == GameState.FINISHED;
    }
}
