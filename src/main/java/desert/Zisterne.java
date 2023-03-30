package main.java.desert;

import lombok.Getter;
import lombok.Setter;
import org.tinylog.Logger;

/**
 *
 */
public class Zisterne extends Wasserbehaelter {
    /**
     *
     */


    @Getter @Setter private boolean neuePumpe;

    /**
     * Default Konstruktor
     */
    public Zisterne() {
        Logger.info("Zisterne erstellt");
    }


    /**
     * @return
     */
    public Rohr rohrErstellen() {
        // TODO implement here
        return null;
    }

    /**
     *
     */
    public void pumpeErstellen() {
        // TODO implement here


    }

}
