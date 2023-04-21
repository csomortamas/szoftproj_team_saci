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

    @Getter @Setter protected String name;


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
        Rohr aktuelleRohr = null;
        for (Rohr rohr : alleRohre) {
            if (rohr == position) {
                aktuelleRohr = rohr;
                break;
            }
        }

        if(aktuelleRohr==null) return;

        Netzelement pumpe0 = aktuelleRohr.getNachbarn().get(0);
        Netzelement pumpe1 = aktuelleRohr.getNachbarn().get(1);



        if(pumpeWoher == pumpe0) {
            Kontroller.getKontroller().binden(aktuelleRohr, pumpe1, pumpeWohin);
            pumpe0.getNachbarn().remove(aktuelleRohr); // entfernt das rohr aus der liste der nachbarn
            aktuelleRohr.getNachbarn().remove(pumpe0); // entfernt die pumpe aus der liste der nachbarn
            aktuelleRohr.getNachbarn().remove(pumpe1);

        } else {
            Kontroller.getKontroller().binden(aktuelleRohr, pumpe0, pumpeWohin);
            pumpe1.getNachbarn().remove(aktuelleRohr); // entfernt das rohr aus der liste der nachbarn
            aktuelleRohr.getNachbarn().remove(pumpe1); // entfernt die pumpe aus der liste der nachbarn
            aktuelleRohr.getNachbarn().remove(pumpe0);
        }

        if(pumpeWoher.getEingangsRohr() == aktuelleRohr) {
            pumpeWoher.setEingangsRohr(null);
        } else if (pumpeWoher.getAusgangsRohr() == aktuelleRohr) {
            pumpeWoher.setAusgangsRohr(null);
        }
    }

    /**
     * @param netzwerkElement
     */
    public void step(Netzelement netzwerkElement) {
        if(netzwerkElement.isIstBesetzt()){
            Logger.error("Netzwerkelement ist schon besetzt: " + netzwerkElement);
            return;
        }
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

    // Pumpe eingansrohr umstellen
    public void eingangsRohrUmstellen(Rohr rohr) {
        List<Pumpe> allePumpen = Kontroller.getKontroller().getAllePumpen();
        for(Pumpe pumpe : allePumpen) {
            if(pumpe == position) {
                if(pumpe.getNachbarn().contains(rohr)) {
                    if(rohr != pumpe.getAusgangsRohr()) {
                        pumpe.setEingangsRohr(rohr);
                        Logger.info("Eingangsrohr der Pumpe {} wurde auf {} umgestellt.", position, rohr);
                    } else {
                        Logger.error("Ausgangsrohr und Eingangsrohr dürfen nicht gleich sein.");
                    }
                }
                else {
                    Logger.error("Rohr ist kein Nachbar von " + position);
                }
            }
        }
    }

    // Pumpe ausgangsrohr umstellen
    public void ausgangsRohrUmstellen(Rohr rohr) {
        List<Pumpe> allePumpen = Kontroller.getKontroller().getAllePumpen();
        for(Pumpe pumpe : allePumpen) {
            if(pumpe == position) {
                if(pumpe.getNachbarn().contains(rohr)) {
                    if(rohr != pumpe.getAusgangsRohr() ){
                        pumpe.setAusgangsRohr(rohr);
                        Logger.info("Ausgangsrohr der Pumpe {} wurde auf {} umgestellt.", position, rohr);
                    } else {
                        Logger.error("Ausgangsrohr und Eingangsrohr dürfen nicht gleich sein.");
                    }

                }
                else {
                    Logger.error("Rohr ist kein Nachbar von " + position);
                }
            }
        }
    }

}
