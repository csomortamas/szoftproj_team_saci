package main.java.desert;
import org.tinylog.Logger;


/**
 * Eintrittspunkt des Programms.
 */
public class Main {
    public static void main(String[] args) {
        // SKELETON TEST
        Kontroller kontroller = new Kontroller(100);

        // Pumpe starten
        System.out.println("Pumpe starten:");
        Pumpe pumpe1 = new Pumpe(2);
        Spieler spieler1 = new Installateur(pumpe1, kontroller);
        spieler1.pumpeStarten();

        // Pumpe stoppen
        System.out.println("\nPumpe stoppen:");
        Pumpe pumpe2 = new Pumpe(3);
        Spieler spieler2 = new Installateur(pumpe2, kontroller);
        spieler2.pumpeStarten();
        spieler2.pumpeStoppen();

        // Step
        System.out.println("\nStep");
        Pumpe pumpe3 = new Pumpe(2);
        Rohr rohr3 = new Rohr();
        Spieler spieler3 = new Installateur(pumpe3, kontroller);

    }
}
