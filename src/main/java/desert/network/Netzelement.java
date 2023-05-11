package desert.network;

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
    protected String name;
    /**
     *
     */
    protected boolean istAktiv;

    /**
     *
     */
    protected boolean istKaputt = false;

    /**
     *
     */
    protected boolean istBesetzt = false;

    /**
     *
     */
    protected transient List<Netzelement> nachbarn = new ArrayList<>();


    /**
     *
     */
    protected Integer hash;
    /**
     *
     */
    protected List<Integer> nachbarnHash = new ArrayList<>();

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

    //=======================================================================================
    //=======================================================================================

    public List<Integer> getNachbarnHash() {
        return nachbarnHash;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isIstAktiv() {
        return istAktiv;
    }

    public void setIstAktiv(boolean istAktiv) {
        this.istAktiv = istAktiv;
    }

    public boolean isIstKaputt() {
        return istKaputt;
    }

    public void setIstKaputt(boolean istKaputt) {
        this.istKaputt = istKaputt;
    }

    public boolean isIstBesetzt() {
        return istBesetzt;
    }

    public void setIstBesetzt(boolean istBesetzt) {
        this.istBesetzt = istBesetzt;
    }

    public List<Netzelement> getNachbarn() {
        return nachbarn;
    }

    public void setNachbarn(List<Netzelement> nachbarn) {
        this.nachbarn = nachbarn;
    }

    public Integer getHash() {
        return hash;
    }

    public void setHash(Integer hash) {
        this.hash = hash;
    }
}
