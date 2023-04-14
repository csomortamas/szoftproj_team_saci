package main.java.desert;
import lombok.Getter;
import lombok.Setter;
import org.tinylog.Logger;

import java.util.List;

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
    public Installateur(Wasserquelle startPunkt) {
        super(startPunkt);
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
        if(middle && position instanceof Rohr) {
            List<Rohr> alleRohre = Kontroller.getKontroller().getAlleRohre();
            for(Rohr rohr : alleRohre) {
                if(position == rohr)
                    rohr.rohrSplit(pumpeInHand);
            }
        }

        Kontroller.getKontroller().addPumpe(pumpeInHand);
        pumpeInHand = null;
    }

    /**
     *
     */
    public void pumpeAufnehmen() {
        List<Zisterne> zisterneList = Kontroller.getKontroller().getAlleZisternen();

        for(Zisterne zisterne : zisterneList) {
            if(position == zisterne && zisterne.getPumpeZurVerfuegung() != null) {
                pumpeInHand = zisterne.getPumpeZurVerfuegung();
                zisterne.setPumpeZurVerfuegung(null);
                Logger.info("Pumpe aufgenommen.");
            }
        }
    }
}
