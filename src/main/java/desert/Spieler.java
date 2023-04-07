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
    @Getter @Setter Kontroller kontroller;


    public Spieler(Netzelement startPunkt, Kontroller kontroller) {
        this.aktuellePumpe = (Pumpe)startPunkt;
        this.kontroller = kontroller;
    }


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
                for(int i=0; i<aktuellePumpe.getRohre().size(); i++){
                    if(aktuellePumpe.getRohre().get(i)== (Rohr) netzwerkElement){
                        setAktuelleRohr((Rohr) netzwerkElement);
                        setAktuellePumpe(null);
                        Logger.info("Der Spieler ist auf ein naechste rohr gezogen!");
                        return;
                    }
                }

            }else {
                Logger.error("Von einer Pumpe können wir nur auf einen benachbarten Rohr treten.");
            }
        } else {
            if (aktuelleRohr.getClass() != netzwerkElement.getClass()){
                for(int i=0; i<aktuelleRohr.getEndPumpen().size(); i++){
                    if(aktuelleRohr.getEndPumpen().get(i)== (Pumpe) netzwerkElement){
                        setAktuellePumpe((Pumpe) netzwerkElement);
                        setAktuelleRohr(null);

                        Logger.info("Der Spieler ist auf ein naechste pumpe gezogen!");
                        return;
                    }
                }

            }else {
                Logger.error("Von einem Rohr können wir nur auf eine der Endpumpen treten.");
            }
        }
    }
}
