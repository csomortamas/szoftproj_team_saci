package desert.network;

import javafx.scene.control.Button;
import org.tinylog.Logger;


public class Pumpe extends Netzelement {


    private Button button;
    /**
     *
     */
    protected double posX;

    /**
     *
     */
    protected double posY;
    /**
     *
     */
    private Rohr eingangsRohr;
    /**
     *
     */
    private Rohr ausgangsRohr;
    /**
     *
     */
    private int wasserTank;
    /**
     *
     */
    final private int maxRohrAnzahl = 4;
    /**
     *
     */
    private boolean isForZisterne;

    private boolean isForQuelle;


    /**
     * Default Konstruktor
     */
    public Pumpe(double x, double y) {
        this.posX = x;
        this.posY = y;
        istAktiv = true;
        Logger.info("Neue Pumpe erstellt.");
    }

    /**
     *
     */
    public void wasserWeiterleiten() {
        if (!istAktiv)
            return;

        if (eingangsRohr != null && ausgangsRohr != null && !eingangsRohr.isIstKaputt()) {
            ausgangsRohr.setIstAktiv(eingangsRohr.istAktiv);
        } else if (eingangsRohr != null && ausgangsRohr == null && eingangsRohr.istAktiv && !eingangsRohr.isIstKaputt()) {
            wasserTank++;
        } else if (eingangsRohr == null && ausgangsRohr != null && ausgangsRohr.istAktiv && wasserTank > 0) {
            wasserTank--;
            ausgangsRohr.setIstAktiv(true);
        }
    }
    //=======================================================================================
    //=======================================================================================

    public int getMaxRohrAnzahl() {
        return maxRohrAnzahl;
    }

    public double getPosX() {
        return posX;
    }

    public void setPosX(double posX) {
        this.posX = posX;
    }

    public double getPosY() {
        return posY;
    }

    public void setPosY(double posY) {
        this.posY = posY;
    }

    public Rohr getEingangsRohr() {
        return eingangsRohr;
    }

    public void setEingangsRohr(Rohr eingangsRohr) {
        this.eingangsRohr = eingangsRohr;
    }

    public Rohr getAusgangsRohr() {
        return ausgangsRohr;
    }

    public void setAusgangsRohr(Rohr ausgangsRohr) {
        this.ausgangsRohr = ausgangsRohr;
    }

    public int getWasserTank() {
        return wasserTank;
    }

    public void setWasserTank(int wasserTank) {
        this.wasserTank = wasserTank;
    }

    public boolean isForZisterne() {
        return isForZisterne;
    }

    public void setForZisterne(boolean forZisterne) {
        isForZisterne = forZisterne;
    }

    public boolean isForQuelle() {
        return isForQuelle;
    }

    public void setForQuelle(boolean forQuelle) {
        isForQuelle = forQuelle;
    }

    public Button getButton() {
        return button;
    }
    public void setButton(Button _button){
        this.button = _button;
    }
}
