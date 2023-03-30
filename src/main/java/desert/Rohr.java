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
    @Getter @Setter private List<Pumpe> enden;


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
    }

    /**
     * @return
     */
    public List<Rohr> rohrSplit() {
        // TODO implement here
        return null;
    }
}
