package main.java.desert;
import lombok.Getter;
import lombok.Setter;
import org.tinylog.Logger;
/**
 *
 */
public abstract class Spieler {
    /**
     *
     */
    @Getter @Setter protected Pumpe aktuellePumpe;
    /**
     *
     */
    @Getter @Setter protected Rohr aktuelleRohr;



    /**
     *
     */
    public void pumpeStarten() {
        if (aktuellePumpe!=null){
            aktuellePumpe.setIstAktiv(true);
            Logger.info("Pumpe gestartet!");
        } else {
            Logger.error("Der Spieler steht derzeit nicht auf einer Pumpe.");
        }
    }

    /**
     * @param rohr
     */
    public void eingangsRohrUmstellen(Rohr rohr) {
        if (aktuellePumpe!=null){
            aktuellePumpe.setEingangsRohr(rohr);
            Logger.info("Eingangsrohr umgestellt!");
        } else {
            Logger.error("Der Spieler steht derzeit nicht auf einer Pumpe.");
        }
    }

    /**
     * @param rohr
     *
     */
    public void ausgangsRohrUmstellen(Rohr rohr) {
        if (aktuellePumpe!=null){
            aktuellePumpe.setEingangsRohr(rohr);
            Logger.info("Eingangsrohr umgestellt!");
        } else {
            Logger.error("Der Spieler steht derzeit nicht auf einer Pumpe.");
        }
    }

    /**
     *
     */
    public void pumpeStoppen() {
        if (aktuellePumpe!=null){
            aktuellePumpe.setIstAktiv(false);
            Logger.info("Pumpe gestoppt!");
        } else {
            Logger.error("Der Spieler steht derzeit nicht auf einer Pumpe.");
        }
    }

    /**
     * @param netzwerkElement
     */
    public void step(Netzelement netzwerkElement) {
        if (aktuellePumpe!=null){
            if (aktuellePumpe.getClass() != netzwerkElement.getClass()){
                setAktuelleRohr((Rohr) netzwerkElement);
                Logger.info("Der Spieler ist auf ein neues Feld gezogen!");
            }else {
                Logger.error("Von einen Rohr können wir nur auf eine Pumpe treten.");
            }
        } else {
            if (aktuelleRohr.getClass() != netzwerkElement.getClass()){
                setAktuellePumpe((Pumpe) netzwerkElement);
                Logger.info("Der Spieler ist auf ein neues Feld gezogen!");
            }else {
                Logger.error("Von einen Pumpe können wir nur auf eine Rohr treten.");
            }
        }
    }
}
