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
                break;
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


    public static class LineClickAction implements EventHandler<MouseEvent> {
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

        }
    }
    private double calculateSpielerPos(double startCoord, double endCoord){
        return (abs((endCoord - startCoord))/2 +startCoord);
    }

    public void pumpeAkrivierenClick(ActionEvent e){
        if(Kontroller.getKontroller().getAktuelleRunde() %2 ==0) return;

        if(Kontroller.getKontroller().getSelectedPlayer() instanceof Saboteur) return;
        Button bAktiv = (Button)e.getSource();
        for(Pumpe p : Kontroller.getKontroller().getMap().getPumpen()){
            if(p.getButton() == bAktiv){

            }
        }
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