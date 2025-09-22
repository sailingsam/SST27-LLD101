package com.snakesladders.model;

/**
 * Represents a player in the Snakes and Ladders game
 */
public class Player {
    private final String name;
    private int position;
    private final int playerId;

    public Player(String name, int playerId) {
        this.name = name;
        this.playerId = playerId;
        this.position = 0; // Starting position outside the board
    }

    public String getName() {
        return name;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getPlayerId() {
        return playerId;
    }

    public void moveBy(int steps) {
        this.position += steps;
    }

    @Override
    public String toString() {
        return "Player{" +
                "name='" + name + '\'' +
                ", position=" + position +
                ", playerId=" + playerId +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        Player player = (Player) obj;
        return playerId == player.playerId;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(playerId);
    }
}
