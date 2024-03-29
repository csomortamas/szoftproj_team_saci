package desert.player;

import desert.control.Kontroller;
import desert.network.Pumpe;
import desert.network.Rohr;
import desert.network.Wasserquelle;
import desert.network.Zisterne;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SpielerTest {

    @Test
    void eingangsrohrUmstellen() {
        Pumpe pumpe1 = new Pumpe(5,2);
        Pumpe pumpe2 = new Pumpe(5,6);
        Rohr r1 = new Rohr();

        Kontroller.getKontroller().getMap().getPumpen().add(pumpe1);
        Kontroller.getKontroller().getMap().getPumpen().add(pumpe2);
        Kontroller.getKontroller().getMap().getRohre().add(r1);

        Installateur spieler = new Installateur(new Wasserquelle(1,2));
        spieler.setPosition(pumpe1);
        Kontroller.getKontroller().binden(r1, pumpe1, pumpe2);

        spieler.eingangsRohrUmstellen(r1);
        assertEquals(r1, pumpe1.getEingangsRohr());

    }

    @Test
    void ausgangsrohrUmstellen(){
        Pumpe pumpe1 = new Pumpe(5,2);
        Pumpe pumpe2 = new Pumpe(5,6);
        Rohr r1 = new Rohr();

        Kontroller.getKontroller().getMap().getPumpen().add(pumpe1);
        Kontroller.getKontroller().getMap().getPumpen().add(pumpe2);
        Kontroller.getKontroller().getMap().getRohre().add(r1);

        Installateur spieler = new Installateur(new Wasserquelle(1,2));
        spieler.setPosition(pumpe1);
        Kontroller.getKontroller().binden(r1, pumpe1, pumpe2);

        spieler.ausgangsRohrUmstellen(r1);
        assertEquals(r1, pumpe1.getAusgangsRohr());
    }
    @Test
    void rohrUmbinden() {
        Pumpe pumpe1 = new Pumpe(5,2);
        Pumpe pumpe2 = new Pumpe(5,5);
        Pumpe pumpe3 = new Pumpe(5,8);
        Rohr r1 = new Rohr();
        Kontroller.getKontroller().addPumpe(pumpe1);
        Kontroller.getKontroller().addPumpe(pumpe2);
        Kontroller.getKontroller().addPumpe(pumpe3);
        Kontroller.getKontroller().addRohr(r1);
        Installateur installateur = new Installateur(new Wasserquelle(1,2));
        Kontroller.getKontroller().binden(r1, pumpe1, pumpe2);
        installateur.setPosition(r1);
        installateur.umbinden(pumpe2, pumpe3);
        boolean oldRemoved = !pumpe2.getNachbarn().contains(r1);
        boolean newAdded = pumpe3.getNachbarn().contains(r1);
        Assertions.assertTrue(oldRemoved);
        Assertions.assertTrue(newAdded);
    }

    @Test
    void Step() {
        Wasserquelle pumpe0 = new Wasserquelle(0,1);
        Pumpe pumpe1 = new Pumpe(5,5);
        Zisterne pumpe2 = new Zisterne(5,7);
        Rohr r1 = new Rohr();
        Rohr r2 = new Rohr();

        Kontroller.getKontroller().getMap().getWasserquellen().add(pumpe0);
        Kontroller.getKontroller().getMap().getPumpen().add(pumpe1);
        Kontroller.getKontroller().getMap().getZisternen().add(pumpe2);
        Kontroller.getKontroller().getMap().getRohre().add(r1);
        Kontroller.getKontroller().getMap().getRohre().add(r2);

        Installateur spieler1 = new Installateur(pumpe0);
        Kontroller.getKontroller().getInstallateurTeam().add(spieler1);

        Saboteur spieler2 = new Saboteur(pumpe2);
        Kontroller.getKontroller().getSaboteurTeam().add(spieler2);

        Kontroller.getKontroller().binden(r1, pumpe0, pumpe1);
        Kontroller.getKontroller().binden(r2, pumpe0, pumpe2);

        spieler1.step(pumpe1);
        assertEquals(pumpe0, spieler1.getPosition());    //pumpe1 ist nicht ein nachbar von pumpe0 => Operation nicht möglich!

        spieler1.step(r2);
        assertEquals(r2, spieler1.getPosition());    //r2 ist ein nachbar von pumpe0 => Operation möglich!

        spieler1.step(pumpe1);
        assertEquals(r2, spieler1.getPosition());   //pumpe1 ist nicht ein nachbar von r2 => Operation nicht möglich!

        spieler2.step(r2);
        assertEquals(pumpe2, spieler2.getPosition());    //spieler1 steht schon auf r2 => Operation nicht möglich!

        spieler1.step(pumpe2);
        assertEquals(pumpe2, spieler1.getPosition());   ///pumpe2 ist ein nachbar von r2 => Operation möglich!

        spieler2.step(r2);
        assertEquals(r2, spieler2.getPosition());    //Niemand steht auf r2 => Operation  möglich!
    }

    @Test
    void eingangsRohrUmstellenAufAusgangsrohr() {
        Pumpe pumpe1 = new Pumpe(5,2);
        Kontroller.getKontroller().addPumpe(pumpe1);
        Rohr r1 = new Rohr();
        Rohr r2 = new Rohr();
        Kontroller.getKontroller().addRohr(r1);
        Kontroller.getKontroller().addRohr(r2);

        pumpe1.getNachbarn().add(r1);
        pumpe1.getNachbarn().add(r2);

        Installateur installateur = new Installateur(new Wasserquelle(1,1));
        installateur.setPosition(pumpe1);

        pumpe1.setEingangsRohr(r1);
        installateur.ausgangsRohrUmstellen(r1);

        Assertions.assertNull(pumpe1.getAusgangsRohr());
    }

    @Test
    void pumpeAktivieren() {
        Pumpe pumpe = new Pumpe(5,2);
        Kontroller.getKontroller().addPumpe(pumpe);
        Installateur installateur = new Installateur(new Wasserquelle(1,1));
        installateur.setPosition(pumpe);
        installateur.pumpeSchalten(true);
        Assertions.assertTrue(pumpe.isAktiv());
    }

    @Test
    void pumpeDeaktivieren() {
        Pumpe pumpe = new Pumpe(5,2);
        Kontroller.getKontroller().addPumpe(pumpe);
        Installateur installateur = new Installateur(new Wasserquelle(1,1));
        installateur.setPosition(pumpe);
        installateur.pumpeSchalten(false);
        Assertions.assertFalse(pumpe.isAktiv());
    }

    @Test
    void pumpeDeaktivierenWennEsSchonDeaktiviertIst(){
        Pumpe pumpe = new Pumpe(5,2);
        Kontroller.getKontroller().addPumpe(pumpe);
        Installateur installateur = new Installateur(new Wasserquelle(1,1));
        installateur.setPosition(pumpe);
        pumpe.setIstAktiv(false);
        installateur.pumpeSchalten(false);
        Assertions.assertFalse(pumpe.isAktiv());
    }
}