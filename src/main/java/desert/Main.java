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

        Kontroller.getKontroller().setup(wasserquelleList, zisterneList, pumpeList, rohrList);
        Kontroller.getKontroller().game();
        // spiel starten
    }
}
