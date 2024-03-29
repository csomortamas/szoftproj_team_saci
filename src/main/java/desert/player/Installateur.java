package desert.player;

import desert.control.Kontroller;
import desert.gui.GuiMap;
import desert.network.Pumpe;
import desert.network.Rohr;
import desert.network.Wasserquelle;
import desert.network.Zisterne;
import org.tinylog.Logger;

import java.util.List;

public class Installateur extends Spieler {
    private Pumpe pumpeInHand;

    public Installateur(Wasserquelle startPunkt) {
        super(startPunkt);
        //Kontroller.getKontroller().getInstallateurTeam().add(this);
    }

    public void pumpeEinmontieren(boolean middle) {
        if (middle && position instanceof Rohr && this.pumpeInHand != null) {
            List<Rohr> alleRohre = Kontroller.getKontroller().getMap().getRohre();
            for (Rohr rohr : alleRohre) {
                if (position == rohr) {
                    double startX = rohr.getLine().getStartX();
                    double endX = rohr.getLine().getEndX();
                    double startY = rohr.getLine().getStartY();
                    double endY = rohr.getLine().getEndY();

                    this.setPosition(pumpeInHand);
                    Kontroller.getKontroller().addPumpe(pumpeInHand);
                    rohr.rohrSplit(pumpeInHand);
                    GuiMap.getGuiMap().refreshEinmontiertePumpe(pumpeInHand, startX, startY, endX, endY);
                    pumpeInHand = null;
                    Logger.info("Pumpe eingebaut.");
                    break;
                }
            }
        } else if (!middle && position instanceof Rohr && this.pumpeInHand != null) {
            Kontroller.getKontroller().addPumpe(pumpeInHand);
            pumpeInHand = null;
            GuiMap.getGuiMap().refreshSpieler();
            Logger.info("Pumpe eingebaut.");
        } else {
            Logger.error("Pumpe kann nicht eingebaut werden.");
        }
    }

    public void pumpeAufnehmen() {
        List<Zisterne> zisterneList = Kontroller.getKontroller().getMap().getZisternen();

        for (Zisterne zisterne : zisterneList) {
            if (position == zisterne && zisterne.getPumpeZurVerfuegung() != null) {
                pumpeInHand = zisterne.getPumpeZurVerfuegung();
                zisterne.setPumpeZurVerfuegung(null);
                GuiMap.getGuiMap().refreshReadyPumps();
                GuiMap.getGuiMap().refreshSpieler();
                Logger.info("Pumpe aufgenommen.");
                return;
            }
        }
        Logger.error("Keine Pumpe aufgenommen.");
    }
    //==================================GETTERS & SETTERS====================================
    //=======================================================================================

    public Pumpe getPumpeInHand() {
        return pumpeInHand;
    }

    public void setPumpeInHand(Pumpe pumpeInHand) {
        this.pumpeInHand = pumpeInHand;
    }
}
