package desert.player;

import desert.control.Kontroller;
import desert.network.Pumpe;
import desert.network.Rohr;
import desert.network.Wasserquelle;
import desert.network.Zisterne;
import desert.player.Installateur;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
        Assertions.assertNull(ins.getPumpeInHand());
    }
}