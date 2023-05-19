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

    public void onRohrClick(MouseEvent e) {

        new LineClickAction().handle(e);
    }

    public void endOfAction() {
        eingangsrohrUmstellung = false;
        step = true;
        rohrUmbinden = false;
        ausgangsrohrUmstellung = false;

        eingangsrohrUmstellungPumpe = null;
        ausgangsrohrUmstellungPumpe = null;
        pumpeWoher = null;

        rohrUmbindenZaehler = 0;

        Kontroller.getKontroller().setActionCount(Kontroller.getKontroller().getActionCount() + 1);
        if(Kontroller.getKontroller().getActionCount() % 2 == 0){
            Kontroller.getKontroller().tick();
        }
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

                for (Netzelement n : pos.getNachbarn()) {
                    if (clickedRohr == n) {

                        break;
                    }
                }
                if(sp.step(clickedRohr)) {
                    step = false;
                }

            }
        }
    }

    public void onPumpeAktivierenClick(ActionEvent e) {
        if (step) {
            step = false;
        }
        Pumpe p = Kontroller.getKontroller().getMap().findPumpe(Kontroller.getKontroller().getSelectedPlayer().getPosition());
        p.setIstAktiv(true);
        endOfAction();
    }

    public void onPumpeDeaktivierenClick(ActionEvent e) {
        if (step) {
            step = false;
        }

        Pumpe p = Kontroller.getKontroller().getMap().findPumpe(Kontroller.getKontroller().getSelectedPlayer().getPosition());
        p.setIstAktiv(false);
        endOfAction();
    }

    public void onEingangsrohrUmstellenClick(ActionEvent e) {
        if (step) {
            step = false;
        }

        List<Pumpe> allePumpen = new ArrayList<>();
        allePumpen.addAll(Kontroller.getKontroller().getMap().getPumpen());
        allePumpen.addAll(Kontroller.getKontroller().getMap().getZisternen());
        allePumpen.addAll(Kontroller.getKontroller().getMap().getWasserquellen());
        Pumpe p = null;
        for (Pumpe pumpe : allePumpen) {
            if (pumpe == Kontroller.getKontroller().getSelectedPlayer().getPosition()) {
                p = pumpe;
            }
        }

        eingangsrohrUmstellungPumpe = p;
        eingangsrohrUmstellung = true;
        endOfAction();
    }

    /*public void onStehenBleibenClick(ActionEvent e) {
        step = false;
    }*/

    public void onAusgangsRohrUmstellenClick(ActionEvent e) {
        if (step) {
            step = false;
        }

        List<Pumpe> allePumpen = new ArrayList<>();
        allePumpen.addAll(Kontroller.getKontroller().getMap().getPumpen());
        allePumpen.addAll(Kontroller.getKontroller().getMap().getZisternen());
        allePumpen.addAll(Kontroller.getKontroller().getMap().getWasserquellen());
        Pumpe p = null;
        for (Pumpe pumpe : allePumpen) {
            if (pumpe == Kontroller.getKontroller().getSelectedPlayer().getPosition()) {
                p = pumpe;
            }
        }

        ausgangsrohrUmstellungPumpe = p;
        ausgangsrohrUmstellung = true;
        endOfAction();
    }

    public void onInstallateurClick(ActionEvent e) {
        if (step) {
            step = false;
        }

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
        if (step) {
            step = false;
        }

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
        if (step) {
            step = false;
        }

        Kontroller.getKontroller().getSelectedPlayer().getPosition().reparieren();

        endOfAction();
    }

    public void onRohrUmbindenClick(ActionEvent e) {
        if (step) {
            step = false;
        }
        rohrUmbinden = true;
        endOfAction();
    }

    public void onLoechernClick(ActionEvent e) {
        if (step) {
            step = false;
        }

        Kontroller.getKontroller().getSelectedPlayer().getPosition().kaputtMachen();

        endOfAction();
    }

    public void onPumpeAufnehmen(ActionEvent e) {
        if (step) {
            step = false;
        }

        if (Kontroller.getKontroller().getSelectedPlayer() instanceof Installateur) {
            Installateur inst = (Installateur) Kontroller.getKontroller().getSelectedPlayer();
            inst.pumpeAufnehmen();
            endOfAction();
        }
    }

}
