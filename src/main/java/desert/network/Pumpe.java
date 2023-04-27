package main.java.desert.network;

import lombok.Getter;
import lombok.Setter;
import org.tinylog.Logger;


public class Pumpe extends Netzelement {

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
    @Getter final private int maxRohrAnzahl = 4;
    /**
     *
     */
    @Getter @Setter private boolean isForZisterne;

    @Getter @Setter private boolean isForQuelle;


    /**
     * Default Konstruktor
     */
    public Pumpe() {
        istAktiv = true;
        Logger.info("Neue Pumpe erstellt.");
    }

    /**
     *
     */
    public void tankFuellen(boolean istAktiv) {

    }

    /**
     *
     */
    public void wasserWeiterleiten() {
        if(!istAktiv)
            return;

        if(eingangsRohr != null && ausgangsRohr != null && !eingangsRohr.isIstKaputt()) {
            ausgangsRohr.setIstAktiv(eingangsRohr.istAktiv);
        } else if(eingangsRohr != null && ausgangsRohr == null && eingangsRohr.istAktiv && eingangsRohr.isIstKaputt()) {
            wasserTank++;
        } else if(eingangsRohr == null && ausgangsRohr != null && ausgangsRohr.istAktiv && wasserTank > 0) {
            wasserTank--;
            ausgangsRohr.setIstAktiv(true);
        }
    }
}
