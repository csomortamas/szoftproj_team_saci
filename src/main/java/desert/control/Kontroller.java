package main.java.desert.control;
import lombok.Getter; import lombok.Setter;
import main.java.desert.network.*;
import main.java.desert.player.Spieler;
import org.tinylog.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 *
 */
public class Kontroller {
    private static Kontroller kontroller = new Kontroller();
    private Kontroller(){}
    public static Kontroller getKontroller(){
        return kontroller;
    }
    /**
     *
     */
    @Getter protected List<Pumpe> allePumpen = new ArrayList<>();
    /**
     *
     */
    @Getter protected List<Zisterne> alleZisternen = new ArrayList<>();

    /**
     *
     */
    @Getter protected List<Rohr> alleRohre = new ArrayList<>();

    /**
     *
     */
    @Getter @Setter private int aktuelleRunde;

    /**
     *
     */
    @Getter @Setter private int maxRunde = 50;

    /**
     *
     */
    @Getter @Setter private String team1;

    /**
     *
     */
    @Getter @Setter public String team2;

    /**
     *
     */
    @Getter @Setter private int installateurPunkte;
    @Getter @Setter private int saboteurPunkte;


    public void pumpeKaputtMacht() {
        Random rand = new Random();
        int randomNumber = rand.nextInt(5);
        int randomPumpeIndex = rand.nextInt(allePumpen.size());

        // Pumpe Random kaputt machen
        if (allePumpen.size() > 0 && randomNumber == 1) {
            allePumpen.get(randomPumpeIndex).kaputtMachen();
        }
    }

    public void binden(Rohr rohr, Netzelement left, Netzelement right) {
        if(left instanceof Pumpe && right instanceof Pumpe) {
            rohr.getNachbarn().add(left);
            rohr.getNachbarn().add(right);

            left.getNachbarn().add(rohr);
            right.getNachbarn().add(rohr);

            Logger.info("Rohr zwischen Pumpe {} und Pumpe {} gebunden", left, right);
        }
    }

    public void setup(Wasserquelle wasserquelle, Zisterne zisterne, Pumpe pumpe1, Pumpe pumpe2, Rohr rohr1, Rohr rohr2, Rohr rohr3, Spieler installateur, Spieler saboteur) {
        // setup
        // binden
        binden(rohr1, wasserquelle, pumpe1);
        binden(rohr2, pumpe1, zisterne);
        binden(rohr3, zisterne, pumpe2);

        // add to kontroller
        allePumpen.add(pumpe1);
        allePumpen.add(pumpe2);
        alleRohre.add(rohr1);
        alleRohre.add(rohr2);
        alleRohre.add(rohr3);
        alleZisternen.add(zisterne);

        // wasserquell ausgangrohr zu rohr1
        wasserquelle.setAusgangsRohr(rohr1);
        rohr1.setIstAktiv(true);

        // zisterne eingangrohr zu rohr2
        zisterne.setEingangsRohr(rohr3);

        // pumpe eingangrohr zu rohr1
        pumpe1.setEingangsRohr(rohr1);

        // pumpe ausgangrohr zu rohr2
        pumpe1.setAusgangsRohr(rohr2);

        // logger
        Logger.info("Setup erfolgreich");
    }

    /**
     * @return
     */
    public void pumpeErstellen() {
        Random rand = new Random();
        int randomNumber = rand.nextInt(10);
        int randomZisterneIndex = rand.nextInt(alleZisternen.size());

        if(randomNumber == 0)
            alleZisternen.get(randomZisterneIndex).setPumpeZurVerfuegung(new Pumpe());
    }
    public void rohrErstellen() {
        Random rand = new Random();
        int randomNumber = rand.nextInt(7);
        int randomZisterneIndex = rand.nextInt(alleZisternen.size());

        if(randomNumber == 0) {
            Rohr newRohr = new Rohr();
            alleZisternen.get(randomZisterneIndex).getNachbarn().add(newRohr);
            newRohr.getNachbarn().add(alleZisternen.get(randomZisterneIndex));

            alleZisternen.get(randomZisterneIndex).getNachbarn().add(newRohr);
        }
    }

    /**
     * @param
     *
     */
    public void punkteKalkulieren() {
        for (Rohr rohr : alleRohre) {
            if (rohr.isIstAktiv() && rohr.isIstKaputt()) {
                saboteurPunkte++;
            }
        }

        for (Zisterne zisterne : alleZisternen) {
            if (zisterne.getEingangsRohr().isIstAktiv() && !zisterne.getEingangsRohr().isIstKaputt()) {
                installateurPunkte++;
            }
        }
    }

    /**
     *
     */
    public void tick() {
        for(int i = 0; i < alleRohre.size() + 1; i++) {
            for (Pumpe pumpe : allePumpen) {
                pumpe.wasserWeiterleiten();
            }
        }

        punkteKalkulieren();

        aktuelleRunde++;
        pumpeKaputtMacht();
        pumpeErstellen();

        Logger.info("Tick");
    }

    /**
     * @param zisterne
     *      */
    public void addZisterne(Zisterne zisterne) {
        alleZisternen.add(zisterne);

        Logger.info("Eine Zisterne wurde hinzugefügt");
    }

    /**
     * @param pumpe
     *
     */
    public void addPumpe(Pumpe pumpe) {

    }

    /**
     * @param rohr
     *
     */
    public void addRohr(Rohr rohr) {

    }

    public void gameLoop() {
        while (aktuelleRunde < maxRunde) {

            tick();
        }
    }
}
