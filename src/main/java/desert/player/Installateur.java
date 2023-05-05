package main.java.desert.player;
import lombok.Getter;
import lombok.Setter;
import main.java.desert.control.Kontroller;
import main.java.desert.network.Pumpe;
import main.java.desert.network.Rohr;
import main.java.desert.network.Wasserquelle;
import main.java.desert.network.Zisterne;
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

    public void reparieren() {
        this.position.reparieren();
    }

    /**
     * @param middle
     *
     */
    public void pumpeEinmontieren(boolean middle) {

        if(middle && position instanceof Rohr && this.pumpeInHand!=null) {
            List<Rohr> alleRohre = Kontroller.getKontroller().getMap().getRohre();
            for(Rohr rohr : alleRohre) {
                if(position == rohr) {
                    rohr.rohrSplit(pumpeInHand);
                    this.setPosition(pumpeInHand);
                    Kontroller.getKontroller().addPumpe(pumpeInHand);
                    pumpeInHand = null;
                    Logger.info("Pumpe eingebaut.");
                    break;
                }
            }
        } else if(!middle && position instanceof Rohr && this.pumpeInHand!=null){
            Kontroller.getKontroller().addPumpe(pumpeInHand);
            pumpeInHand = null;
            Logger.info("Pumpe eingebaut.");
        }else {
            Logger.error("Pumpe kann nicht eingebaut werden.");
        }
    }

    /**
     *
     */
    public void pumpeAufnehmen() {
        List<Zisterne> zisterneList = Kontroller.getKontroller().getMap().getZisternen();

        for(Zisterne zisterne : zisterneList) {
            if(position == zisterne && zisterne.getPumpeZurVerfuegung() != null) {
                pumpeInHand = zisterne.getPumpeZurVerfuegung();
                zisterne.setPumpeZurVerfuegung(null);
                Logger.info("Pumpe aufgenommen.");
                return;
            }
        }
        Logger.error("Keine Pumpe aufgenommen.");
    }
}
