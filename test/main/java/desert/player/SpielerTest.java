package main.java.desert.player;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SpielerTest {

    @Test
    void eingangsrohrUmstellen() {
        Pumpe pumpe1 = new Pumpe();
        Pumpe pumpe2 = new Pumpe();
        Rohr r1 = new Rohr();

        Kontroller.getKontroller().getMap().getPumpen().add(pumpe1);
        Kontroller.getKontroller().getMap().getPumpen().add(pumpe2);
        Kontroller.getKontroller().getMap().getRohre().add(r1);

        Installateur spieler = new Installateur(new Wasserquelle());
        spieler.setPosition(pumpe1);
        Kontroller.getKontroller().binden(r1, pumpe1, pumpe2);

        spieler.eingangsRohrUmstellen(r1);
        assertEquals(r1, pumpe1.getEingangsRohr());

    }

    @Test
    void ausgangsrohrUmstellen(){
        Pumpe pumpe1 = new Pumpe();
        Pumpe pumpe2 = new Pumpe();
        Rohr r1 = new Rohr();

        Kontroller.getKontroller().getMap().getPumpen().add(pumpe1);
        Kontroller.getKontroller().getMap().getPumpen().add(pumpe2);
        Kontroller.getKontroller().getMap().getRohre().add(r1);

        Installateur spieler = new Installateur(new Wasserquelle());
        spieler.setPosition(pumpe1);
        Kontroller.getKontroller().binden(r1, pumpe1, pumpe2);

        spieler.ausgangsRohrUmstellen(r1);
        assertEquals(r1, pumpe1.getAusgangsRohr());
    }
    @Test
    void umbinden() {
    }

    @Test
    void Step() {
        Wasserquelle pumpe0 = new Wasserquelle();
        Pumpe pumpe1 = new Pumpe();
        Zisterne pumpe2 = new Zisterne();
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
    void eingangsRohrUmstellen() {
    }

    @Test
    void ausgangsRohrUmstellen() {
    }
}