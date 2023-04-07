package main.java.desert;
import org.tinylog.Logger;
public class Wasserquelle extends Wasserbehaelter {
    /**
     * Default Konstruktor
     */
    public Wasserquelle() {
        this.setPumpe(new Pumpe(4));
        Rohr rohrFromQuelleToPumpe=new Rohr();
        this.setSpeziellesRohr(rohrFromQuelleToPumpe);
        wasserStarten(rohrFromQuelleToPumpe);
        Logger.info("Quelle initialisiert, wasser gestartet");
    }

    /**
     *
     */
    public void wasserStarten(Rohr r) {
        // TODO implement here
        if (r.getEndPumpen() != null) {
            r.getEndPumpen().add(this.getPumpe());
            r.setFlusswert(3);
            Logger.info("3 Wasserquantitäten zur Pumpe r Wasserquelle");
        }
    }

}
