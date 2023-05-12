package desert.network;

import desert.control.Kontroller;
import javafx.scene.shape.Line;
import org.tinylog.Logger;

public class Rohr extends Netzelement {
    private Line line;

    /**
     *
     */
    private boolean besetzt;


    /**
     * Default Konstruktor
     */
    public Rohr() {
        istAktiv = false;
        Logger.info("Neues Rohr erstellt.");
    }

    /**
     * @param pumpeInHand
     */
    public void rohrSplit(Pumpe pumpeInHand) {
        Rohr r1 = new Rohr();
        Rohr r2 = new Rohr();

        Kontroller.getKontroller().getMap().getRohre().remove(this);

        Kontroller.getKontroller().addRohr(r1);
        Kontroller.getKontroller().addRohr(r2);

        // binden
        Kontroller.getKontroller().binden(r1, this.getNachbarn().get(0), pumpeInHand);
        Kontroller.getKontroller().binden(r2, pumpeInHand, this.getNachbarn().get(1));
    }
    //=======================================================================================
    //=======================================================================================

    public boolean isBesetzt() {
        return besetzt;
    }

    public void setBesetzt(boolean besetzt) {
        this.besetzt = besetzt;
    }

    public void setLine(Line _line){
        this.line = _line;
    }
    public Line getLine(){
        return this.line;
    }
}
