package com.example.game;

/**
 * Starter demo using only the base character.
 * TODOs guide you to implement decorators and compose them.
 */
public class GameDemo {
    public static void main(String[] args) {
        Character base = new BaseCharacter();

        System.out.println("--- Base ---");
        base.move();
        base.attack();

        System.out.println("\n=== Decorator Pattern Power-ups ===\n");
        
        // a) Base + SpeedBoost + DamageBoost
        System.out.println("--- Base + SpeedBoost(+3) + DamageBoost(+15) ---");
        Character buffed = new DamageBoost(new SpeedBoost(base, 3), 15);
        System.out.println("Stats: Speed=" + buffed.getSpeed() + ", Damage=" + buffed.getDamage() + ", Sprite=" + buffed.getSprite());
        buffed.move();
        buffed.attack();
        
        // b) Add GoldenAura (sprite change + buffs)
        System.out.println("\n--- Adding GoldenAura (sprite change + small buffs) ---");
        Character shiny = new GoldenAura(buffed);
        System.out.println("Stats: Speed=" + shiny.getSpeed() + ", Damage=" + shiny.getDamage() + ", Sprite=" + shiny.getSprite());
        shiny.move();
        shiny.attack();
        
        // c) Remove GoldenAura by recomposing (rebuild chain without it)
        System.out.println("\n--- Removing GoldenAura (recomposition) ---");
        Character withoutAura = buffed; // reference to the buffed character without golden aura
        System.out.println("Stats: Speed=" + withoutAura.getSpeed() + ", Damage=" + withoutAura.getDamage() + ", Sprite=" + withoutAura.getSprite());
        withoutAura.move();
        withoutAura.attack();
        
        // Bonus: Show stacking multiple same decorators
        System.out.println("\n--- Bonus: Double Speed Boost ---");
        Character superFast = new SpeedBoost(new SpeedBoost(base, 5), 5);
        System.out.println("Stats: Speed=" + superFast.getSpeed() + ", Damage=" + superFast.getDamage() + ", Sprite=" + superFast.getSprite());
        superFast.move();
        
        // Bonus: Complex composition
        System.out.println("\n--- Bonus: Ultimate Character ---");
        Character ultimate = new GoldenAura(
            new DamageBoost(
                new SpeedBoost(
                    new DamageBoost(base, 10), 
                    8
                ), 
                20
            )
        );
        System.out.println("Stats: Speed=" + ultimate.getSpeed() + ", Damage=" + ultimate.getDamage() + ", Sprite=" + ultimate.getSprite());
        ultimate.move();
        ultimate.attack();
    }
}
