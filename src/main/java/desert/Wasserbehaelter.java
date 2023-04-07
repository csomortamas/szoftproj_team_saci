package main.java.desert;
import org.tinylog.Logger;
import java.util.List;
import lombok.Getter; import lombok.Setter;

/**
 *
 */
public abstract class Wasserbehaelter {
    @Getter @Setter private Rohr speziellesRohr; //Cisternát és pumpát, quellét és pumpát összekötő rohr ami nem változtatható
    /**
     *
     */
    @Getter protected List<Rohr> rohre;

    @Getter @Setter private Pumpe p;
    /**
     * @param rohr
     */
    public void addRohr(Rohr rohr) {
        rohre.add(rohr);

        Logger.info("Rohr wurde zum Wasserbehälter addiert");
    }

    /**
     * Konstruktor
     */
    public Wasserbehaelter() {
        Logger.info("Wasserbehaelter wurde erschaffen");
    }

}
