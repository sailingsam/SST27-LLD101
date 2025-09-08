package com.example.game;

/**
 * Decorator that adds golden aura effect with sprite change and small buffs.
 */
public class GoldenAura extends CharacterDecorator {
    private final int speedBonus = 2;
    private final int damageBonus = 5;
    
    public GoldenAura(Character character) {
        super(character);
    }
    
    @Override
    public void move() {
        System.out.println("Moving at speed " + getSpeed() + " with sprite " + getSprite() + " ✨ [GOLDEN AURA GLOWING] ✨");
    }
    
    @Override
    public void attack() {
        System.out.println("Attacking with damage " + getDamage() + " using sprite " + getSprite() + " ⚡ [GOLDEN POWER STRIKE] ⚡");
    }
    
    @Override
    public int getSpeed() {
        return character.getSpeed() + speedBonus;
    }
    
    @Override
    public int getDamage() {
        return character.getDamage() + damageBonus;
    }
    
    @Override
    public String getSprite() {
        return "golden_" + character.getSprite();
    }
}
