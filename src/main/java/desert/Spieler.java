package main.java.desert;
import lombok.Getter;
import lombok.Setter;
import org.tinylog.Logger;
/**
 *
 */
public abstract class Spieler {
    /**
     *
     */
    @Getter @Setter protected Pumpe aktuellePumpe;
    /**
     *
     */
    @Getter @Setter protected Rohr aktuelleRohr;

    /**
     *
     */
    @Getter @Setter Kontroller kontroller;


    public Spieler(Netzelement startPunkt, Kontroller kontroller) {

    }

    /**
     *
     */
    public void pumpeStarten() {

    }

    /**
     * @param rohr
     */
    public void eingangsRohrUmstellen(Rohr rohr) {

    }

    /**
     * @param rohr
     *
     */
    public void ausgangsRohrUmstellen(Rohr rohr) {

    }

    /**
     *
     */
    public void pumpeStoppen() {

    }

    /**
     * @param netzwerkElement
     */
    public void step(Netzelement netzwerkElement) {

    }
}
