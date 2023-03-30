package main.java.desert;

import java.util.List;

/**
 *
 */
public class Kontroller {
    /**
     *
     */
    protected List<Pumpe> allePumpen;
    /**
     *
     */
    protected List<Zisterne> alleZisternen;

    /**
     *
     */
    private int aktuelleRunde;

    /**
     *
     */
    private int maxRunde;

    /**
     *
     */
    private String team1;

    /**
     *
     */
    public String team2;

    /**
     * Default Konstruktor
     */
    public Kontroller(int maxRunde) {
        this.maxRunde = maxRunde;
        System.out.println("Kontroller Object erstellt mit Konstruktor");
    }

    /**
     *
     */
    public void pumpeKaputtMacht() {
        // TODO implement here
    }

    /**
     *
     */
    public void wasserTankLaden() {
        // TODO implement here
    }

    /**
     * @return
     */
    public Pumpe pumpeErstellen() {
        // TODO implement here
        return null;
    }

    /**
     * @param leaderboard
     *
     */
    public void punkteKalkulieren(Leaderboard leaderboard) {
        // TODO implement here
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
