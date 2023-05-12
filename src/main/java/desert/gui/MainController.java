package desert.gui;

import desert.control.Kontroller;
import desert.network.Pumpe;
import desert.network.Rohr;
import desert.network.Wasserquelle;
import desert.network.Zisterne;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Line;

public class MainController {

    @FXML
    public void onQuelleClick(ActionEvent e){
        Button buttonQuelle = (Button)e.getSource();
        Wasserquelle quelle = new Wasserquelle(buttonQuelle.getLayoutX(), buttonQuelle.getLayoutY());

        for (Wasserquelle w : Kontroller.getKontroller().getMap().getWasserquellen()) {
            System.out.println(w.getPosX());
            if (w.getButton() == buttonQuelle) {
                quelle = w;
                w.getButton().setText("quelle");
                break;
            }
        }
    }

    public void onPumpeClick(ActionEvent e){
        Button buttonQuelle = (Button)e.getSource();
        Pumpe pumpe = new Pumpe(buttonQuelle.getLayoutX(), buttonQuelle.getLayoutY());

        for (Pumpe p : Kontroller.getKontroller().getMap().getPumpen()) {
            System.out.println(p.getPosX());
            if (p.getButton() == buttonQuelle) {
                pumpe = p;
                p.getButton().setText("pumpe");
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
                zist = z;
                z.getButton().setText("zisterne");
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
            GuiMap.getGuiMap().refreshLine();

        }
    }

}