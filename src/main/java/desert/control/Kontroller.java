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


public class Kontroller {
    private static final Kontroller kontroller = new Kontroller();

    private Kontroller() {
    }

    public static Kontroller getKontroller() {
        return kontroller;
    }

    private GameMap map = new GameMap();

    private int actionCount = 0;

    private int maxRunde = 3;

    private String installateurTeamName;

    private String saboteurTeamName;

    private final List<Installateur> installateurTeam = new ArrayList<>();

    private final List<Saboteur> saboteurTeam = new ArrayList<>();

    private int installateurPunkte;

    private int neuePumpeChance = 6;
    private int neueRohrChance = 6;
    private int pumpeKaputtGehtChance = 6;

    private int saboteurPunkte;

    private Spieler selectedPlayer;

    private Spieler lastSelectedPlayer;

    private int spielerAnzahlProTeam = 3;
    private boolean spielLauft = false;

    public void binden(Rohr rohr, Netzelement left, Netzelement right) {
        if (left instanceof Pumpe && right instanceof Pumpe) {
            rohr.getNachbarn().add(left);
            rohr.getNachbarn().add(right);

            left.getNachbarn().add(rohr);
            right.getNachbarn().add(rohr);

            Logger.info("Rohr zwischen Pumpe {} und Pumpe {} gebunden", left, right);
        }
    }

    public void pumpeRandomErstellenBeiZisternen() {
        Random rand = new Random();
        int randomNumber = rand.nextInt(neuePumpeChance);
        int randomZisterneIndex = rand.nextInt(map.getZisternen().size());

        if (randomNumber == 1) {
            Pumpe p = new Pumpe(map.getZisternen().get(randomZisterneIndex).getPosX(), map.getZisternen().get(randomZisterneIndex).getPosY());
            map.getZisternen().get(randomZisterneIndex).setPumpeZurVerfuegung(p);
        }
    }

    public void rohrErstellen() {
        Random rand = new Random();
        int randomNumber = rand.nextInt(neueRohrChance);
        int randomZisterneIndex = rand.nextInt(map.getZisternen().size());

        if (randomNumber == 1) {
            Rohr newRohr = new Rohr();
            newRohr.setName("R-G");
            map.getZisternen().get(randomZisterneIndex).getNachbarn().add(newRohr);
            newRohr.getNachbarn().add(map.getZisternen().get(randomZisterneIndex));

            map.getZisternen().get(randomZisterneIndex).getNachbarn().add(newRohr);
            GuiMap.getGuiMap().refreshNewRohre(map.getZisternen().get(randomZisterneIndex), newRohr);
            map.getRohre().add(newRohr);
        }
    }

    public void pumpeKaputtMacht() {
        Random rand = new Random();
        int randomNumber = rand.nextInt(pumpeKaputtGehtChance);
        int randomPumpeIndex = rand.nextInt(map.getPumpen().size());

        if (map.getPumpen().size() > 0 && randomNumber == 1) {
            map.getPumpen().get(randomPumpeIndex).kaputtMachen();
        }
    }

    public void punkteKalkulieren() {
        for (Rohr rohr : map.getRohre()) {
            if (rohr.isAktiv() && rohr.isIstKaputt()) {
                saboteurPunkte++;
            }
        }

        for (Zisterne zisterne : map.getZisternen()) {
            if (zisterne.getEingangsRohr() != null) {
                if (zisterne.getEingangsRohr().isAktiv() && !zisterne.getEingangsRohr().isIstKaputt()) {
                    installateurPunkte++;
                }
            }
        }
    }

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
        pumpeKaputtMacht();
        pumpeRandomErstellenBeiZisternen();
        rohrErstellen();
        GuiMap.getGuiMap().refreshReadyPumps();
        GuiMap.getGuiMap().refreshPoints();
        Logger.info("Tick");
    }

    public void addPumpe(Pumpe pumpe) {
        map.getPumpen().add(pumpe);

        Logger.info("Eine Pumpe wurde hinzugefügt");
    }

    public void addRohr(Rohr rohr) {
        map.getRohre().add(rohr);

        Logger.info("Ein Rohr wurde hinzugefügt");
    }


    public void setupV2(List<Wasserquelle> wasserquellen, List<Zisterne> zisternen, List<Pumpe> pumpen, List<Rohr> rohre, String teamInstName, String teamSabName) {
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

        Logger.info("Setup erfolgreich");
    }

    //============================GETTERS & SETTERS==========================================
    //=======================================================================================


    public boolean isSpielLauft() {
        return spielLauft;
    }

    public void setSpielLauft(boolean spielLauft) {
        this.spielLauft = spielLauft;
    }

    public int getSpielerAnzahlProTeam() {
        return spielerAnzahlProTeam;
    }

    public void setSpielerAnzahlProTeam(int spielerAnzahlProTeam) {
        this.spielerAnzahlProTeam = spielerAnzahlProTeam;
    }

    public int getNeuePumpeChance() {
        return neuePumpeChance;
    }

    public void setNeuePumpeChance(int neuePumpeChance) {
        this.neuePumpeChance = neuePumpeChance;
    }

    public int getNeueRohrChance() {
        return neueRohrChance;
    }

    public void setNeueRohrChance(int neueRohrChance) {
        this.neueRohrChance = neueRohrChance;
    }

    public int getPumpeKaputtGehtChance() {
        return pumpeKaputtGehtChance;
    }

    public void setPumpeKaputtGehtChance(int pumpeKaputtGehtChance) {
        this.pumpeKaputtGehtChance = pumpeKaputtGehtChance;
    }

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


    public List<Saboteur> getSaboteurTeam() {
        return saboteurTeam;
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

