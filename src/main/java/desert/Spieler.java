package main.java.desert;
import lombok.Getter;
import lombok.Setter;
import org.tinylog.Logger;

import java.lang.reflect.Array;
import java.util.ArrayList;
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
     * @param rohr
     *
     */
    public void umbinden(Pumpe pumpeWoher, Pumpe pumpeWohin) {
        List<Rohr> alleRohre = Kontroller.getKontroller().getAlleRohre();
        for (Rohr rohr : alleRohre) {
            if (rohr == position) {
                if(pumpeWoher == rohr.nachbarn.get(0)) {
                    Kontroller.getKontroller().binden(rohr, rohr.nachbarn.get(1), pumpeWohin);
                    rohr.nachbarn.get(0).nachbarn.remove(rohr); // entfernt das rohr aus der liste der nachbarn
                    rohr.nachbarn.remove(rohr.nachbarn.get(0)); // entfernt die pumpe aus der liste der nachbarn
                    // TODO: eingangsrohr und ausgangsrohr der pumpe ändern
                } else {
                    Kontroller.getKontroller().binden(rohr, rohr.nachbarn.get(0), pumpeWohin);
                    rohr.nachbarn.get(1).nachbarn.remove(rohr); // entfernt das rohr aus der liste der nachbarn
                    rohr.nachbarn.remove(rohr.nachbarn.get(1)); // entfernt die pumpe aus der liste der nachbarn
                    // TODO: eingangsrohr und ausgangsrohr der pumpe ändern
                }
            }
        }
    }

    /**
     * @param netzwerkElement
     */
    public void step(Netzelement netzwerkElement) {
        if(position.getNachbarn().contains(netzwerkElement) && !netzwerkElement.istBesetzt) {
            position.istBesetzt = false;

            position = netzwerkElement;

            if (netzwerkElement instanceof Rohr) {
                netzwerkElement.istBesetzt = true;
            }

            Logger.info("Spieler ist jetzt bei " + position);
        }
        else {
            Logger.error("NetzwerkElement ist kein Nachbar von " + position);
        }
    }
}
