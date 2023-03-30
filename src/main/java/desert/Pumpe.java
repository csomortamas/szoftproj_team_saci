package main.java.desert;

import lombok.Getter;
import lombok.Setter;
import org.tinylog.Logger;

import java.util.List;

public class Pumpe extends Netzelement {
    /**
     *
     */
    @Getter @Setter private List<Rohr> rohre;
    /**
     *
     */
    @Getter @Setter private Rohr eingangsRohr;
    /**
     *
     */
    @Getter @Setter private Rohr ausgangsRohr;
    /**
     *
     */
    @Getter @Setter private int wasserTank;
    /**
     *
     */
    @Getter @Setter private int maxRohrAnzahl;

    /**
     * Default Konstruktor
     */
    public Pumpe(int maxRohrAnzahl) {
        this.maxRohrAnzahl = maxRohrAnzahl;
        this.wasserTank = 0;
        Logger.info("Neue Pumpe erstellt.");
    }

    /**
     *
     */
    private void tankFuellen() {
        // TODO implement here
        if (this.ausgangsRohr.istKaputt || this.ausgangsRohr == null || this.eingangsRohr.getFlusswert() > this.ausgangsRohr.getFlusswert()) {
            int plusWasser = this.eingangsRohr.getFlusswert() - this.ausgangsRohr.getFlusswert();
            this.wasserTank += plusWasser;
            Logger.info("Die WassermAnge in dieser Pumpe wurde mit " + plusWasser + "erhöht");
        }
    }

    /**
     * @param rohr
     */
    public void addRohr(Rohr rohr) {
        // TODO implement here
        if(this.rohre.size() == maxRohrAnzahl){
            Logger.error("Neues Rohr konnte nicht zur Pumpe addiert werden, maximale Anzahl von Rohre schon erreicht");
            throw new RuntimeException("Max Rohrenanzahl schon erreicht");
        }
        this.rohre.add(rohr);
        Logger.info("Neues Rohr zur Pumpe gefügt.");
    }
    /**
     *
     */
    public void removeRohr(Rohr rohr){
        if(this.ausgangsRohr == rohr){
            ausgangsRohr = null;
        }else if (this.eingangsRohr == rohr) {
            eingangsRohr = null;
        }
        this.rohre.remove(rohr);
    }
    /**
     *
     */
    public void flussKalkulierenn() {
        // TODO implement here
        int setTo;
        if(this.eingangsRohr.getFlusswert() > this.ausgangsRohr.kapazitaet){
            setTo = ausgangsRohr.kapazitaet;
            this.ausgangsRohr.setFlusswert(setTo);

        }else{
            this.ausgangsRohr.setFlusswert(eingangsRohr.getFlusswert());
        }
        Logger.info("Ausgangsrohr Flusswert gestellt auf: " +);
    }
}
