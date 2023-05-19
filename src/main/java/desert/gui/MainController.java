package desert.gui;

import desert.control.Kontroller;
import desert.network.*;
import desert.player.Installateur;
import desert.player.Saboteur;
import desert.player.Spieler;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;

import java.text.AttributedCharacterIterator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.lang.Math.abs;

public class MainController {

    boolean eingangsrohrUmstellung = false;
    Pumpe eingangsrohrUmstellungPumpe = null;
    boolean ausgangsrohrUmstellung = false;
    Pumpe ausgangsrohrUmstellungPumpe = null;
    boolean step = true;
    boolean rohrUmbinden = false;
    Pumpe pumpeWoher = null;
    int rohrUmbindenZaehler = 0;

    @FXML
    public void onQuelleClick(ActionEvent e) {
        Button buttonQuelle = (Button) e.getSource();
        Wasserquelle quelle = new Wasserquelle(buttonQuelle.getLayoutX(), buttonQuelle.getLayoutY());

        for (Wasserquelle w : Kontroller.getKontroller().getMap().getWasserquellen()) {
            System.out.println(w.getPosX());
            if (w.getButton() == buttonQuelle) {
                break;
            }
        }
    }

    public void onPumpeClick(ActionEvent e) {
        Button buttonQuelle = (Button) e.getSource();
        Pumpe pumpe = null;
        for (Pumpe p : Kontroller.getKontroller().getMap().getPumpen()) {
            if (p.getButton() == buttonQuelle) {
                pumpe = p;
                break;
            }
        }
        if (pumpe == null) {
            for (Pumpe p : Kontroller.getKontroller().getMap().getZisternen()) {
                if (p.getButton() == buttonQuelle) {
                    pumpe = p;
                    break;
                }
            }
        }
        if (pumpe == null) {
            for (Pumpe p : Kontroller.getKontroller().getMap().getWasserquellen()) {
                if (p.getButton() == buttonQuelle) {
                    pumpe = p;
                    break;
                }
            }
        }
        if (rohrUmbinden) {
            if (rohrUmbindenZaehler == 0) {
                pumpeWoher = pumpe;
                rohrUmbindenZaehler++;

            } else if (rohrUmbindenZaehler == 1) {
                Pumpe pumpeWohin = pumpe;
                Kontroller.getKontroller().getSelectedPlayer().umbinden(pumpeWoher, pumpeWohin);

                Rohr rohr = Kontroller.getKontroller().getMap().findRohr(Kontroller.getKontroller().getSelectedPlayer().getPosition());

                Pumpe nachbarnPumpe1 = Kontroller.getKontroller().getMap().findPumpe(rohr.getNachbarn().get(0));
                if (nachbarnPumpe1 == null) {
                    nachbarnPumpe1 = Kontroller.getKontroller().getMap().findZisterne(rohr.getNachbarn().get(0));
                }
                if (nachbarnPumpe1 == null) {
                    nachbarnPumpe1 = Kontroller.getKontroller().getMap().findWasserquelle(rohr.getNachbarn().get(0));
                }
                Pumpe nachbarnPumpe2 = Kontroller.getKontroller().getMap().findPumpe(rohr.getNachbarn().get(1));
                if (nachbarnPumpe2 == null) {
                    nachbarnPumpe2 = Kontroller.getKontroller().getMap().findZisterne(rohr.getNachbarn().get(1));
                }
                if (nachbarnPumpe2 == null) {
                    nachbarnPumpe2 = Kontroller.getKontroller().getMap().findWasserquelle(rohr.getNachbarn().get(1));
                }

                rohr.getLine().setStartX(nachbarnPumpe1.getButton().getLayoutX() + (nachbarnPumpe1.getButton().getWidth() / 2));
                rohr.getLine().setStartY(nachbarnPumpe1.getButton().getLayoutY() + (nachbarnPumpe1.getButton().getHeight() / 2));


                rohr.getLine().setEndX(nachbarnPumpe2.getButton().getLayoutX() + (nachbarnPumpe2.getButton().getWidth() / 2));
                rohr.getLine().setEndY(nachbarnPumpe2.getButton().getLayoutY() + (nachbarnPumpe2.getButton().getHeight() / 2));
                rohr.getLine().toBack();

                Kontroller.getKontroller().getSelectedPlayer().getButton().setLayoutX(calculateSpielerPos(rohr.getLine().getStartX(), rohr.getLine().getEndX()));
                Kontroller.getKontroller().getSelectedPlayer().getButton().setLayoutY(calculateSpielerPos(rohr.getLine().getEndY(),rohr.getLine().getStartY()));


                //GuiMap.getGuiMap().refresh();
                rohrUmbindenZaehler = 0;
                rohrUmbinden = false;
                pumpeWoher = null;
                pumpeWohin = null;
                endOfAction();
            }
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
        }
    }

    public void onZisterneClick(ActionEvent e) {
        Button buttonQuelle = (Button) e.getSource();
        Zisterne zist = new Zisterne(buttonQuelle.getLayoutX(), buttonQuelle.getLayoutY());

        for (Zisterne z : Kontroller.getKontroller().getMap().getZisternen()) {
            System.out.println(z.getPosX());
            if (z.getButton() == buttonQuelle) {

                break;
            }
        }
    }

    public void onRohrClick(MouseEvent e) {

        new LineClickAction().handle(e);
    }

    public void endOfAction() {
        step = true;
        Kontroller.getKontroller().setActionCount(Kontroller.getKontroller().getActionCount() + 1);
        Text l = (Text) GuiMap.getGuiMap().getScene().lookup("#txtAktuelleRunde");
        l.setText(String.valueOf(Math.floor((Kontroller.getKontroller().getActionCount() / 2) + 1)));
    }


    public class LineClickAction implements EventHandler<MouseEvent> {
        @Override
        public void handle(MouseEvent e) {
            Line line = (Line) e.getSource();
            /*Rohr rohr = new Rohr();

            for (Rohr r : Kontroller.getKontroller().getMap().getRohre()) {
                if (r.getLine() == line) {
                    rohr = r;
                    System.out.println("line");
                    break;
                }
            }
            rohr.setIstKaputt(true);
            GuiMap.getGuiMap().refresh();
          */
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
                boolean nachbar = false;
                for (Netzelement n : pos.getNachbarn()) {
                    if (clickedRohr == n) {
                        nachbar = true;
                        break;
                    }
                }
                if (nachbar) {
                    sp.step(clickedRohr);
                    double startX = line.getStartX();
                    double endX = line.getEndX();
                    double startY = line.getStartY();
                    double endY = line.getEndY();

                    sp.getButton().setLayoutX(calculateSpielerPos(startX, endX));
                    sp.getButton().setLayoutY(calculateSpielerPos(startY, endY));
                    step = false;
                }
            }

        }
    }

    private double calculateSpielerPos(double startCoord, double endCoord) {
        return (abs((endCoord - startCoord)) / 2 + startCoord);
    }

    public void onPumpeAktivierenClick(ActionEvent e) {
       /* if(Kontroller.getKontroller().getAktuelleRunde() %2 ==0) return;

        if(Kontroller.getKontroller().getSelectedPlayer() instanceof Saboteur) return; */
        Pumpe p = Kontroller.getKontroller().getMap().findPumpe(Kontroller.getKontroller().getSelectedPlayer().getPosition());
        p.setIstAktiv(true);
        endOfAction();
    }

    public void onPumpeDeaktivierenClick(ActionEvent e) {
        Pumpe p = Kontroller.getKontroller().getMap().findPumpe(Kontroller.getKontroller().getSelectedPlayer().getPosition());
        p.setIstAktiv(false);
        endOfAction();
    }

    public void onEingangsrohrUmstellenClick(ActionEvent e) {
        Pumpe p = Kontroller.getKontroller().getMap().findPumpe(Kontroller.getKontroller().getSelectedPlayer().getPosition());
        if (p == null) {
            p = Kontroller.getKontroller().getMap().findZisterne(Kontroller.getKontroller().getSelectedPlayer().getPosition());
        }
        if (p == null) {
            p = Kontroller.getKontroller().getMap().findWasserquelle(Kontroller.getKontroller().getSelectedPlayer().getPosition());
        }

        eingangsrohrUmstellungPumpe = p;
        eingangsrohrUmstellung = true;
        endOfAction();
    }

    public void onStehenBleibenClick(ActionEvent e) {
        step = false;
    }

    public void onAusgangsRohrUmstellenClick(ActionEvent e) {
        Pumpe p = Kontroller.getKontroller().getMap().findPumpe(Kontroller.getKontroller().getSelectedPlayer().getPosition());
        if (p == null) {
            p = Kontroller.getKontroller().getMap().findZisterne(Kontroller.getKontroller().getSelectedPlayer().getPosition());
        }
        if (p == null) {
            p = Kontroller.getKontroller().getMap().findWasserquelle(Kontroller.getKontroller().getSelectedPlayer().getPosition());
        }

        ausgangsrohrUmstellungPumpe = p;
        ausgangsrohrUmstellung = true;
        endOfAction();
    }

    public void onInstallateurClick(ActionEvent e) {
        Button b = (Button) e.getSource();
        for (Installateur i : Kontroller.getKontroller().getInstallateurTeam()) {
            if (i.getButton() == b) {
                Kontroller.getKontroller().setSelectedPlayer(i);
                break;
            }
        }
        endOfAction();
    }

    public void onSaboteurClick(ActionEvent e) {
        Button b = (Button) e.getSource();
        for (Saboteur s : Kontroller.getKontroller().getSaboteurTeam()) {
            if (s.getButton() == b) {
                Kontroller.getKontroller().setSelectedPlayer(s);
                break;
            }
        }
        endOfAction();
    }

    public void onReparierenClick(ActionEvent e) {
        Kontroller.getKontroller().getSelectedPlayer().getPosition().reparieren();
        GuiMap.getGuiMap().refresh();
        endOfAction();
    }

    public void onRohrUmbindenClick(ActionEvent e) {
        Rohr r = Kontroller.getKontroller().getMap().findRohr(Kontroller.getKontroller().getSelectedPlayer().getPosition());
        // System.out.println("aktuelle Rohr: "+ r.getName());
        rohrUmbinden = true;
        endOfAction();
    }

    public void onLoechernClick(ActionEvent e) {
        Kontroller.getKontroller().getSelectedPlayer().getPosition().kaputtMachen();
        GuiMap.getGuiMap().refresh();
        endOfAction();
    }

    public void onPumpeAufnehmen(ActionEvent e) {
        if (Kontroller.getKontroller().getSelectedPlayer() instanceof Installateur) {
            Installateur inst = (Installateur) Kontroller.getKontroller().getSelectedPlayer();
            inst.pumpeAufnehmen();
            endOfAction();
        }
    }

}
