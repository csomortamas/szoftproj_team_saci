package main.java.desert;
import lombok.Getter; import lombok.Setter;
import org.tinylog.Logger;
import java.util.List;
import java.util.Random;

/**
 *
 */
public class Kontroller {
    /**
     *
     */
    @Getter @Setter protected List<Pumpe> allePumpen;
    /**
     *
     */
    @Getter @Setter protected List<Zisterne> alleZisternen;

    /**
     *
     */
    @Getter @Setter protected List<Rohr> alleRohre;

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
        System.out.println("Kontroller Object erstellt mit Konstruktor");
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
        Pumpe neuePumpe = new Pumpe();
        Logger.info("Pumpe wurde erstellt");
        return neuePumpe;
    }

    /**
     * @param leaderboard
     *
     */
    public void punkteKalkulieren(Leaderboard leaderboard) {
        for(int i = 0; i < alleZisternen.size(); i++) {
            for(int j = 0; j < alleZisternen.get(i).getRohre().size(); j++) {
                if(alleZisternen.get(i).getRohre().get(j).isIstAktiv() && !alleZisternen.get(i).getRohre().get(j).isIstKaputt()) {
                    leaderboard.setPunkteTeam1(leaderboard.getPunkteTeam1() + alleZisternen.get(i).getRohre().get(j).getFlusswert());
                }
            }
        }
    }

    /**
     *
     */
    public void spielStarten() {
        // TODO implement here
    }

    /**
     * @param zisterne
     *      */
    public void addZisterne(Zisterne zisterne) {
        // TODO implement here
    }

    /**
     * @param pumpe
     *
     */
    public void addPumpe(Pumpe pumpe) {
        // TODO implement here
    }
}
