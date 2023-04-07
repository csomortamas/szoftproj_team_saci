package main.java.desert;

import lombok.Getter;
import lombok.Setter;
import org.tinylog.Logger;

import java.util.Random;

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
        Rohr rohrToZisterne = new Rohr();
        this.setP(new Pumpe(4));
        this.setSpeziellesRohr(rohrToZisterne);
        this.getP().addRohr(rohrToZisterne);
        this.getP().setAusgangsRohr(rohrToZisterne);

    }


    /**
     * @return
     */
    public void rohrErstellen() {
        // TODO implement here
        Random random = new Random();
        int rn = random.nextInt(7);
        if(rn == 6){
            Rohr newRohr = new Rohr();
            this.getP().addRohr(newRohr);
            Logger.info("Neues Rohr zur Zisterne hinzugefügt");
            return;
        }
        Logger.info("In dieser Runde keine Rohr zu dieser Zisterne erstellt.");
    }

    /**
     *
     */
    public void pumpeErstellen() {
        // TODO implement here
        Random random = new Random();
        int rand = random.nextInt(5);
        if(rand == 4) {
            this.neuePumpe = true;
            Logger.info("Neue Pumpe erstellt in dieser Runde");
            return;
        }
        Logger.info("Pumpe wurde nicht erstellt in dieser Runde");
    }
}
