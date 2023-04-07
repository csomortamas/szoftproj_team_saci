package main.java.desert;
import org.tinylog.Logger;
public class Wasserquelle extends Wasserbehaelter {
    /**
     * Default Konstruktor
     */
    public Wasserquelle() {
        this.getP()=new Pumpe();
        Rohr r=new Rohr();
        wasserStarten(r);
    }

    /**
     *
     */
    public void wasserStarten(Rohr r) {
        // TODO implement here
        r.getEndPumpen().add(this.getP());
        r.setFlusswert(3);
    }

}
