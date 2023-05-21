package desert.control;

import desert.gui.GuiMap;
import desert.network.*;
import desert.player.Installateur;
import desert.player.Saboteur;
import desert.player.Spieler;
import org.tinylog.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 *
 */
public class Kontroller {
    private static final Kontroller kontroller = new Kontroller();

    private Kontroller() {
    }

    public static Kontroller getKontroller() {
        return kontroller;
    }


    /**
     *
     */

    private GameMap map = new GameMap();


    /**
     *
     */

    private Leaderboard leaderboard = new Leaderboard();


    /**
     *
     */

    private int actionCount = 0;

    /**
     *
     */

    private int maxRunde = 3;

    /**
     *
     */

    private String installateurTeamName;

    /**
     *
     */

    private String saboteurTeamName;

    /**
     *
     */

    private List<Installateur> installateurTeam = new ArrayList<>();

    /**
     *
     */

    private List<Saboteur> saboteurTeam = new ArrayList<>();

    /**
     *
     */

    private int installateurPunkte;
    /**
     *
     */

    private int saboteurPunkte;

    private Spieler selectedPlayer;

    private Spieler lastSelectedPlayer;

    public void binden(Rohr rohr, Netzelement left, Netzelement right) {
        if (left instanceof Pumpe && right instanceof Pumpe) {
            rohr.getNachbarn().add(left);
            rohr.getNachbarn().add(right);

            left.getNachbarn().add(rohr);
            right.getNachbarn().add(rohr);

            Logger.info("Rohr zwischen Pumpe {} und Pumpe {} gebunden", left, right);
        }
    }

    /**
     *
     */
    public void pumpeErstellen() {
        Random rand = new Random();
        int randomNumber = rand.nextInt(4);
        int randomZisterneIndex = rand.nextInt(map.getZisternen().size());

        if (randomNumber == 2||true) {
            Pumpe p = new Pumpe(map.getZisternen().get(randomZisterneIndex).getPosX(), map.getZisternen().get(randomZisterneIndex).getPosY());
            map.getZisternen().get(randomZisterneIndex).setPumpeZurVerfuegung(p);
        }
    }

    public void rohrErstellen() {
        Random rand = new Random();
        int randomNumber = rand.nextInt(7);
        int randomZisterneIndex = rand.nextInt(map.getZisternen().size());

        if (randomNumber == 5) {
            Rohr newRohr = new Rohr();
            newRohr.setName("R-G");
            map.getZisternen().get(randomZisterneIndex).getNachbarn().add(newRohr);
            newRohr.getNachbarn().add(map.getZisternen().get(randomZisterneIndex));

            map.getZisternen().get(randomZisterneIndex).getNachbarn().add(newRohr);
            GuiMap.getGuiMap().refreshNewRohre(map.getZisternen().get(randomZisterneIndex), newRohr);
            map.getRohre().add(newRohr);
            //map.getZisternen().get(randomZisterneIndex).getButton().toFront();
            //System.out.println("lol");
        }
    }

    public void pumpeKaputtMacht() {
        Random rand = new Random();
        int randomNumber = rand.nextInt(5);
        int randomPumpeIndex = rand.nextInt(map.getPumpen().size());

        // Pumpe Random kaputt machen
        if (map.getPumpen().size() > 0 && randomNumber == 1) {
            map.getPumpen().get(randomPumpeIndex).kaputtMachen();
        }
    }

    /**
     *
     */
    public void punkteKalkulieren() {
        for (Rohr rohr : map.getRohre()) {
            if (rohr.isIstAktiv() && rohr.isIstKaputt()) {
                saboteurPunkte++;
            }
        }

        for (Zisterne zisterne : map.getZisternen()) {
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
        for (Wasserquelle quelle : map.getWasserquellen()) {
            if (quelle.getAusgangsRohr() != null)
                quelle.getAusgangsRohr().setIstAktiv(true);
        }
        for (int i = 0; i < map.getRohre().size() + 1; i++) {
            for (Pumpe pumpe : map.getPumpen()) {
                pumpe.wasserWeiterleiten();
            }
        }

        punkteKalkulieren();

        //aktuelleRunde++;
        pumpeKaputtMacht();
        pumpeErstellen();
        rohrErstellen();
        GuiMap.getGuiMap().refreshReadyPumps();
        GuiMap.getGuiMap().refreshPoints();
        Logger.info("Tick");
    }

    /**
     * @param zisterne
     */
    public void addZisterne(Zisterne zisterne) {
        map.getZisternen().add(zisterne);

        Logger.info("Eine Zisterne wurde hinzugefügt");
    }

    /**
     * @param pumpe
     */
    public void addPumpe(Pumpe pumpe) {
        map.getPumpen().add(pumpe);

        Logger.info("Eine Pumpe wurde hinzugefügt");
    }

    /**
     * @param rohr
     */
    public void addRohr(Rohr rohr) {
        map.getRohre().add(rohr);

        Logger.info("Ein Rohr wurde hinzugefügt");
    }


    public void setupV2(List<Wasserquelle> wasserquellen, List<Zisterne> zisternen, List<Pumpe> pumpen, List<Rohr> rohre, String teamInstName, String teamSabName) {      // binden
        // setup

        // add to kontroller
        installateurTeamName = teamInstName;
        saboteurTeamName = teamSabName;
        map = new GameMap();

        map.getPumpen().add(pumpen.get(0));
        map.getPumpen().add(pumpen.get(1));
        map.getPumpen().add(pumpen.get(2));

        map.getRohre().add(rohre.get(0));
        map.getRohre().add(rohre.get(1));
        map.getRohre().add(rohre.get(2));
        map.getRohre().add(rohre.get(3));
        map.getRohre().add(rohre.get(4));

        map.getZisternen().add(zisternen.get(0));
        map.getZisternen().add(zisternen.get(1));
        map.getZisternen().add(zisternen.get(2));

        map.getWasserquellen().add(wasserquellen.get(0));
        map.getWasserquellen().add(wasserquellen.get(1));
        map.getWasserquellen().add(wasserquellen.get(2));


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

        //map.saveMap();

        // logger
        Logger.info("Setup erfolgreich");
    }


    public void setup(String teamInstallateurName, String teamSaboteurName) {
        map = map.loadMap();
        installateurTeamName = teamInstallateurName;
        saboteurTeamName = teamSaboteurName;

        Logger.info("Setup erfolgreich");
    }
/*
    public void game() {
        int spielerZahlProTeam = installateurTeam.size();
        int choose;

       // while (aktuelleRunde < maxRunde) {
            if(aktuelleRunde % 2 == 0){
                for (int i = 0; i < spielerZahlProTeam; i++){
                    installateurTeam.get(i).getButton().setDisable(false);
                    saboteurTeam.get(i).getButton().setDisable(true);
                }
            }else{
                for (int i = 0; i < spielerZahlProTeam; i++){
                    installateurTeam.get(i).getButton().setDisable(true);
                    saboteurTeam.get(i).getButton().setDisable(false);
                }
            }

            System.out.println("Team Installateur: Mit welchem Spieler möchtest du spielen? (Index: <1-" + spielerZahlProTeam + ">)");
            for (int i = 0; i < spielerZahlProTeam; i++) {
                System.out.println((i + 1) + ": " + installateurTeam.get(i).getName());
            }


            System.out.print(">");
            choose = scanner.nextInt();

            // installateur
            Installateur installateur = installateurTeam.get(choose - 1);

            // installateur step
            System.out.println("Wohin möchtest du gehen? (Index: <1-" + installateur.getPosition().getNachbarn().size() + ">, 0: stehen bleiben)");
            for (int i = 0; i < installateur.getPosition().getNachbarn().size(); i++) {
                System.out.println((i + 1) + ": " + installateur.getPosition().getNachbarn().get(i).getName());
            }
            System.out.print(">");
            choose = scanner.nextInt();

            if (choose != 0) {
                installateur.step(installateur.getPosition().getNachbarn().get(choose - 1));
            }

            // installateur aktion
            if (installateur.getPosition() instanceof Pumpe) {
                System.out.print("\nSpieler: Welchen Aktion möchtest du durchführen? " +
                        "\n1: Eingangsrohr Umstellen, " +
                        "\n2: Ausgangsrohr Umstellen" +
                        "\n3: Pumpe aktivieren" +
                        "\n4: Pumpe deaktivieren" +
                        "\n5: Pumpe aufnehmen" +
                        "\n>");
                choose = scanner.nextInt();
                Pumpe aktuellePumpe = null;

                aktuellePumpe = getAktuellePosition(installateur, aktuellePumpe);


                switch (choose) {
                    case 1: {
                        System.out.println("Rohr auswählen:");
                        for (int i = 0; i < installateur.getPosition().getNachbarn().size(); i++) {
                            System.out.println((i + 1) + ": " + installateur.getPosition().getNachbarn().get(i).getName());
                        }
                        System.out.print(">");
                        choose = scanner.nextInt();
                        for (Rohr r : map.getRohre()) {
                            if (r == aktuellePumpe.getNachbarn().get(choose - 1)) {
                                installateur.eingangsRohrUmstellen(r);
                                //aktuellePumpe.setEingangsRohr(r);
                            }
                        }
                        break;
                    }
                    case 2: {
                        System.out.println("Rohr auswählen: ");
                        for (int i = 0; i < installateur.getPosition().getNachbarn().size(); i++) {
                            System.out.println((i + 1) + ": " + installateur.getPosition().getNachbarn().get(i).getName());
                        }
                        System.out.print(">");
                        choose = scanner.nextInt();
                        for (Rohr r : map.getRohre()) {
                            if (r == aktuellePumpe.getNachbarn().get(choose - 1)) {
                                installateur.ausgangsRohrUmstellen(r);
                                //aktuellePumpe.setAusgangsRohr(r);
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
            } else {
                System.out.print("\nSpieler: Welchen Aktion möchtest du durchführen? " +
                        "\n1: Rohr reparieren" +
                        "\n2: Rohr umbinden" +
                        "\n3: Pumpe einmontieren" +
                        "\n>");
                choose = scanner.nextInt();
                switch (choose) {
                    case 1: {
                        installateur.getPosition().reparieren();
                        break;
                    }
                    case 2: {
                        if (installateur.getPosition().getNachbarn().contains(null) == false) {
                            System.out.println("Index, woher ausbinden (Index: <1-" + (installateur.getPosition().getNachbarn().size()) + ">)");
                            for (int i = 0; i < installateur.getPosition().getNachbarn().size(); i++) {
                                System.out.println((i + 1) + ": " + installateur.getPosition().getNachbarn().get(i).getName());
                            }
                            System.out.print(">");
                            choose = scanner.nextInt();
                            System.out.println("Index, wohin einbinden (Index: <1-" + (map.getPumpen().size()) + ">) ausser der index: " + choose);
                            for (int i = 0; i < map.getPumpen().size(); i++) {
                                System.out.println((i + 1) + ": " + map.getPumpen().get(i).getName());
                            }
                            System.out.print(">");
                            int chooseWohin = scanner.nextInt();

                            for (Pumpe pumpe : map.getPumpen()) {
                                if (installateur.getPosition().getNachbarn().get(choose - 1) == pumpe)
                                    installateur.umbinden(pumpe, map.getPumpen().get(chooseWohin - 1));
                            }
                        } else {
                            System.out.println("Index, wohin einbinden (Index: <1-" + (map.getPumpen().size()) + ">) ausser der index: " + choose);
                            for (int i = 0; i < map.getPumpen().size(); i++) {
                                System.out.println((i + 1) + ": " + map.getPumpen().get(i).getName());
                            }
                            System.out.print(">");
                            int chooseWohin = scanner.nextInt();

                            for (Pumpe pumpe : map.getPumpen()) {
                                if (installateur.getPosition().getNachbarn().get(choose - 1) == pumpe)
                                    installateur.umbinden(null, map.getPumpen().get(chooseWohin - 1));
                            }
                        }
                        break;
                    }
                    case 3: {
                        System.out.println("In die Mitte des Rohres? (1 = yes/0 = no)");
                        System.out.print(">");
                        choose = scanner.nextInt();
                        installateur.pumpeEinmontieren(choose == 1);
                        break;
                    }
                }
            }

            // saboteur
            System.out.println("Team Saboteur: Mit welchen Spieler möchtest du spielen? (Index: <1-" + spielerZahlProTeam + ">)");
            for (int i = 0; i < spielerZahlProTeam; i++) {
                System.out.println((i + 1) + ": " + saboteurTeam.get(i).getName());
            }
            System.out.print(">");
            choose = scanner.nextInt();

            Saboteur saboteur = saboteurTeam.get(choose - 1);

            //saboteur step
            System.out.println("Wohin möchtest du gehen? (Index: <1-" + saboteur.getPosition().getNachbarn().size() + ">, 0: stehen bleiben)");
            for (int i = 0; i < saboteur.getPosition().getNachbarn().size(); i++) {
                System.out.println((i + 1) + ": " + saboteur.getPosition().getNachbarn().get(i).getName());
            }
            System.out.print(">");
            choose = scanner.nextInt();

            if (choose != 0) {
                saboteur.step(saboteur.getPosition().getNachbarn().get(choose - 1));
            }
            if (saboteur.getPosition() instanceof Pumpe) {
                System.out.print("\nSpieler: Welchen Aktion möchtest du durchführen? " +
                        "\n1: Eingangsrohr Umstellen, " +
                        "\n2: Ausgangsrohr Umstellen" +
                        "\n3: Pumpe aktivieren" +
                        "\n4: Pumpe deaktivieren" +
                        "\n>");
                choose = scanner.nextInt();
                Pumpe aktuellePumpe = null;

                aktuellePumpe = getAktuellePosition(saboteur, aktuellePumpe);


                switch (choose) {
                    case 1: {
                        System.out.println("Rohr auswählen: ");
                        for (int i = 0; i < saboteur.getPosition().getNachbarn().size(); i++) {
                            System.out.println((i + 1) + ": " + saboteur.getPosition().getNachbarn().get(i).getName());
                        }
                        System.out.print(">");
                        choose = scanner.nextInt();
                        for (Rohr r : map.getRohre()) {
                            if (r == aktuellePumpe.getNachbarn().get(choose - 1)) {
                                saboteur.eingangsRohrUmstellen(r);
                                //aktuellePumpe.setEingangsRohr(r);
                            }
                        }
                        break;
                    }
                    case 2: {
                        System.out.println("Rohr auswählen: ");
                        for (int i = 0; i < saboteur.getPosition().getNachbarn().size(); i++) {
                            System.out.println((i + 1) + ": " + saboteur.getPosition().getNachbarn().get(i).getName());
                        }
                        System.out.print(">");
                        choose = scanner.nextInt();
                        for (Rohr r : map.getRohre()) {
                            if (r == aktuellePumpe.getNachbarn().get(choose - 1)) {
                                saboteur.ausgangsRohrUmstellen(r);
                                //aktuellePumpe.setAusgangsRohr(r);
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
            } else {
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
                        System.out.println("Index, woher ausbinden (Index: <1-" + (saboteur.getPosition().getNachbarn().size()) + ">)");
                        for (int i = 0; i < saboteur.getPosition().getNachbarn().size(); i++) {
                            System.out.println((i + 1) + ": " + saboteur.getPosition().getNachbarn().get(i).getName());
                        }
                        System.out.print(">");
                        choose = scanner.nextInt();
                        System.out.println("Index, wohin einbinden (Index: <1-" + (map.getPumpen().size()) + ">) ausser der index: " + choose);
                        for (int i = 0; i < map.getPumpen().size(); i++) {
                            System.out.println((i + 1) + ": " + map.getPumpen().get(i).getName());
                        }
                        System.out.print(">");
                        int chooseWohin = scanner.nextInt();

                        /*for (Pumpe pumpe : map.getPumpen()) {
                            if (saboteur.getPosition().getNachbarn().get(choose-1) == pumpe)
                                saboteur.umbinden(pumpe, map.getPumpen().get(chooseWohin-1));
                        }*//*
                        Pumpe pumpe = map.findPumpe(saboteur.getPosition().getNachbarn().get(choose - 1));
                        if (pumpe != null) {
                            saboteur.umbinden(pumpe, map.getPumpen().get(chooseWohin - 1));
                        }
                        break;
                    }
                }
            }


            // ende der runde
            tick();
            System.out.println("\033[1;32m" + installateurTeamName + ": " + Kontroller.getKontroller().getInstallateurPunkte());
            System.out.println("\033[1;31m" + saboteurTeamName + ": " + Kontroller.getKontroller().getSaboteurPunkte() + "\033[0m");
        }

        endGame();
    }*/


    public void endGame() {
        leaderboard.addRekord(installateurTeamName, installateurPunkte);
        leaderboard.addRekord(saboteurTeamName, saboteurPunkte);
        Logger.info("Spiel beendet.");
        /*if (installateurPunkte > saboteurPunkte) {
            System.out.println("\033[1;32mHurray! Team " + installateurTeamName + " hat gewonnen.\033[0m");
        } else if (installateurPunkte < saboteurPunkte) {
            System.out.println("\033[1;32mHurray! Team " + saboteurTeamName + " hat gewonnen.\033[0m");
        } else {
            System.out.println("\033[1;33mDas Spiel endet unentschieden.\033[0m");
        }
        leaderboard.listAll();*/
    }


    private Pumpe getAktuellePosition(Spieler spieler, Pumpe aktuellePumpe) {
        if (spieler.getPosition() instanceof Zisterne) {
            /*for (Zisterne zisterne : map.getZisternen()) {
                if (zisterne== spieler.getPosition()){
                    aktuellePumpe =zisterne;
                }
            }*/
            Pumpe p = map.findZisterne(spieler.getPosition());
            if (p != null) {
                aktuellePumpe = p;
            }
        } else if (spieler.getPosition() instanceof Wasserquelle) {
            /*for (Wasserquelle wasserquelle : map.getWasserquellen()) {
                if (wasserquelle== spieler.getPosition()){
                    aktuellePumpe =wasserquelle;
                }
            }*/
            Pumpe p = map.findWasserquelle(spieler.getPosition());
            if (p != null) {
                aktuellePumpe = p;
            }
        } else {
            /*for (Pumpe pumpe : map.getPumpen()) {
                if (pumpe== spieler.getPosition()){
                    aktuellePumpe =pumpe;
                }
            }*/
            Pumpe p = map.findPumpe(spieler.getPosition());
            if (p != null) {
                aktuellePumpe = p;
            }
        }
        return aktuellePumpe;
    }

    //=======================================================================================
    //=======================================================================================


    public int getActionCount() {
        return actionCount;
    }

    public void setActionCount(int actionCount) {
        if (actionCount == maxRunde) Logger.info("Game over");
        this.actionCount = actionCount;
        GuiMap.getGuiMap().refreshRoundCounter();
    }

    public GameMap getMap() {
        return map;
    }

    public void setMap(GameMap map) {
        this.map = map;
    }

    public Leaderboard getLeaderboard() {
        return leaderboard;
    }

    public void setLeaderboard(Leaderboard leaderboard) {
        this.leaderboard = leaderboard;
    }

    public int getMaxRunde() {
        return maxRunde;
    }

    public void setMaxRunde(int maxRunde) {
        this.maxRunde = maxRunde;
    }

    public String getInstallateurTeamName() {
        return installateurTeamName;
    }

    public void setInstallateurTeamName(String installateurTeamName) {
        this.installateurTeamName = installateurTeamName;
    }

    public String getSaboteurTeamName() {
        return saboteurTeamName;
    }

    public void setSaboteurTeamName(String saboteurTeamName) {
        this.saboteurTeamName = saboteurTeamName;
    }

    public List<Installateur> getInstallateurTeam() {
        return installateurTeam;
    }

    public void setInstallateurTeam(List<Installateur> installateurTeam) {
        this.installateurTeam = installateurTeam;
    }

    public List<Saboteur> getSaboteurTeam() {
        return saboteurTeam;
    }

    public void setSaboteurTeam(List<Saboteur> saboteurTeam) {
        this.saboteurTeam = saboteurTeam;
    }

    public int getInstallateurPunkte() {
        return installateurPunkte;
    }

    public void setInstallateurPunkte(int installateurPunkte) {
        this.installateurPunkte = installateurPunkte;
    }

    public int getSaboteurPunkte() {
        return saboteurPunkte;
    }

    public void setSaboteurPunkte(int saboteurPunkte) {
        this.saboteurPunkte = saboteurPunkte;
    }

    public void setSelectedPlayer(Spieler s) {
        this.selectedPlayer = s;
    }

    public Spieler getSelectedPlayer() {
        return selectedPlayer;
    }

    public Spieler getLastSelectedPlayer() {
        return lastSelectedPlayer;
    }

    public void setLastSelectedPlayer(Spieler lastSelectedPlayer) {
        this.lastSelectedPlayer = lastSelectedPlayer;
    }
}

