package desert.gui;

import desert.control.Kontroller;
import desert.network.Pumpe;
import desert.network.Rohr;
import desert.network.Wasserquelle;
import desert.network.Zisterne;
import desert.player.Saboteur;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;

import java.text.AttributedCharacterIterator;

import static java.lang.Math.abs;

public class MainController {

    @FXML
    public void onQuelleClick(ActionEvent e){
        Button buttonQuelle = (Button)e.getSource();
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
        Pumpe pumpe =null;
        for (Pumpe p : Kontroller.getKontroller().getMap().getPumpen()){
            if (p.getButton() == buttonQuelle) {
                pumpe = p;
                break;
            }
        }
        if(pumpe ==  null){
            for (Pumpe p : Kontroller.getKontroller().getMap().getZisternen()){
                if (p.getButton() == buttonQuelle) {
                    pumpe = p;
                    break;
                }
            }
        }
        if(pumpe ==  null) {
            for (Pumpe p : Kontroller.getKontroller().getMap().getZisternen()) {
                if (p.getButton() == buttonQuelle) {
                    pumpe = p;
                    break;
                }
            }
        }
        if (rohrUmbinden) {
            if (rohrUmbindenZaehler == 0){
                pumpeWoher = pumpe ;
                rohrUmbindenZaehler++;
                //System.out.println("pumpe Woher: "+pumpeWoher.getName());
            }else if(rohrUmbindenZaehler == 1){
                Pumpe pumpeWohin = pumpe;
                //System.out.println("pumpe Wohin: "+pumpeWohin.getName());
                Kontroller.getKontroller().getSelectedPlayer().umbinden(pumpeWoher,pumpeWohin);

                Rohr rohr = Kontroller.getKontroller().getMap().findRohr(Kontroller.getKontroller().getSelectedPlayer().getPosition());

                //Pumpe nachbarnPumpe1 = Kontroller.getKontroller().getMap().findPumpe(rohr.getNachbarn().get(0));
                rohr.getLine().setStartX(15);
                rohr.getLine().setStartY(11);
                //Pumpe nachbarnPumpe2 = Kontroller.getKontroller().getMap().findPumpe(rohr.getNachbarn().get(1));
                rohr.getLine().setEndX(45);
                rohr.getLine().setEndY(1);
                rohr.getLine().toBack();

                //GuiMap.getGuiMap().refresh();
                rohrUmbindenZaehler =0;
                rohrUmbinden = false;
                pumpeWoher = null;
                pumpeWohin = null;
            }
        }
    }

    public void onZisterneClick(ActionEvent e){
        Button buttonQuelle = (Button)e.getSource();
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
        step = false;
        Kontroller.getKontroller().setActionCount(Kontroller.getKontroller().getActionCount() + 1);
        Text l = (Text) GuiMap.getGuiMap().getScene().lookup("#txtAktuelleRunde");
        l.setText(String.valueOf(Math.floor((Kontroller.getKontroller().getActionCount() / 2) + 1)));
    }


    public class LineClickAction implements EventHandler<MouseEvent> {
        @Override
        public void handle(MouseEvent e){
            Line line = (Line)e.getSource();
            Rohr rohr = new Rohr();

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

                    sp.getButton().setLayoutX(calculateSpielerPos(startX,endX));
                    sp.getButton().setLayoutY(calculateSpielerPos(startY, endY));
                    step = false;
                }
            }

        }
    }
    private double calculateSpielerPos(double startCoord, double endCoord){
        return (abs((endCoord - startCoord))/2 +startCoord);
    }

    public void onPumpeAktivierenClick(ActionEvent e) {
       /* if(Kontroller.getKontroller().getAktuelleRunde() %2 ==0) return;

        if(Kontroller.getKontroller().getSelectedPlayer() instanceof Saboteur) return; */
        Pumpe p = Kontroller.getKontroller().getMap().findPumpe(Kontroller.getKontroller().getSelectedPlayer().getPosition());
        p.setIstAktiv(true);
    }

    public void onPumpeDeaktivierenClick(ActionEvent e) {
        Pumpe p = Kontroller.getKontroller().getMap().findPumpe(Kontroller.getKontroller().getSelectedPlayer().getPosition());
        p.setIstAktiv(false);
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
    }

    public void onInstallateurClick (ActionEvent e){

    }

    public void onSaboteurClick (ActionEvent e){
        Button bAktiv = (Button)e.getSource();

    }

    public void onReparierenClick(ActionEvent e) {
        Kontroller.getKontroller().getSelectedPlayer().getPosition().reparieren();
        GuiMap.getGuiMap().refresh();
    }

    public void onRohrUmbindenClick(ActionEvent e) {
        Rohr r = Kontroller.getKontroller().getMap().findRohr(Kontroller.getKontroller().getSelectedPlayer().getPosition());
       // System.out.println("aktuelle Rohr: "+ r.getName());
        rohrUmbinden = true;
    }

    public void onLoechernClick(ActionEvent e) {
        Kontroller.getKontroller().getSelectedPlayer().getPosition().kaputtMachen();
        GuiMap.getGuiMap().refresh();
    }

    public void onPumpeAufnehmen(ActionEvent e) {
        if (Kontroller.getKontroller().getSelectedPlayer() instanceof Installateur) {
            Installateur inst = (Installateur) Kontroller.getKontroller().getSelectedPlayer();
            inst.pumpeAufnehmen();
        }
    }

}
