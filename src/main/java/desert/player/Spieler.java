package main.java.desert.player;
import lombok.Getter;
import lombok.Setter;
import main.java.desert.control.Kontroller;
import main.java.desert.network.Netzelement;
import main.java.desert.network.Pumpe;
import main.java.desert.network.Rohr;
import org.tinylog.Logger;

import java.util.List;

/**
 *
 */
public abstract class Spieler {
    /**
     *
     */
    @Getter @Setter protected Netzelement position;


    public Spieler(Netzelement startPunkt) {
        position = startPunkt;
    }

    /**
     * @param rohr
     *
     */
    public void rohrUmstellen(Rohr rohr, boolean eingangsRohr) {
        List<Pumpe> allePumpen = Kontroller.getKontroller().getAllePumpen();
        for (Pumpe pumpe : allePumpen) {
            if (pumpe == position) {
                if (eingangsRohr) {
                    pumpe.setEingangsRohr(rohr);
                    Logger.info("Eingangsrohr der Pumpe {} wurde auf {} umgestellt.", pumpe, rohr);
                }
                else {
                    pumpe.setAusgangsRohr(rohr);
                    Logger.info("Ausgangsrohr der Pumpe {} wurde auf {} umgestellt.", pumpe, rohr);
                }
            }
        }
    }

    /**
     *
     */
    public void pumpeSchalten(boolean einSchalten) {
        List<Pumpe> allePumpen = Kontroller.getKontroller().getAllePumpen();
        for (Pumpe pumpe : allePumpen) {
            if (pumpe == position) {
                if(einSchalten) {
                    pumpe.setIstAktiv(true);
                    Logger.info("Pumpe {} wurde eingeschaltet.", pumpe);
                }
                else {
                    pumpe.setIstAktiv(false);
                    Logger.info("Pumpe {} wurde ausgeschaltet.", pumpe);
                }
            }
        }
    }

    /**
     * @param pumpeWohin
     * @param pumpeWoher
     *
     *
     */
    public void umbinden(Pumpe pumpeWoher, Pumpe pumpeWohin) {
        List<Rohr> alleRohre = Kontroller.getKontroller().getAlleRohre();
        for (Rohr rohr : alleRohre) {
            if (rohr == position) {
                if(pumpeWoher == rohr.getNachbarn().get(0)) {
                    Kontroller.getKontroller().binden(rohr, rohr.getNachbarn().get(1), pumpeWohin);
                    rohr.getNachbarn().get(0).getNachbarn().remove(rohr); // entfernt das rohr aus der liste der nachbarn
                    rohr.getNachbarn().remove(rohr.getNachbarn().get(0)); // entfernt die pumpe aus der liste der nachbarn
                    // TODO: eingangsrohr und ausgangsrohr der pumpe ändern
                } else {
                    Kontroller.getKontroller().binden(rohr, rohr.getNachbarn().get(0), pumpeWohin);
                    rohr.getNachbarn().get(1).getNachbarn().remove(rohr); // entfernt das rohr aus der liste der nachbarn
                    rohr.getNachbarn().remove(rohr.getNachbarn().get(1)); // entfernt die pumpe aus der liste der nachbarn
                    // TODO: eingangsrohr und ausgangsrohr der pumpe ändern
                }
            }
        }
    }

    /**
     * @param netzwerkElement
     */
    public void step(Netzelement netzwerkElement) {
        if(position.getNachbarn().contains(netzwerkElement) && !netzwerkElement.isIstBesetzt()) {
            position.setIstBesetzt(false);

            position = netzwerkElement;

            if (netzwerkElement instanceof Rohr) {
                netzwerkElement.setIstBesetzt(true);
            }

            Logger.info("Spieler ist jetzt bei " + position);
        }
        else {
            Logger.error("NetzwerkElement ist kein Nachbar von " + position);
        }
    }
}
