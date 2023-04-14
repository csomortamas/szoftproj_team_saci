package main.java.desert;
import lombok.Getter;
import lombok.Setter;
import org.tinylog.Logger;

import java.util.ArrayList;

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
    public Installateur(Pumpe startPunkt, Kontroller kontroller) {

    }
    
    /**
     *
     */
    public void reparieren() {

    }

    /**
     * @param pumpeWohin
     *
     */
    public void rohrMitFreiemEndeBinden(Pumpe pumpeWohin) {

    }

    /**
     * @param middle
     *
     */
    public void pumpeEinmontieren(boolean middle) {

    }

    /**
     * @param pumpeWohin
     * @param pumpeWoher
     * @param rohr
     *
     */
    public void rohrEinbinden(Pumpe pumpeWoher, Pumpe pumpeWohin, Rohr rohr) {

    }

    /**
     *
     */
    public void pumpeAufnehmen() {

    }
}
