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
    @Getter @Setter protected List<Rohr> rohre;


    /**
     * @param rohr
     */
    public void addRohr(Rohr rohr) {
        // TODO implement here
    }

    /**
     * Konstruktor
     */
    public Wasserbehaelter() {

    }

}
