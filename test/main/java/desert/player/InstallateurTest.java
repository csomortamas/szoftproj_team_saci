package main.java.desert.player;

import main.java.desert.control.Kontroller;
import main.java.desert.network.Pumpe;
import main.java.desert.network.Rohr;
import main.java.desert.network.Wasserquelle;
import main.java.desert.network.Zisterne;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InstallateurTest {
    Wasserquelle wq;
    Installateur ins;
    Pumpe p1;
    Zisterne z1;
    @BeforeEach
    public void init(){
        wq = new Wasserquelle(1,1);
        ins = new Installateur(wq);
        p1 = new Pumpe(5,5);
        z1 = new Zisterne(10,1);
    }



    @Test
    void pumpeEinmontierenInDerWueste() {
        ins.setPumpeInHand(p1);
        ins.pumpeEinmontieren(false);
        Assertions.assertTrue(Kontroller.getKontroller().getMap().getPumpen().contains(p1));

    }
    @Test
    void pumpeEinmontierenImRohr() {
        Rohr r1 =new Rohr();
        Pumpe nachbarnP1 = new Pumpe(5,2);
        Pumpe nachbarnP2 = new Pumpe(5,6);
        Kontroller.getKontroller().getMap().getRohre().add(r1);
        Kontroller.getKontroller().binden(r1,nachbarnP1,nachbarnP2);
        ins.setPumpeInHand(p1);
        ins.setPosition(r1);
        ins.pumpeEinmontieren(true);
        Assertions.assertEquals(p1,ins.getPosition());
    }

    @Test
    void pumpeAufnehmenWennMoeglichIst() {
        ins.setPosition(z1);
        z1.setPumpeZurVerfuegung(p1);
        Kontroller.getKontroller().getMap().getZisternen().add(z1);

        ins.pumpeAufnehmen();
        Assertions.assertEquals(p1, ins.getPumpeInHand());
    }

    @Test
    void pumpeAufnehmenWennNichtMoglich(){
        ins.setPosition(z1);
        Kontroller.getKontroller().getMap().getZisternen().add(z1);
        ins.pumpeAufnehmen();
        Assertions.assertEquals(null,ins.getPumpeInHand());
    }
}