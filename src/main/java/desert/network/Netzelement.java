package desert.network;

import desert.gui.GuiMap;
import org.tinylog.Logger;

import java.util.ArrayList;
import java.util.List;

public abstract class Netzelement {

    protected String name;

    protected boolean istAktiv;

    protected boolean istKaputt = false;

    protected boolean istBesetzt = false;

    protected transient List<Netzelement> nachbarn = new ArrayList<>();


    public void kaputtMachen() {
        istKaputt = true;
        Logger.info("Netzelement {} ist kaputt.", this);
        GuiMap.getGuiMap().refreshRohrColor();
        GuiMap.getGuiMap().refreshPumpen();
    }

    public void reparieren() {
        istKaputt = false;
        Logger.info("Netzelement {} wurde repariert.", this);
        GuiMap.getGuiMap().refreshRohrColor();
    }

    //===============================GETTERS & SETTERS=======================================
    //=======================================================================================

    public void setName(String name) {
        this.name = name;
    }

    public boolean isAktiv() {
        return istAktiv;
    }

    public void setIstAktiv(boolean istAktiv) {
        this.istAktiv = istAktiv;
        GuiMap.getGuiMap().refreshPumpen();
    }

    public boolean isIstKaputt() {
        return istKaputt;
    }

    public boolean isBesetzt() {
        return istBesetzt;
    }

    public void setIstBesetzt(boolean istBesetzt) {
        this.istBesetzt = istBesetzt;
    }

    public List<Netzelement> getNachbarn() {
        return nachbarn;
    }

}
