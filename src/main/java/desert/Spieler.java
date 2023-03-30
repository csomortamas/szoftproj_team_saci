package main.java.desert;
import org.tinylog.Logger;
/**
 *
 */
public abstract class Spieler {
    /**
     * Aktuelle Position der Spieler.
     */
    protected Netzelement akutellePosition;
    /**
     * Default Konstruktor
     */
    public Spieler() {
    }

    /**
     *
     */
    public void pumpeStarten() {
        // TODO implement here
    }

    /**
     * @param rohr
     *
     */
    public void eingangsRohrUmstellen(Rohr rohr) {
        // TODO implement here
    }

    /**
     * @param rohr
     *
     */
    public void ausgangsRohrUmstellen(Rohr rohr) {
        // TODO implement here
    }

    /**
     *
     */
    public void pumpeStoppen() {
        // TODO implement here
    }

    /**
     * @param netzwerkElement
     *
     */
    public void step(Netzelement netzwerkElement) {
        // TODO implement here
    }
}
