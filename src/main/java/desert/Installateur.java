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
        super(startPunkt, kontroller);
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

                    pumpeInHand = null;
                    Logger.info("Pumpe eingebindet.");
                } else {
                    Logger.error("Der Spieler steht derzeit nicht auf eine Rohr.");
                }
            } else {
                if (aktuelleRohr!=null){
                    aktuelleRohr.getEndPumpen().add(pumpeInHand);
                    pumpeInHand.addRohr(aktuelleRohr);
                    kontroller.addPumpe(pumpeInHand);

                    pumpeInHand = null;
                    Logger.info("Pumpe eingebindet.");
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
    public void rohrEinbinden(Pumpe pumpeWoher, Pumpe pumpeWohin, Rohr rohr) {
        if(aktuellePumpe!=null){
            pumpeWoher.addRohr(rohr);
            pumpeWohin.addRohr(rohr);
            rohr.getEndPumpen().add(pumpeWoher);
            rohr.getEndPumpen().add(pumpeWohin);

            Logger.info("Rohr eingebindet!");
        }else{
            Logger.error("Der Spieler steht derzeit nicht auf einen Pumpe!");
        }
    }

    /**
     *
     */
    public void pumpeAufnehmen() {
        if (aktuellePumpe!=null){
            if (aktuellePumpe.isForZisterne()){
                if (pumpeInHand!=null){
                    pumpeInHand=new Pumpe(4);
                    Logger.info("Pumpe aufgenommen!");
                }else{
                    Logger.error("Der Spieler hat schon einer Pumpe in der Hand!");
                }

            }else{
                Logger.error("Der Spieler befindet sich derzeit nicht an einer Pumpe, die zu einer Zisterne gehört.");
            }
        }else{
            Logger.error("Der Spieler steht derzeit nicht auf einer Pumpe!");
        }
    }
}
