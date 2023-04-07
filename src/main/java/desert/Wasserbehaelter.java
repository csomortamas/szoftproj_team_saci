package main.java.desert;
import org.tinylog.Logger;
import java.util.List;
import lombok.Getter; import lombok.Setter;

/**
 *
 */
public abstract class Wasserbehaelter {
    /**
     *
     */
    @Getter protected List<Rohr> rohre;

    @Getter private Pumpe p;
    /**
     * @param rohr
     */
    public void addRohr(Rohr rohr) {
        rohre.add(rohr);

        Logger.info("Rohr wurde");
    }

    /**
     * Konstruktor
     */
    public Wasserbehaelter() {
        Logger.info("Wasserbehaelter wurde erschaffen");
    }

}
