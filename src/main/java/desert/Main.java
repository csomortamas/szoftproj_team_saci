package main.java.desert;
import org.tinylog.Logger;


/**
 * Eintrittspunkt des Programms.
 */
public class Main {
    public static void main(String[] args) {
        // SKELETON TEST

        // INIT
        Kontroller kontroller = new Kontroller(100);
        Leaderboard leaderboard = new Leaderboard("Installateur", "Saboteur");

        Wasserquelle wasserquelle = new Wasserquelle();
        Zisterne zisterne = new Zisterne();


    }
}
