package main.java.desert;
import lombok.Getter;
import lombok.Setter;
import org.tinylog.Logger;
/**
 *
 */
public class Installateur extends Spieler {
    /**
     * Objekt von Pumpe in der Hand von der Installateur.
     */
    @Getter @Setter private Pumpe pumpeInHand;
    /**
     * Default Konstruktor
     */
    public Installateur() {
    }
    
    /**
     *
     */
    public void reparieren() {
        // TODO implement here
    }

    /**
     *
     */
    public void rohrReparieren() {
        // TODO implement here
    }

    /**
     * @param pumpeWohin
     *
     */
    public void rohrMitFreiemEndeBinden(Pumpe pumpeWohin) {
        // TODO implement here
    }

    /**
     * @param middle
     *
     */
    public void pumpeEinmontieren(boolean middle) {
        // TODO implement here
    }

    /**
     * @param pumpeWohin
     * @param pumpeWoher
     * @param rohr
     *
     */
    public void rohrEinbinden(Pumpe pumpeWohin, Pumpe pumpeWoher, Rohr rohr) {
        // TODO implement here
    }

    /**
     *
     */
    public void pumpeAufnehmen() {
        // TODO implement here
    }
}
