package desert.network;

import desert.control.Kontroller;
import desert.gui.GuiMap;
import javafx.scene.shape.Line;
import org.tinylog.Logger;

public class Rohr extends Netzelement {
    private Line line;

    public Rohr() {
        istAktiv = false;
        Logger.info("Neues Rohr erstellt.");
    }

    public void rohrSplit(Pumpe pumpeInHand) {
        Rohr r1 = new Rohr();
        Rohr r2 = new Rohr();

        line.setVisible(false);
        line.setDisable(true);
        Kontroller.getKontroller().getMap().getRohre().remove(this);

        Kontroller.getKontroller().addRohr(r1);
        Kontroller.getKontroller().addRohr(r2);

        // binden
        Kontroller.getKontroller().binden(r1, this.getNachbarn().get(0), pumpeInHand);
        Kontroller.getKontroller().binden(r2, pumpeInHand, this.getNachbarn().get(1));
        GuiMap.getGuiMap().refreshSplittedRohr(r1, r2);
    }
    //=================================GETTERS & SETTERS=====================================
    //=======================================================================================


    public void setLine(Line _line) {
        this.line = _line;
    }

    public Line getLine() {
        return this.line;
    }

}
