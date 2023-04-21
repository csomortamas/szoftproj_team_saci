package main.java.desert;
import main.java.desert.control.Kontroller;
import main.java.desert.network.Pumpe;
import main.java.desert.network.Rohr;
import main.java.desert.network.Wasserquelle;
import main.java.desert.network.Zisterne;
import main.java.desert.player.Installateur;
import main.java.desert.player.Saboteur;
import main.java.desert.player.Spieler;


/**
 * Eintrittspunkt des Programms.
 */
public class Main {
    public static void main(String[] args) {
        // SETUP GAME MAP
        // create wasserquelle
        Wasserquelle wasserquelle = new Wasserquelle();

        // create 1 zisterne
        Zisterne zisterne = new Zisterne();

        // create 1 pumpe
        Pumpe pumpe = new Pumpe();

        // create 6 rohr
        Rohr rohr1 = new Rohr();
        Rohr rohr2 = new Rohr();

        // create 1 spieler
        Spieler installateur = new Installateur(wasserquelle);
        Spieler saboteur = new Saboteur(zisterne);

        Kontroller.getKontroller().setup(wasserquelle, zisterne, pumpe, rohr1, rohr2, installateur, saboteur);

        //Kontroller.getKontroller().binden(rohr1, wasserquelle, pumpe);
        //Kontroller.getKontroller().binden(rohr2, pumpe, zisterne);

        // START SPIEL
        // spieler step
        installateur.step(rohr1); // steht auf rohr1
        saboteur.step(rohr2); // steht auf rohr2

        // tick (runde beenden, fluss kalkulieren, punkte hinzufügen)
        Kontroller.getKontroller().tick();

        // tick (runde beenden, fluss kalkulieren, punkte hinzufügen)
        Kontroller.getKontroller().tick();

        // kaputt rohr2 kaputt machen
        saboteur.getPosition().kaputtMachen();

        // tick (runde beenden, fluss kalkulieren, punkte hinzufügen)
        Kontroller.getKontroller().tick();

        // spieler step
        installateur.step(pumpe); // steht auf der Pumpe
        saboteur.step(zisterne); // steht auf Zisterne
        installateur.step(rohr2); // steht auf rohr2

        // rohr2 reparieren
        installateur.getPosition().reparieren();

        // runde beenden
        Kontroller.getKontroller().tick();

        System.out.println("Saboteur: " + Kontroller.getKontroller().getSaboteurPunkte());
        System.out.println("Installateur: " + Kontroller.getKontroller().getInstallateurPunkte());
    }
}
