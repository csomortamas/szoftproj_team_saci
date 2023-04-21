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
        Rohr rohr3 = new Rohr();

        // create 1 spieler
        Spieler installateur = new Installateur(wasserquelle);
        Spieler saboteur = new Saboteur(zisterne);

        Kontroller.getKontroller().setup(wasserquelle, zisterne, pumpe1, pumpe2, rohr1, rohr2, rohr3, installateur, saboteur);

        //Kontroller.getKontroller().binden(rohr1, wasserquelle, pumpe);
        //Kontroller.getKontroller().binden(rohr2, pumpe, zisterne);

        // spiel starten
        installateur.step(rohr1);
        saboteur.step(rohr3);
        saboteur.step(pumpe1);
        saboteur.step(rohr1);
        //installateur.umbinden(pumpe1, pumpe2);
        //installateur.step(pumpe2);
        //installateur.eingangsRohrUmstellen(rohr1);
        //installateur.ausgangsRohrUmstellen(rohr3);

        Kontroller.getKontroller().tick();
        Kontroller.getKontroller().tick();
        Kontroller.getKontroller().tick();


        System.out.println("Saboteur: " + Kontroller.getKontroller().getSaboteurPunkte());
        System.out.println("Installateur: " + Kontroller.getKontroller().getInstallateurPunkte());
    }
}
