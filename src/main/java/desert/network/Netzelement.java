package main.java.desert.network;
import lombok.Getter;
import lombok.Setter;
import org.tinylog.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public abstract class Netzelement {

    /**
     *
     */
    @Getter @Setter protected String name;
    /**
     *
     */
    @Getter @Setter protected boolean istAktiv;

    /**
     *
     */
    @Getter @Setter protected boolean istKaputt = false;

    /**
     *
     */
    @Getter @Setter protected boolean istBesetzt = false;

    /**
     *
     */
    @Getter @Setter protected List<Netzelement> nachbarn = new ArrayList<>();

    /**
     *
     */
    public void kaputtMachen() {
        istKaputt = true;
        Logger.info("Netzelement {} ist kaputt.", this);
    }

    /**
     *
     */
    public void reparieren() {
        istKaputt = false;
        Logger.info("Netzelement {} wurde repariert.", this);
    }
}
