package main.java.desert;

import lombok.Getter;
import lombok.Setter;
import org.tinylog.Logger;

import java.util.ArrayList;
import java.util.List;

public class Rohr extends Netzelement {
    /**
     *
     */

    @Getter @Setter private List<Pumpe> endPumpen;


    /**
     *
     */

    @Getter @Setter private int flusswert;

    /**
     *
     */

    @Getter @Setter private boolean besetzt;

    /**
     * Default Konstruktor
     */
    public Rohr() {
        Rohr rohr = new Rohr();
        Logger.info("Neues Rohr erstellt.");
    }

    /**
     * @return
     */
    public List<Rohr> rohrSplit() {
        // TODO implement here
        Rohr r1 = new Rohr();
        Rohr r2 = new Rohr();

        List<Rohr> splittedRohre = new ArrayList<>();
        splittedRohre.add(r1);
        splittedRohre.add(r2);

        Logger.info("Rohre splitted um Pumpe einbauen zu können");
        return splittedRohre;
    }
}
