package main.java.desert;
import org.tinylog.Logger;


/**
 * Eintrittspunkt des Programms.
 */
public class Main {
    public static void main(String[] args) {
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

        // spieler step
        installateur.step(rohr1);
        saboteur.step(rohr2);

        saboteur.position.kaputtMachen();

        Kontroller.getKontroller().tick();

        System.out.println("Saboteur: " + Kontroller.getKontroller().getSaboteurPunkte());
        System.out.println("Installateur: " + Kontroller.getKontroller().getInstallateurPunkte());
    }
}
