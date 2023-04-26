package main.java.desert;
import main.java.desert.control.Kontroller;
import main.java.desert.network.Pumpe;
import main.java.desert.network.Rohr;
import main.java.desert.network.Wasserquelle;
import main.java.desert.network.Zisterne;
import main.java.desert.player.Installateur;
import main.java.desert.player.Saboteur;
import main.java.desert.player.Spieler;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;


/**
 * Eintrittspunkt des Programms.
 */
public class Main {
    public static void main(String[] args) {
        // SETUP GAME MAP
        // create wasserquelle
        Wasserquelle wasserquelle1 = new Wasserquelle();
        wasserquelle1.setName("Q1");
        Wasserquelle wasserquelle2 = new Wasserquelle();
        wasserquelle2.setName("Q2");
        Wasserquelle wasserquelle3 = new Wasserquelle();
        wasserquelle3.setName("Q3");
        List<Wasserquelle> wasserquelleList = new ArrayList<>();
        wasserquelleList.add(wasserquelle1);
        wasserquelleList.add(wasserquelle2);
        wasserquelleList.add(wasserquelle3);

        // create 3 zisterne
        Zisterne zisterne1 = new Zisterne();
        zisterne1.setName("Z1");
        Zisterne zisterne2 = new Zisterne();
        zisterne2.setName("Z2");
        Zisterne zisterne3 = new Zisterne();
        zisterne3.setName("Z3");
        List<Zisterne> zisterneList = new ArrayList<>();
        zisterneList.add(zisterne1);
        zisterneList.add(zisterne2);
        zisterneList.add(zisterne3);

        // create 1 pumpe
        Pumpe pumpe1 = new Pumpe();
        pumpe1.setName("P1");
        Pumpe pumpe2 = new Pumpe();
        pumpe2.setName("P2");
        Pumpe pumpe3 = new Pumpe();
        pumpe3.setName("P3");
        List<Pumpe> pumpeList=new ArrayList<>();
        pumpeList.add(pumpe1);
        pumpeList.add(pumpe2);
        pumpeList.add(pumpe3);

        // create 5 rohr
        Rohr rohr1 = new Rohr();
        Rohr rohr2 = new Rohr();
        Rohr rohr3 = new Rohr();
        Rohr rohr4 = new Rohr();
        Rohr rohr5 = new Rohr();
        rohr1.setName("R1");
        rohr2.setName("R2");
        rohr3.setName("R3");
        rohr4.setName("R4");
        rohr5.setName("R5");

        List<Rohr> rohrList=new ArrayList<>();
        rohrList.add(rohr1);
        rohrList.add(rohr2);
        rohrList.add(rohr3);
        rohrList.add(rohr4);
        rohrList.add(rohr5);


        Scanner sc = new Scanner(System.in);
        System.out.print("Anzahl der Spielern pro Team: ");
        int spielerAnzahl = sc.nextInt();
        Random rand = new Random();
        for(int i = 0; i < spielerAnzahl; i++){
            //int  randomNum = rand.nextInt(3);
            Installateur installateur=new Installateur(wasserquelleList.get(i%3));
            installateur.setName("Installateur"+(i+1));
            Kontroller.getKontroller().getInstallateurTeam().add(installateur);


            Saboteur sb = new Saboteur(zisterneList.get(i%3));
            sb.setName("Saboteur"+(i+1));
            Kontroller.getKontroller().getSaboteurTeam().add(sb);

        }

        Kontroller.getKontroller().setup(wasserquelleList, zisterneList, pumpeList, rohrList, "Installateure", "Saboteure");
        Kontroller.getKontroller().game();
        // spiel starten
    }
}

