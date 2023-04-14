package main.java.desert;

import lombok.Getter;
import lombok.Setter;
import org.tinylog.Logger;

import java.util.ArrayList;
import java.util.List;

public class Pumpe extends Netzelement {
    /**
     *
     */
    @Getter private List<Rohr> rohre;
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
     *
     */
    @Getter @Setter private boolean isForZisterne;

    @Getter @Setter private boolean isForQuelle;

    /**
     * Default Konstruktor
     */
    public Pumpe(int maxRohrAnzahl) {

    }

    /**
     *
     */
    public void tankFuellen() {
        // TODO implement here

    }

    /**
     * @param rohr
     */
    public void addRohr(Rohr rohr) {

    }
    /**
     *
     */
    public void removeRohr(Rohr rohr){

    }
    /**
     *
     */
    public void flussKalkulierenn() {

    }
}
