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
    public void tankFuellen() {
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
        this.rohre.add(rohr);

    }

    /**
     *
     */
    public void flussKalkulierenn() {
        // TODO implement here
    }
}
