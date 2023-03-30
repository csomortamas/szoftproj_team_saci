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

    private Kontroller kontroller;

    /**
     * Default Konstruktor
     */
    public Installateur(Pumpe startPunkt, Kontroller k) {
        aktuellePumpe = startPunkt;
        kontroller = k;
        Logger.info("Installateur erstellt!");
    }
    
    /**
     *
     */
    public void reparieren() {
        if (aktuelleRohr!=null){
            aktuelleRohr.setIstKaputt(false);
            Logger.info("Rohr repariert!");
        } else{
            aktuellePumpe.setIstKaputt(false);
            Logger.info("Pumpe repariert!");
        }
    }


    /**
     * @param pumpeWohin
     *
     */
    public void rohrMitFreiemEndeBinden(Pumpe pumpeWohin) {
        if (aktuelleRohr!=null){
            if (aktuelleRohr.getEndPumpen().size()==1){
                aktuelleRohr.getEndPumpen().add(pumpeWohin);
                Logger.info("Rohr repariert!");
            } else {
                Logger.error("Beide Enden des Rohres sind bereits mit einer Pumpe verbunden.");
            }
        } else {
            Logger.error("Der Spieler steht derzeit nicht auf eine Rohr.");
        }
    }

    /**
     * @param middle
     *
     */
    public void pumpeEinmontieren(boolean middle) {
        if(pumpeInHand!=null){
            if (middle){
                if (aktuelleRohr!=null){
                    ArrayList<Rohr> rohren = (ArrayList<Rohr>) aktuelleRohr.rohrSplit();

                    aktuelleRohr.getEndPumpen().get(0).removeRohr(aktuelleRohr);
                    rohren.get(0).getEndPumpen().add(aktuelleRohr.getEndPumpen().get(0));
                    aktuelleRohr.getEndPumpen().get(0).addRohr(rohren.get(0));

                    aktuelleRohr.getEndPumpen().get(1).removeRohr(aktuelleRohr);
                    rohren.get(1).getEndPumpen().add(aktuelleRohr.getEndPumpen().get(1));
                    aktuelleRohr.getEndPumpen().get(1).addRohr(rohren.get(1));

                    kontroller.addPumpe(pumpeInHand);
                    pumpeInHand.addRohr(rohren.get(0));
                    pumpeInHand.addRohr(rohren.get(1));
                } else {
                    Logger.error("Der Spieler steht derzeit nicht auf eine Rohr.");
                }
            } else {
                if (aktuelleRohr!=null){
                    ArrayList<Rohr> rohren = (ArrayList<Rohr>) aktuelleRohr.rohrSplit();

                    aktuelleRohr.getEndPumpen().get(0).removeRohr(aktuelleRohr);
                    rohren.get(0).getEndPumpen().add(aktuelleRohr.getEndPumpen().get(0));
                    aktuelleRohr.getEndPumpen().get(0).addRohr(rohren.get(0));

                    aktuelleRohr.getEndPumpen().get(1).removeRohr(aktuelleRohr);
                    rohren.get(1).getEndPumpen().add(aktuelleRohr.getEndPumpen().get(1));
                    aktuelleRohr.getEndPumpen().get(1).addRohr(rohren.get(1));

                    kontroller.addPumpe(pumpeInHand);
                    pumpeInHand.addRohr(rohren.get(0));
                    pumpeInHand.addRohr(rohren.get(1));
                } else {
                    Logger.error("Der Spieler steht derzeit nicht auf eine Rohr.");
                }
            }
        } else {
            Logger.error("Dieser Spieler hat keine Pumpe in der Hand.");
        }
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
