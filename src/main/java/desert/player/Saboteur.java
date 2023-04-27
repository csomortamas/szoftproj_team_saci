package main.java.desert.player;
import main.java.desert.network.Zisterne;

/**
 *
 */
public class Saboteur extends Spieler {
    /**
     * Default Konstruktor
     */
    public Saboteur(Zisterne startPunkt) {
        super(startPunkt);
    }


    public void rohrLoechern() {
        this.position.kaputtMachen();
    }
}
