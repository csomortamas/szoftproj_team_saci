package main.java.desert;
import lombok.Getter; import lombok.Setter;
import org.tinylog.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 *
 */
public class Kontroller {
    /**
     *
     */
    @Getter protected List<Pumpe> allePumpen;
    /**
     *
     */
    @Getter protected List<Zisterne> alleZisternen;

    /**
     *
     */
    @Getter protected List<Rohr> alleRohre;

    /**
     *
     */
    @Getter @Setter private int aktuelleRunde;

    /**
     *
     */
    @Getter @Setter private int maxRunde;

    /**
     *
     */
    @Getter @Setter private String team1;

    /**
     *
     */
    @Getter @Setter public String team2;

    /**
     * Konstruktor
     */
    public Kontroller(int maxRunde) {
        this.maxRunde = maxRunde;
        this.aktuelleRunde = 1;
        allePumpen = new ArrayList<>();
        alleRohre = new ArrayList<>();
        alleZisternen = new ArrayList<>();

        Logger.info("Kontroller erstellt");
    }

    /**
     *
     */
    public void pumpeKaputtMacht() {
        Random rand = new Random();
        int randomNumber = rand.nextInt(10);
        int randomPumpeIndex = rand.nextInt(allePumpen.size());

        // Pumpe Random kaputt machen
        if (allePumpen.size() > 0 && randomNumber == 1) {
            allePumpen.get(randomPumpeIndex).setIstKaputt(true);
            Logger.info("Eine Pumpe ist kaputt gegangen");
        } else {
            Logger.info("Keine Pumpe ist kaputt gegangen");
        }
    }

    /**
     *
     */
    public void wasserTankLaden() {
        for(int i = 0; i < allePumpen.size(); i++) {
            Pumpe aktuellePumpe = allePumpen.get(i);
            aktuellePumpe.tankFuellen();
            Logger.info("Der Tank einer Pumpe wird gefüllt");
        }
    }

    /**
     * @return
     */
    public Pumpe pumpeErstellen() {
        Pumpe neuePumpe = new Pumpe(4);
        Logger.info("Pumpe wurde erstellt");
        return neuePumpe;
    }

    /**
     * @param leaderboard
     *
     */
    public void punkteKalkulieren(Leaderboard leaderboard) {
        int punkteToInstallateure = 0;
        for(int i = 0; i < alleZisternen.size(); i++) {
            punkteToInstallateure = alleZisternen.get(i).getPumpe().getAusgangsRohr().getFlusswert();
                    leaderboard.setPunkteTeam1(leaderboard.getPunkteTeam1() + punkteToInstallateure);
                    Logger.info(punkteToInstallateure + "Punkte zu Installateur");

        }

        for(int i = 0; i < alleRohre.size(); i++) {
            if(alleRohre.get(i).isIstAktiv() && alleRohre.get(i).isIstKaputt()) {
                leaderboard.setPunkteTeam2(leaderboard.getPunkteTeam2() + alleRohre.get(i).getFlusswert());
                Logger.info(alleRohre.get(i).getFlusswert() + " Punkte für Saboteure");
            }
        }
    }

    /**
     *
     */
    public void spielStarten() {
        Leaderboard leaderboard = new Leaderboard("TestInstallTeam", "TestSaboTeam");
        team1 = leaderboard.getSpielendeTeam1Name();
        team2 = leaderboard.getSpielendeTeam2Name();

        alleZisternen.add(new Zisterne());
        allePumpen.add(this.pumpeErstellen());
        allePumpen.add(this.pumpeErstellen());

        Wasserquelle wasserquelle = new Wasserquelle();
        //wasserquelle.wasserStarten(); Ez nem fog kelleni, mert a konstruktor meghívja majd a wasserStartent és az úgy marad a játék végéig, hogy a wasserQuelle rohrjába ami a konstans pumpához van kötve. Ad neki flussWertet.

        Spieler InstallPlayer = new Installateur(allePumpen.get(0), this);
        Spieler SaboPlayer = new Saboteur(allePumpen.get(0), this);

        Logger.info("Spiel wurde gestartet");
    }

    /**
     * @param zisterne
     *      */
    public void addZisterne(Zisterne zisterne) {
        alleZisternen.add(zisterne);

        Logger.info("Eine Zisterne wurde hinzugefügt");
    }

    /**
     * @param pumpe
     *
     */
    public void addPumpe(Pumpe pumpe) {
        allePumpen.add(pumpe);

        Logger.info("Eine Pumpe wurde hinzugefügt");
    }

    /**
     * @param rohr
     *
     */
    public void addRohr(Rohr rohr) {
        alleRohre.add(rohr);

        Logger.info("Ein Rohr wurde hinzugefügt");
    }
}
