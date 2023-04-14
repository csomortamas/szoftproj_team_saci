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

    @Getter private List<Pumpe> endPumpen;


    /**
     *
     */

    /**
     *
     */

    @Getter @Setter private boolean besetzt;

    /**
     * Default Konstruktor
     */
    public Rohr() {
        Logger.info("Neues Rohr erstellt.");
    }

    /**
     * @return
     */
    public List<Rohr> rohrSplit() {

    }
}
