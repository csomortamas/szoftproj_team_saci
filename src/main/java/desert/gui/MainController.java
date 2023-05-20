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
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Line;

import java.io.IOException;
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


    private Pumpe ausgangsrohrUmstellungPumpe = null;
    private Pumpe eingangsrohrUmstellungPumpe = null;
    private Pumpe pumpeWoher = null;

    private int rohrUmbindenZaehler = 0;

    public void onSpielStartenClick(ActionEvent e) {
        TextField instTName = (TextField) GuiMap.getGuiMap().getScene().lookup("#txtInstTName");
        TextField sabTName = (TextField) GuiMap.getGuiMap().getScene().lookup("#txtSabTName");
        Kontroller.getKontroller().setInstallateurTeamName(instTName.getText());
        Kontroller.getKontroller().setSaboteurTeamName(sabTName.getText());

        GuiMap.getGuiMap().setGroup(new Group());

        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("main-view.fxml"));
        try {
            GuiMap.getGuiMap().getGroup().getChildren().add(fxmlLoader.load());
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        GuiMap.getGuiMap().getScene().setRoot(GuiMap.getGuiMap().getGroup());
        GuiMap.getGuiMap().sceneSetup();

        Kontroller.getKontroller().tick();
        Kontroller.getKontroller().setInstallateurPunkte(0);
        GuiMap.getGuiMap().refreshPoints();
        GuiMap.getGuiMap().refreshRoehre();
        GuiMap.getGuiMap().refreshPlayerButtons();
        GuiMap.getGuiMap().refreshControlPanes();
    }

    public void onPumpeClick(ActionEvent e) {
        Button buttonQuelle = (Button) e.getSource();
        Pumpe pumpe = null;

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

    public void endOfAction() {
        eingangsrohrUmstellung = false;
        step = true;
        rohrUmbinden = false;
        ausgangsrohrUmstellung = false;
        playerSelected = false;

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
                eingangsrohrUmstellungPumpe = null;
                eingangsrohrUmstellung = false;
            } else if (ausgangsrohrUmstellung) {
                ausgangsrohrUmstellungPumpe.setAusgangsRohr(clickedRohr);
                ausgangsrohrUmstellungPumpe = null;
                ausgangsrohrUmstellung = false;
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

    public void onPumpeAktivierenClick(ActionEvent e) {
        if (step) {
            step = false;
        }
        if (!playerSelected) {
            playerSelected = true;
        }

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
        GuiMap.getGuiMap().refreshRoehre();

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
