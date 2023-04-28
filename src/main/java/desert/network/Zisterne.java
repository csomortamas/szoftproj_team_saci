package main.java.desert.network;

import lombok.Getter;
import lombok.Setter;
import main.java.desert.network.Pumpe;

/**
 *
 */
public class Zisterne extends Pumpe {
    /**
     *
     */
    @Getter @Setter private Pumpe pumpeZurVerfuegung;

    /**
     * Default Konstruktor
     *
     * @param x
     * @param y
     */
    public Zisterne(double x, double y) {
        super(x, y);
    }
}
