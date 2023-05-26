package desert.gui;

import desert.control.Kontroller;
import desert.network.Netzelement;
import desert.network.Pumpe;
import desert.network.Rohr;
import desert.player.Installateur;
import desert.player.Saboteur;
import desert.player.Spieler;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Line;

import java.util.ArrayList;
import java.util.List;

public class MainController {
    private static MainController mainController = null;

    public static MainController getMainController() {
        if (mainController == null) {
            mainController = new MainController();
        }
        return mainController;
    }
    private boolean eingangsrohrUmstellung = false;
    private boolean ausgangsrohrUmstellung = false;
    private boolean step = true;
    private boolean rohrUmbinden = false;
    private boolean playerSelected = false;
    private boolean rohrMitFreiemEndeUmbinden = false;
    private boolean actionStarted = false;
    private Pumpe ausgangsrohrUmstellungPumpe = null;
    private Pumpe eingangsrohrUmstellungPumpe = null;
    private Pumpe pumpeWoher = null;

    private int rohrUmbindenZaehler = 0;

    public void onSpielStartenClick(ActionEvent e) {
        int i = 0;
        Kontroller.getKontroller().setSpielLauft(true);
        while (Kontroller.getKontroller().getInstallateurTeam().size() < Kontroller.getKontroller().getSpielerAnzahlProTeam()) {
            Kontroller.getKontroller().getInstallateurTeam().add(new Installateur(Kontroller.getKontroller().getMap().getWasserquellen().get(i % 3)));
            Kontroller.getKontroller().getSaboteurTeam().add(new Saboteur(Kontroller.getKontroller().getMap().getZisternen().get(i % 3)));
            i++;
        }

        GuiMap.getGuiMap().loadGame();
        GuiMap.getGuiMap().sceneSetup();

        Kontroller.getKontroller().tick();
        Kontroller.getKontroller().setInstallateurPunkte(0);
        GuiMap.getGuiMap().refreshPoints();
        GuiMap.getGuiMap().refreshRohrColor();
        GuiMap.getGuiMap().refreshPlayerButtons();
        GuiMap.getGuiMap().refreshControlPanes();
    }


    public void endOfAction() {
        if (Math.floor((Kontroller.getKontroller().getActionCount() + 1) / 2) == Kontroller.getKontroller().getMaxRunde()) {
            endOfGame();
            return;
        }

        eingangsrohrUmstellung = false;
        step = true;
        rohrUmbinden = false;
        ausgangsrohrUmstellung = false;
        playerSelected = false;
        actionStarted = false;

        eingangsrohrUmstellungPumpe = null;
        ausgangsrohrUmstellungPumpe = null;
        pumpeWoher = null;

        rohrUmbindenZaehler = 0;

        Spieler temp = Kontroller.getKontroller().getSelectedPlayer();
        Kontroller.getKontroller().setSelectedPlayer(Kontroller.getKontroller().getLastSelectedPlayer());
        Kontroller.getKontroller().setLastSelectedPlayer(temp);

        Kontroller.getKontroller().setActionCount(Kontroller.getKontroller().getActionCount() + 1);
        if (Kontroller.getKontroller().getActionCount() % 2 == 0) {
            Kontroller.getKontroller().tick();
        }

        GuiMap.getGuiMap().refreshPlayerButtons();
        GuiMap.getGuiMap().refreshControlPanes();
        GuiMap.getGuiMap().refreshRohrColor();
    }

    public void endOfGame() {
        Kontroller.getKontroller().tick();
        GuiMap.getGuiMap().refreshPoints();
        GuiMap.getGuiMap().endOfGameDialog();
    }

    public void onSettingsClick(ActionEvent e) {
        GuiMap.getGuiMap().openSettings();
    }

    public void onSaveSettingsClick(ActionEvent e) {
        TextField rundeAnzahl = (TextField) GuiMap.getGuiMap().getSettingsScene().lookup("#rundeAnzahl");
        if (Integer.parseInt(rundeAnzahl.getText()) > Math.floor((Kontroller.getKontroller().getActionCount() / 2) + 1)) {
            Kontroller.getKontroller().setMaxRunde(Integer.parseInt(rundeAnzahl.getText()));
        }

        TextField spielerAnzahl = (TextField) GuiMap.getGuiMap().getSettingsScene().lookup("#spielerAnzahl");
        if (Integer.parseInt(spielerAnzahl.getText()) >= 1 && !Kontroller.getKontroller().isSpielLauft()) {
            Kontroller.getKontroller().setSpielerAnzahlProTeam(Integer.parseInt(spielerAnzahl.getText()));
        }

        TextField neueP = (TextField) GuiMap.getGuiMap().getSettingsScene().lookup("#neueP");
        if (Integer.parseInt(neueP.getText()) >= 1) {
            Kontroller.getKontroller().setNeuePumpeChance(Integer.parseInt(neueP.getText()) + 1);
        }

        TextField neueR = (TextField) GuiMap.getGuiMap().getSettingsScene().lookup("#neueR");
        if (Integer.parseInt(neueR.getText()) >= 1) {
            Kontroller.getKontroller().setNeueRohrChance(Integer.parseInt(neueR.getText()) + 1);
        }

        TextField pumpeK = (TextField) GuiMap.getGuiMap().getSettingsScene().lookup("#pumpeK");
        if (Integer.parseInt(pumpeK.getText()) >= 1) {
            Kontroller.getKontroller().setPumpeKaputtGehtChance(Integer.parseInt(pumpeK.getText()) + 1);
        }

        GuiMap.getGuiMap().getSettingsStage().close();
    }

    public void onPumpeClick(ActionEvent e) {
        Button buttonQuelle = (Button) e.getSource();
        Pumpe pumpe;

        pumpe = Kontroller.getKontroller().getMap().findInAllPumpeWithButton(buttonQuelle);
        if (rohrUmbinden) {
            if (rohrUmbindenZaehler == 0) {
                pumpeWoher = pumpe;
                rohrUmbindenZaehler++;

            } else if (rohrUmbindenZaehler == 1) {
                Pumpe pumpeWohin = pumpe;
                Kontroller.getKontroller().getSelectedPlayer().umbinden(pumpeWoher, pumpeWohin);

                endOfAction();
            }
        } else if (rohrMitFreiemEndeUmbinden) {
            Kontroller.getKontroller().getSelectedPlayer().umbinden(null, pumpe);
            Rohr r = Kontroller.getKontroller().getMap().findRohr(Kontroller.getKontroller().getSelectedPlayer().getPosition());
            r.getLine().setOnMouseClicked(getLineClickAction());
            rohrMitFreiemEndeUmbinden = false;
            endOfAction();
        } else if (step) {
            List<Pumpe> alleNetzelemente = new ArrayList<>();
            alleNetzelemente.addAll(Kontroller.getKontroller().getMap().getWasserquellen());
            alleNetzelemente.addAll(Kontroller.getKontroller().getMap().getZisternen());
            alleNetzelemente.addAll(Kontroller.getKontroller().getMap().getPumpen());

            Button clickedButton = (Button) e.getSource();

            Spieler sp = Kontroller.getKontroller().getSelectedPlayer();

            for (Pumpe netzelement : alleNetzelemente) {
                if (netzelement.getButton() == clickedButton) {

                    if (sp.step(netzelement)) step = false;
                    break;
                }
            }
            GuiMap.getGuiMap().refreshControlPanes();
        }
    }

    public void onRohrClick(MouseEvent e) {
        if (!playerSelected) {
            playerSelected = true;
        }

        new LineClickAction().handle(e);
    }

    public class LineClickAction implements EventHandler<MouseEvent> {
        @Override
        public void handle(MouseEvent e) {
            Line line = (Line) e.getSource();

            Rohr clickedRohr = null;
            for (Rohr r : Kontroller.getKontroller().getMap().getRohre()) {
                if (r.getLine() == line) {
                    clickedRohr = r;
                    break;
                }
            }
            if (eingangsrohrUmstellung) {
                eingangsrohrUmstellungPumpe.setEingangsRohr(clickedRohr);
                endOfAction();
            } else if (ausgangsrohrUmstellung) {
                ausgangsrohrUmstellungPumpe.setAusgangsRohr(clickedRohr);
                endOfAction();
            } else if (step) {
                Spieler sp = Kontroller.getKontroller().getSelectedPlayer();
                Netzelement pos = sp.getPosition();

                if (sp.step(clickedRohr)) {
                    step = false;
                }
                GuiMap.getGuiMap().refreshControlPanes();

            }
        }
    }

    public LineClickAction getLineClickAction() {
        return new LineClickAction();
    }

    public void onPassClick(ActionEvent e) {
        if (!actionStarted) {
            endOfAction();
        }
    }

    public void onPumpeAktivierenClick(ActionEvent e) {
        if (step) {
            step = false;
        }
        if (!playerSelected) {
            playerSelected = true;
        }
        actionStarted = true;

        Pumpe p = Kontroller.getKontroller().getMap().findInAllePumpen(Kontroller.getKontroller().getSelectedPlayer().getPosition());
        p.setIstAktiv(true);
        endOfAction();
    }

    public void onPumpeDeaktivierenClick(ActionEvent e) {
        if (step) {
            step = false;
        }
        if (!playerSelected) {
            playerSelected = true;
        }
        actionStarted = true;

        Pumpe p = Kontroller.getKontroller().getMap().findInAllePumpen(Kontroller.getKontroller().getSelectedPlayer().getPosition());
        p.setIstAktiv(false);
        endOfAction();
    }

    public void onEingangsrohrUmstellenClick(ActionEvent e) {
        if (step) {
            step = false;
        }
        if (!playerSelected) {
            playerSelected = true;
        }
        actionStarted = true;

        eingangsrohrUmstellungPumpe = Kontroller.getKontroller().getMap().findInAllePumpen(Kontroller.getKontroller().getSelectedPlayer().getPosition());
        eingangsrohrUmstellung = true;
    }

    public void onAusgangsRohrUmstellenClick(ActionEvent e) {
        if (step) {
            step = false;
        }
        if (!playerSelected) {
            playerSelected = true;
        }
        actionStarted = true;

        ausgangsrohrUmstellungPumpe = Kontroller.getKontroller().getMap().findInAllePumpen(Kontroller.getKontroller().getSelectedPlayer().getPosition());
        ausgangsrohrUmstellung = true;

    }

    public void onInstallateurClick(ActionEvent e) {
        if (!playerSelected) {
            Button b = (Button) e.getSource();
            Kontroller.getKontroller().getSelectedPlayer().getButton().setEffect(null);
            for (Installateur i : Kontroller.getKontroller().getInstallateurTeam()) {
                if (i.getButton() == b) {
                    Kontroller.getKontroller().setSelectedPlayer(i);
                    break;
                }
            }
            GuiMap.getGuiMap().refreshControlPanes();
            GuiMap.getGuiMap().refreshPlayerButtons();
            step = true;
            playerSelected = true;
        }
    }

    public void onSaboteurClick(ActionEvent e) {
        if (!playerSelected) {
            Button b = (Button) e.getSource();
            Kontroller.getKontroller().getSelectedPlayer().getButton().setEffect(null);
            for (Saboteur s : Kontroller.getKontroller().getSaboteurTeam()) {
                if (s.getButton() == b) {
                    Kontroller.getKontroller().setSelectedPlayer(s);
                    break;
                }
            }
            GuiMap.getGuiMap().refreshControlPanes();
            GuiMap.getGuiMap().refreshPlayerButtons();

            step = true;
            playerSelected = true;
        }
    }

    public void onReparierenClick(ActionEvent e) {
        if (step) {
            step = false;
        }
        if (!playerSelected) {
            playerSelected = true;
        }
        actionStarted = true;

        Kontroller.getKontroller().getSelectedPlayer().getPosition().reparieren();
        GuiMap.getGuiMap().refreshPumpen();
        endOfAction();
    }

    public void onRohrUmbindenClick(ActionEvent e) {
        if (step) {
            step = false;
        }
        if (!playerSelected) {
            playerSelected = true;
        }
        actionStarted = true;

        Button b = (Button) e.getSource();
        Rohr rohr = null;
        for (Rohr r : Kontroller.getKontroller().getMap().getRohre()) {
            if (r.getLine() == ((Rohr) Kontroller.getKontroller().getSelectedPlayer().getPosition()).getLine()) {
                rohr = r;
                break;
            }
        }
        if (rohr.getNachbarn().size() == 2) rohrUmbinden = true;
        else rohrMitFreiemEndeUmbinden = true;

    }

    public void onLoechernClick(ActionEvent e) {
        if (step) {
            step = false;
        }
        if (!playerSelected) {
            playerSelected = true;
        }
        actionStarted = true;

        Kontroller.getKontroller().getSelectedPlayer().getPosition().kaputtMachen();

        endOfAction();
    }

    public void onPumpeAufnehmen(ActionEvent e) {
        if (step) {
            step = false;
        }
        if (!playerSelected) {
            playerSelected = true;
        }
        actionStarted = true;

        if (Kontroller.getKontroller().getSelectedPlayer() instanceof Installateur) {
            Installateur inst = (Installateur) Kontroller.getKontroller().getSelectedPlayer();
            inst.pumpeAufnehmen();

        }
        endOfAction();
    }

    public void onEinmontierenMiddleClick(ActionEvent e) {
        if (step) {
            step = false;
        }
        if (!playerSelected) {
            playerSelected = true;
        }
        actionStarted = true;

        if (Kontroller.getKontroller().getSelectedPlayer() instanceof Installateur) {
            Installateur inst = (Installateur) Kontroller.getKontroller().getSelectedPlayer();
            inst.pumpeEinmontieren(true);
            Pumpe p = Kontroller.getKontroller().getMap().findPumpe(inst.getPosition());
            p.getButton().setOnAction(this::onPumpeClick);
            Rohr r = Kontroller.getKontroller().getMap().findRohr(p.getNachbarn().get(0));
            r.getLine().setOnMouseClicked(getLineClickAction());
            r = Kontroller.getKontroller().getMap().findRohr(p.getNachbarn().get(1));
            r.getLine().setOnMouseClicked(getLineClickAction());
        }
        GuiMap.getGuiMap().refreshAlleRohre();
        GuiMap.getGuiMap().refreshRohrColor();

        //Spieler selectedPlayer = Kontroller.getKontroller().getSelectedPlayer();
        //if(selectedPlayer.getPosition() instanceof Pumpe) {
        //    selectedPlayer.getButton().setLayoutX((Kontroller.getKontroller().getMap().findPumpe(selectedPlayer.getPosition())).getPosX());
        //    selectedPlayer.getButton().setLayoutY((Kontroller.getKontroller().getMap().findPumpe(selectedPlayer.getPosition())).getPosY());
        //}
        GuiMap.getGuiMap().refreshSpieler();
        //GuiMap.getGuiMap().refreshRemovedRohr(Kontroller.getKontroller().getMap().findRohr(Kontroller.getKontroller().getSelectedPlayer().getPosition()));

        endOfAction();
    }
}
