package desert.network;

import javafx.scene.control.Button;
import org.tinylog.Logger;


public class Pumpe extends Netzelement {


    private Button button;

    protected double posX;

    protected double posY;

    private Rohr eingangsRohr;

    private Rohr ausgangsRohr;

    private int wasserTank = 0;

    public Pumpe(double x, double y) {
        this.posX = x;
        this.posY = y;
        istAktiv = true;
        Logger.info("Neue Pumpe erstellt.");
    }

    public void wasserWeiterleiten() {
        if (isKaputt() || !istAktiv) {
            if (ausgangsRohr != null) {
                ausgangsRohr.setIstAktiv(false);
            }
            return;
        }

        if (eingangsRohr != null && ausgangsRohr != null && !eingangsRohr.isKaputt()) {
            ausgangsRohr.setIstAktiv(eingangsRohr.isAktiv());
        } else if (eingangsRohr != null && ausgangsRohr == null && eingangsRohr.istAktiv && !eingangsRohr.isKaputt()) {
            wasserTank++;
        } else if (eingangsRohr == null && ausgangsRohr != null && ausgangsRohr.istAktiv && wasserTank > 0) {
            wasserTank--;
            ausgangsRohr.setIstAktiv(true);
        } else if (eingangsRohr == null && ausgangsRohr != null && ausgangsRohr.istAktiv && wasserTank <= 0) {
            ausgangsRohr.setIstAktiv(false);
        } else if (eingangsRohr != null && ausgangsRohr != null && eingangsRohr.isKaputt() && wasserTank <= 0) {
            ausgangsRohr.setIstAktiv(false);
        } else if (eingangsRohr != null && ausgangsRohr != null && eingangsRohr.isKaputt() && wasserTank > 0) {
            wasserTank--;
            ausgangsRohr.setIstAktiv(true);
        }
    }
    //==============================GETTERS & SETTERS========================================
    //=======================================================================================


    public double getPosX() {
        return posX;
    }

    public double getPosY() {
        return posY;
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

    public Button getButton() {
        return button;
    }

    public void setButton(Button _button) {
        this.button = _button;
    }
}
