package main.java.desert;
import lombok.Getter;
import lombok.Setter;
import org.tinylog.Logger;
import java.util.List;

public class Pumpe extends Netzelement {
    /**
     *
     */
    @Getter @Setter private List<Rohr> rohre;

    /**
     *
     */
    @Getter @Setter private Rohr eingangsRohr;
    /**
     *
     */
    @Getter @Setter private Rohr ausgangsRohr;


    /**
     *
     */
    @Getter @Setter private int wasserTank;

    /**
     *
     */


    @Getter @Setter private int maxRohrAnzahl;

    /**
     * Default Konstruktor
     */
    public Pumpe() {
    }

    /**
     *
     */
    public void tankFuellen() {
        // TODO implement here
    }

    /**
     * @param rohr
     */
    public void addRohr(Rohr rohr) {
        // TODO implement here
    }

    /**
     *
     */
    public void flussKalkulierenn() {
        // TODO implement here
    }
}
