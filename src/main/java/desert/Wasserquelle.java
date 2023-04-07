package main.java.desert;
import org.tinylog.Logger;
public class Wasserquelle extends Wasserbehaelter {
    /**
     * Default Konstruktor
     */
    public Wasserquelle() {
        this.setP(new Pumpe(4));
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
        r.getEndPumpen().add(this.getP());
        r.setFlusswert(3);
        Logger.info("3 Wasserquantitäten zur Pumpe r Wasserquelle");

    }

}
