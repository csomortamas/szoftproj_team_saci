package main.java.desert.network;

import lombok.Getter;
import lombok.Setter;
import main.java.desert.control.Kontroller;
import org.tinylog.Logger;

import java.util.ArrayList;
import java.util.List;

public class Rohr extends Netzelement {

    /**
     *
     */
    @Getter @Setter private boolean besetzt;


    /**
     * Default Konstruktor
     */
    public Rohr() {
        istAktiv = false;
        Logger.info("Neues Rohr erstellt.");
    }

    /**
     * @return
     */
    public void rohrSplit(Pumpe pumpeInHand) {
        Rohr r1 = new Rohr();
        Rohr r2 = new Rohr();

        Kontroller.getKontroller().getMap().getRohre().remove(this);

        Kontroller.getKontroller().addRohr(r1);
        Kontroller.getKontroller().addRohr(r2);

        // binden
        Kontroller.getKontroller().binden(r1, this.getNachbarn().get(0), pumpeInHand);
        Kontroller.getKontroller().binden(r2, pumpeInHand, this.getNachbarn().get(1));
    }
}
