package main.java.desert;
import lombok.Getter;
import lombok.Setter;
import org.tinylog.Logger;
import java.util.List;

/**
 *
 */
public abstract class Netzelement {

    /**
     *
     */
    @Getter @Setter protected boolean istAktiv;

    /**
     *
     */
    @Getter @Setter protected boolean istKaputt;
    /**
     *
     */
    @Getter @Setter protected int ID;

    /**
     *
     */
    @Getter @Setter protected int kapazitaet;

}
