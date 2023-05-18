package desert.player;

import desert.control.Kontroller;
import desert.network.Netzelement;
import desert.network.Pumpe;
import desert.network.Rohr;
import javafx.scene.control.Button;
import org.tinylog.Logger;

import java.util.List;

/**
 *
 */
public abstract class Spieler {
    private Button button;



    protected Netzelement position;

    protected String name;


    public Spieler(Netzelement startPunkt) {
        position = startPunkt;
    }

    /**
     * @param rohr
     * @param eingangsRohr
     */
    public void rohrUmstellen(Rohr rohr, boolean eingangsRohr) {
        List<Pumpe> allePumpen = Kontroller.getKontroller().getMap().getPumpen();
        for (Pumpe pumpe : allePumpen) {
            if (pumpe == position) {
                if (eingangsRohr) {
                    pumpe.setEingangsRohr(rohr);
                    Logger.info("Eingangsrohr der Pumpe {} wurde auf {} umgestellt.", pumpe, rohr);
                } else {
                    pumpe.setAusgangsRohr(rohr);
                    Logger.info("Ausgangsrohr der Pumpe {} wurde auf {} umgestellt.", pumpe, rohr);
                }
            }
        }
    }

    /**
     * @param einSchalten
     */
    public void pumpeSchalten(boolean einSchalten) {
        List<Pumpe> allePumpen = Kontroller.getKontroller().getMap().getPumpen();
        for (Pumpe pumpe : allePumpen) {
            if (pumpe == position) {
                if (einSchalten) {
                    pumpe.setIstAktiv(true);
                    Logger.info("Pumpe {} wurde eingeschaltet.", pumpe);
                } else {
                    pumpe.setIstAktiv(false);
                    Logger.info("Pumpe {} wurde ausgeschaltet.", pumpe);
                }
            }
        }
    }

    /**
     * @param pumpeWohin
     * @param pumpeWoher
     */
    public void umbinden(Pumpe pumpeWoher, Pumpe pumpeWohin) {
        List<Rohr> alleRohre = Kontroller.getKontroller().getMap().getRohre();
        Rohr aktuelleRohr = null;
        for (Rohr rohr : alleRohre) {
            if (rohr == position) {
                aktuelleRohr = rohr;
                break;
            }
        }

        if (aktuelleRohr == null) return;

        Netzelement pumpe0 = aktuelleRohr.getNachbarn().get(0);
        Netzelement pumpe1 = aktuelleRohr.getNachbarn().get(1);

        if (aktuelleRohr.getNachbarn().contains(null)) {
            aktuelleRohr.getNachbarn().remove(null);
            aktuelleRohr.getNachbarn().add(pumpeWohin);
        } else {
            if (pumpeWoher == pumpe0) {
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
        }

        if (pumpeWoher.getEingangsRohr() == aktuelleRohr) {
            pumpeWoher.setEingangsRohr(null);
        } else if (pumpeWoher.getAusgangsRohr() == aktuelleRohr) {
            pumpeWoher.setAusgangsRohr(null);
        }
    }

    /**
     * @param netzwerkElement
     */
    public void step(Netzelement netzwerkElement) {
        if (netzwerkElement.isIstBesetzt()) {
            Logger.error("Netzwerkelement ist schon besetzt: " + netzwerkElement);
            return;
        }
        if (position.getNachbarn().contains(netzwerkElement) && !netzwerkElement.isIstBesetzt()) {
            position.setIstBesetzt(false);

            position = netzwerkElement;

            if (netzwerkElement instanceof Rohr) {
                netzwerkElement.setIstBesetzt(true);
            }

            Logger.info("Spieler ist jetzt bei " + position);
        } else {
            Logger.error("NetzwerkElement ist kein Nachbar von " + position);
        }
    }

    // Pumpe eingansrohr umstellen
    public void eingangsRohrUmstellen(Rohr rohr) {
        List<Pumpe> allePumpen = Kontroller.getKontroller().getMap().getPumpen();
        for (Pumpe pumpe : allePumpen) {
            if (pumpe == position) {
                if (pumpe.getNachbarn().contains(rohr)) {
                    if (rohr != pumpe.getAusgangsRohr()) {
                        pumpe.setEingangsRohr(rohr);
                        Logger.info("Eingangsrohr der Pumpe {} wurde auf {} umgestellt.", position, rohr);
                    } else {
                        Logger.error("Ausgangsrohr und Eingangsrohr dürfen nicht gleich sein.");
                    }
                } else {
                    Logger.error("Rohr ist kein Nachbar von " + position);
                }
            }
        }
    }

    // Pumpe ausgangsrohr umstellen
    public void ausgangsRohrUmstellen(Rohr rohr) {
        List<Pumpe> allePumpen = Kontroller.getKontroller().getMap().getPumpen();
        for (Pumpe pumpe : allePumpen) {
            if (pumpe == position) {
                if (pumpe.getNachbarn().contains(rohr)) {
                    if (rohr != pumpe.getEingangsRohr()) {
                        pumpe.setAusgangsRohr(rohr);
                        Logger.info("Ausgangsrohr der Pumpe {} wurde auf {} umgestellt.", position, rohr);
                    } else {
                        Logger.error("Ausgangsrohr und Eingangsrohr dürfen nicht gleich sein.");
                    }

                } else {
                    Logger.error("Rohr ist kein Nachbar von " + position);
                }
            }
        }
    }
    //=======================================================================================
    //=======================================================================================

    public Netzelement getPosition() {
        return position;
    }

    public void setPosition(Netzelement position) {
        this.position = position;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Button getButton() {
        return button;
    }

    public void setButton(Button sButton) {
        this.button = sButton;
    }
}
