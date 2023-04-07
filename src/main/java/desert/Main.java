package main.java.desert;
import org.tinylog.Logger;

import javax.net.ssl.CertPathTrustManagerParameters;


/**
 * Eintrittspunkt des Programms.
 */
public class Main {
    public static void main(String[] args) {
        // SKELETON TEST
        Kontroller kontroller = new Kontroller(100);

        //kontroller.spielStarten();

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
        System.out.println("\nStep:");
        Pumpe pumpe3 = new Pumpe(2);
        Rohr rohr3 = new Rohr();
        Spieler spieler3 = new Installateur(pumpe3, kontroller);
        pumpe3.addRohr(rohr3);
        spieler3.step(rohr3);

        // Pumpe aufnehmen
        System.out.println("\nPumpe aufnehmen:");
        Zisterne zisterne4 = new Zisterne();
        Installateur spieler4 = new Installateur(zisterne4.getPumpe(), kontroller);
        spieler4.pumpeAufnehmen();

        // Rohr einbinden
        System.out.println("\nRohr einbinden:");
        Pumpe pumpe5_1 = new Pumpe(2);
        Pumpe pumpe5_2 = new Pumpe(2);
        Pumpe pumpe5_3 = new Pumpe(2);
        Rohr rohr5 = new Rohr();
        rohr5.getEndPumpen().add(pumpe5_1);
        rohr5.getEndPumpen().add(pumpe5_2);

        pumpe5_1.setAusgangsRohr(rohr5);
        pumpe5_2.setEingangsRohr(rohr5);

        Installateur spieler5 = new Installateur(pumpe5_2, kontroller);
        spieler5.rohrEinbinden(pumpe5_1, pumpe5_3, rohr5);

        // Rohr mit freiem Ende binden
        System.out.println("\nRohr mit freiem Ende binden:");
        Pumpe pumpe6_1 = new Pumpe(2);
        Pumpe pumpe6_2 = new Pumpe(2);
        Rohr rohr6 = new Rohr();
        rohr6.getEndPumpen().add(pumpe6_2);
        pumpe6_2.addRohr(rohr6);

        Installateur spieler6 = new Installateur(pumpe6_2, kontroller);
        spieler6.step(rohr6);
        spieler6.rohrMitFreiemEndeBinden(pumpe6_1);

        // Reparieren
        System.out.println("\nReparieren:");
        Pumpe pumpe7 = new Pumpe(2);
        pumpe7.setIstKaputt(true);

        Installateur spieler7 = new Installateur(pumpe7, kontroller);
        spieler7.reparieren();

        // Pumpe einmontieren
        System.out.println("\nPumpe einmontieren:");
        Pumpe pumpe8 = new Pumpe(2);
        Rohr rohr8 = new Rohr();
        rohr8.getEndPumpen().add(pumpe8);
        pumpe8.addRohr(rohr8);

        Installateur spieler8 = new Installateur(pumpe8, kontroller);
        spieler8.setPumpeInHand(new Pumpe(2));

        spieler8.step(rohr8);
        spieler8.pumpeEinmontieren(false);

        // Rohr löchern
        System.out.println("\nRohr löchern");
        Pumpe pumpe9 = new Pumpe(2);
        Rohr rohr9 = new Rohr();

        pumpe9.addRohr(rohr9);

        Saboteur spieler9 = new Saboteur(pumpe9, kontroller);

        spieler9.step(rohr9);
        spieler9.rohrLochern();

        // Spiel starten
        System.out.println("\nSpiel starten");
        kontroller.spielStarten();

        // Pumpe kaputt machen
        System.out.println("\nPumpe kaputt machen");
        Pumpe pumpe11 = new Pumpe(2);
        kontroller.addPumpe(pumpe11);
        kontroller.pumpeKaputtMachen();
    }
}
