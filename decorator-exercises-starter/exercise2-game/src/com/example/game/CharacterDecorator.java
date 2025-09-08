package com.example.game;

/**
 * Base decorator for Character implementations.
 * Provides common structure for all character enhancement decorators.
 */
public abstract class CharacterDecorator implements Character {
    protected Character character;
    
    public CharacterDecorator(Character character) {
        this.character = character;
    }
    
    @Override
    public void move() {
        character.move();
    }
    
    @Override
    public void attack() {
        character.attack();
    }
    
    @Override
    public int getSpeed() {
        return character.getSpeed();
    }
    
    @Override
    public int getDamage() {
        return character.getDamage();
    }
    
    @Override
    public String getSprite() {
        return character.getSprite();
    }
}
