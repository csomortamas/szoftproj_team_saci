package main.java.desert.control;
import lombok.Getter; import lombok.Setter;
import main.java.desert.network.*;
import main.java.desert.player.Installateur;
import main.java.desert.player.Saboteur;

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
    @Getter protected List<Wasserquelle> alleWasserQuellen = new ArrayList<>();

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
    @Getter @Setter private String installateurTeamName;

    /**
     *
     */
    @Getter @Setter public String saboteurTeamName;

    /**
     *
     */
    @Getter @Setter private List<Installateur> installateurTeam = new ArrayList<>();

    /**
     *
     */
    @Getter @Setter private List<Saboteur> saboteurTeam = new ArrayList<>();

    /**
     *
     */
    @Getter @Setter private int installateurPunkte;
    @Getter @Setter private int saboteurPunkte;


    public void binden(Rohr rohr, Netzelement left, Netzelement right) {
        if(left instanceof Pumpe && right instanceof Pumpe) {
            rohr.getNachbarn().add(left);
            rohr.getNachbarn().add(right);

            left.getNachbarn().add(rohr);
            right.getNachbarn().add(rohr);

            Logger.info("Rohr zwischen Pumpe {} und Pumpe {} gebunden", left, right);
        }
    }

    /**
     * @return
     */
    public void pumpeErstellen() {
        Random rand = new Random();
        int randomNumber = rand.nextInt(1);
        int randomZisterneIndex = rand.nextInt(alleZisternen.size());

        if(randomNumber == 0)
            alleZisternen.get(randomZisterneIndex).setPumpeZurVerfuegung(new Pumpe());
    }
    public void rohrErstellen() {
        Random rand = new Random();
        int randomNumber = rand.nextInt(1);
        int randomZisterneIndex = rand.nextInt(alleZisternen.size());

        if(randomNumber == 0) {
            Rohr newRohr = new Rohr();
            alleZisternen.get(randomZisterneIndex).getNachbarn().add(newRohr);
            newRohr.getNachbarn().add(alleZisternen.get(randomZisterneIndex));

            alleZisternen.get(randomZisterneIndex).getNachbarn().add(newRohr);
        }
    }

    public void pumpeKaputtMacht() {
        Random rand = new Random();
        int randomNumber = rand.nextInt(5);
        int randomPumpeIndex = rand.nextInt(allePumpen.size());

        // Pumpe Random kaputt machen
        if (allePumpen.size() > 0 && randomNumber == 1) {
            allePumpen.get(randomPumpeIndex).kaputtMachen();
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
            if (zisterne.getEingangsRohr() != null) {
                if (zisterne.getEingangsRohr().isIstAktiv() && !zisterne.getEingangsRohr().isIstKaputt()) {
                    installateurPunkte++;
                }
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
        rohrErstellen();

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
        allePumpen.add(pumpe);

        Logger.info("Eine Pumpe wurde hinzugefügt");
    }

    /**
     * @param rohr
     *
     */
    public void addRohr(Rohr rohr) {
        alleRohre.add(rohr);

        Logger.info("Ein Rohr wurde hinzugefügt");
    }

    public void setup(List<Wasserquelle> wasserquellen, List<Zisterne> zisternen, List<Pumpe> pumpen, List<Rohr> rohre) {      // binden
        // setup

        // add to kontroller
        allePumpen.add(pumpen.get(0));
        allePumpen.add(pumpen.get(1));
        allePumpen.add(pumpen.get(2));

        alleRohre.add(rohre.get(0));
        alleRohre.add(rohre.get(1));
        alleRohre.add(rohre.get(2));
        alleRohre.add(rohre.get(3));
        alleRohre.add(rohre.get(4));

        alleZisternen.add(zisternen.get(0));
        alleZisternen.add(zisternen.get(1));
        alleZisternen.add(zisternen.get(2));

        alleWasserQuellen.add(wasserquellen.get(0));
        alleWasserQuellen.add(wasserquellen.get(1));
        alleWasserQuellen.add(wasserquellen.get(2));

        binden(rohre.get(0), wasserquellen.get(0), pumpen.get(0));
        binden(rohre.get(1), pumpen.get(0), zisternen.get(0));
        binden(rohre.get(2), zisternen.get(0), pumpen.get(1));
        binden(rohre.get(3), wasserquellen.get(2), pumpen.get(2));
        binden(rohre.get(4), pumpen.get(2), zisternen.get(2));


        wasserquellen.get(0).setAusgangsRohr(rohre.get(0));
        zisternen.get(0).setEingangsRohr(rohre.get(1));
        pumpen.get(0).setEingangsRohr(rohre.get(0));
        pumpen.get(0).setAusgangsRohr(rohre.get(1));

        wasserquellen.get(2).setAusgangsRohr(rohre.get(3));
        pumpen.get(2).setEingangsRohr(rohre.get(3));
        pumpen.get(2).setAusgangsRohr(rohre.get(4));
        zisternen.get(2).setEingangsRohr(rohre.get(4));




        // logger
        Logger.info("Setup erfolgreich");
    }

    public void game() {
        System.out.print(alleZisternen.get(0).getNachbarn().size());


        int spielerZahlProTeam = installateurTeam.size();
        Scanner scanner = new Scanner(System.in);
        int choose;

        //System.out.println("Eralubene befähle:\n" + "");
        while (aktuelleRunde < maxRunde) {
            System.out.print("\nTeam Installateur: Mit welchen Spieler möchtest du spielen? (Index: <1-"+spielerZahlProTeam+">)\n>");
            for (int i =0; i < spielerZahlProTeam; i++){
                System.out.println((i+1) + ": " + installateurTeam.get(i).getName()+"\n");
            }

            choose = scanner.nextInt();

            // installateur
            Installateur installateur = installateurTeam.get(choose-1);

            // installateur step
            System.out.print("\nWohin möchtest du gehen? (Index: <1-" + installateur.getPosition().getNachbarn().size() + ">, 0: stehen bleiben)\n>");
            for (int i =0; i < installateur.getPosition().getNachbarn().size(); i++){
                System.out.println((i+1) + ": " + installateur.getPosition().getNachbarn().get(i).getName()+"\n");
            }
            choose=scanner.nextInt();

            if(choose!=0){
                installateur.step(installateur.getPosition().getNachbarn().get(choose-1));
            }

            // installateur aktion
            if(installateur.getPosition() instanceof Pumpe){
                System.out.print("\nSpieler: Welchen Aktion möchtest du durchführen? " +
                        "\n1: Eingangsrohr Umstellen, " +
                        "\n2: Ausgangsrohr Umstellen" +
                        "\n3: Pumpe aktivieren" +
                        "\n4: Pumpe deaktivieren" +
                        "\n5: Pumpe aufnehmen" +
                        "\n>");
                choose = scanner.nextInt();
                Pumpe aktuellePumpe = null;

                for (Pumpe pumpe : allePumpen) {
                    if (pumpe==installateur.getPosition()){
                        aktuellePumpe=pumpe;
                    }
                }


                switch(choose) {
                    case 1: {
                        System.out.println("Rohr auswählen  ");
                        for (int i =0; i < installateur.getPosition().getNachbarn().size(); i++){
                            System.out.println((i+1) + ": " + installateur.getPosition().getNachbarn().get(i).getName()+"\n");
                        }
                        choose = scanner.nextInt();
                        for (Rohr r:alleRohre){
                            if (r==aktuellePumpe.getNachbarn().get(choose - 1)){
                                aktuellePumpe.setEingangsRohr(r);
                                break;
                            }
                        }
                        break;
                    }
                    case 2: {
                        System.out.println("Rohr auswählen ");
                        for (int i =0; i < installateur.getPosition().getNachbarn().size(); i++){
                            System.out.println((i+1) + ": " + installateur.getPosition().getNachbarn().get(i).getName()+"\n");
                        }
                        choose = scanner.nextInt();
                        for (Rohr r:alleRohre){
                            if (r==aktuellePumpe.getNachbarn().get(choose - 1)){
                                aktuellePumpe.setAusgangsRohr(r);
                                break;
                            }
                        }
                        break;
                    }
                    case 3: {
                        installateur.pumpeSchalten(true);
                        break;
                    }
                    case 4: {
                        installateur.pumpeSchalten(false);
                        break;
                    }
                    case 5: {
                        installateur.pumpeAufnehmen();
                        break;
                    }
                }
            }else{
                System.out.print("\nSpieler: Welchen Aktion möchtest du durchführen? " +
                                "\n1: Rohr reparieren" +
                                "\n2: Rohr umbinden" +
                                "\n3: Pumpe einmontieren" +
                                "\n>");
                choose = scanner.nextInt();
                switch(choose){
                    case 1:{
                        installateur.getPosition().reparieren();
                        break;
                    }
                    case 2:{
                        System.out.print("\nIndex, woher ausbinden (Index: <1-"+(installateur.getPosition().getNachbarn().size())+">)\n");
                        for (int i =0; i < installateur.getPosition().getNachbarn().size(); i++){
                            System.out.println((i+1) + ": " + installateur.getPosition().getNachbarn().get(i).getName()+"\n");
                        }
                        choose = scanner.nextInt() ;
                        System.out.print("\nIndex, wohin einbinden (Index: <1-"+(allePumpen.size())+">) ausser der index: "+ choose+"> \n");
                        for (int i =0; i < allePumpen.size(); i++){
                            System.out.println((i+1) + ": " + allePumpen.get(i).getName()+"\n");
                        }
                        int chooseWohin = scanner.nextInt();

                        for (Pumpe pumpe: allePumpen) {
                            if (installateur.getPosition().getNachbarn().get(choose-1) == pumpe)
                                installateur.umbinden(pumpe, allePumpen.get(chooseWohin-1));
                        }
                        break;
                    }
                    case 3:{
                        System.out.println("In die Mitte des Rohres? (1 = yes/0 = no)");
                        choose = scanner.nextInt();
                        if(choose == 1){
                            installateur.pumpeEinmontieren(true);
                        }
                        else{
                            installateur.pumpeEinmontieren(false);
                        }
                        break;
                    }
                }
            }


            // saboteur
            System.out.print("\nTeam Saboteur: Mit welchen Spieler möchtest du spielen? (Index: <1-"+spielerZahlProTeam+">)\n>");
            for (int i =0; i < spielerZahlProTeam; i++){
                System.out.println((i+1) + ": " + saboteurTeam.get(i).getName()+"\n");
            }
            choose = scanner.nextInt();

            Saboteur saboteur = saboteurTeam.get(choose-1);

            //saboteur step
            System.out.print("\nWohin möchtest du gehen? (Index: <1-" + saboteur.getPosition().getNachbarn().size() + ">, 0: stehen bleiben)\n>");
            for (int i =0; i < saboteur.getPosition().getNachbarn().size(); i++){
                System.out.println((i+1) + ": " + saboteur.getPosition().getNachbarn().get(i).getName()+"\n");
            }
            choose=scanner.nextInt();

            if(choose!=0){
                saboteur.step(saboteur.getPosition().getNachbarn().get(choose-1));
            }
            if(saboteur.getPosition() instanceof Pumpe){
                System.out.print("\nSpieler: Welchen Aktion möchtest du durchführen? " +
                        "\n1: Eingangsrohr Umstellen, " +
                        "\n2: Ausgangsrohr Umstellen" +
                        "\n3: Pumpe aktivieren" +
                        "\n4: Pumpe schalten" +
                        "\n>");
                choose = scanner.nextInt();
                Pumpe aktuellePumpe = null;

                for (Pumpe pumpe : allePumpen) {
                    if (pumpe==saboteur.getPosition()){
                        aktuellePumpe=pumpe;
                    }
                }

                switch(choose) {
                    case 1: {
                        System.out.println("Rohr auswählen ");
                        for (int i =0; i < saboteur.getPosition().getNachbarn().size(); i++){
                            System.out.println((i+1) + ": " + saboteur.getPosition().getNachbarn().get(i).getName()+"\n");
                        }
                        choose = scanner.nextInt();
                        for (Rohr r:alleRohre){
                            if (r==aktuellePumpe.getNachbarn().get(choose - 1)){
                                aktuellePumpe.setEingangsRohr(r);
                            }
                        }
                        break;
                    }
                    case 2: {
                        System.out.println("Rohr auswählen ");
                        for (int i =0; i < saboteur.getPosition().getNachbarn().size(); i++){
                            System.out.println((i+1) + ": " + saboteur.getPosition().getNachbarn().get(i).getName()+"\n");
                        }
                        choose = scanner.nextInt();
                        for (Rohr r:alleRohre){
                            if (r==aktuellePumpe.getNachbarn().get(choose - 1)){
                                aktuellePumpe.setAusgangsRohr(r);
                            }
                            break;
                        }
                        break;
                    }
                    case 3: {
                        saboteur.pumpeSchalten(true);
                        break;
                    }
                    case 4: {
                        saboteur.pumpeSchalten(false);
                        break;
                    }
                }
            }else {
                System.out.print("\nSpieler: Welchen Aktion möchtest du durchführen? " +
                        "\n1: Rohr loechern" +
                        "\n2: Rohr umbinden" +
                        "\n>");
                choose = scanner.nextInt();

                switch (choose) {
                    case 1: {
                        saboteur.getPosition().kaputtMachen();
                        break;
                    }
                    case 2: {
                        System.out.print("\nIndex, woher ausbinden (Index: <1-" + (saboteur.getPosition().getNachbarn().size()) + ">)\n");
                        for (int i =0; i < saboteur.getPosition().getNachbarn().size(); i++){
                            System.out.println((i+1) + ": " + saboteur.getPosition().getNachbarn().get(i).getName()+"\n");
                        }
                        choose = scanner.nextInt();
                        System.out.print("\nIndex, wohin einbinden (Index: <1-" + (allePumpen.size()) + ">) ausser der index: " + choose + "> \n");
                        for (int i =0; i < allePumpen.size(); i++){
                            System.out.println((i+1) + ": " + allePumpen.get(i).getName()+"\n");
                        }
                        int chooseWohin = scanner.nextInt();

                        for (Pumpe pumpe : allePumpen) {
                            if (saboteur.getPosition().getNachbarn().get(choose-1) == pumpe)
                                saboteur.umbinden(pumpe, allePumpen.get(chooseWohin-1));
                        }
                        break;
                    }
                }
            }


            // ende der runde
            tick();
            System.out.println("Saboteur: " + Kontroller.getKontroller().getSaboteurPunkte());
            System.out.println("Installateur: " + Kontroller.getKontroller().getInstallateurPunkte());
        }
    }
}
