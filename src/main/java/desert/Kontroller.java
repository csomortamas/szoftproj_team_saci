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
    private Kontroller kontroller;
    public Kontroller(){}
    public Kontroller getKontroller(){
        return this.kontroller;
    }
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

    }

    /**
     *
     */
    public void spielStarten() {

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

    }

    /**
     * @param rohr
     *
     */
    public void addRohr(Rohr rohr) {

    }
}
