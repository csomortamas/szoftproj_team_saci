package desert.gui;

import desert.control.Kontroller;
import desert.network.Netzelement;
import desert.network.Pumpe;
import desert.network.Rohr;
import desert.network.Zisterne;
import desert.player.Installateur;
import desert.player.Saboteur;
import desert.player.Spieler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.effect.Blend;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static java.lang.Math.abs;

//Water Pump pic: <a href="https://www.flaticon.com/free-icons/water-pump" title="water pump icons">Water pump icons created by Freepik - Flaticon</a>
//Zistern pic: <a href="https://www.flaticon.com/free-icons/water-tank" title="water tank icons">Water tank icons created by Enes Gozcu - Flaticon</a>
//Water source/Waterfall pic: <a href="https://www.flaticon.com/free-icons/waterfall" title="waterfall icons">Waterfall icons created by photo3idea_studio - Flaticon</a>
//Installateur pic: <a href="https://www.flaticon.com/free-icons/installation" title="installation icons">Installation icons created by Muhammad_Usman - Flaticon</a>
//Saboteur pic: <a href="https://www.flaticon.com/free-icons/thief" title="thief icons">Thief icons created by Freepik - Flaticon</a>
public class GuiMap {
    private Group group=null;
    private Scene scene;
    private final static GuiMap guiMap = new GuiMap();

    public static GuiMap getGuiMap() {
        return guiMap;
    }

    public void setGuiMapScene(Scene _scene) {
        this.scene = _scene;
    }

    private GuiMap() {

    }

    public GuiMap(Scene _scene) {
        this.scene = _scene;
    }

    public void sceneSetup() {
        for (int i = 0; i < Kontroller.getKontroller().getMap().getWasserquellen().size(); i++) {
            Kontroller.getKontroller().getMap().getPumpen().get(i).setButton((Button) scene.lookup("#pumpe" + (i + 1)));
            Kontroller.getKontroller().getMap().getWasserquellen().get(i).setButton((Button) scene.lookup("#wasserQuelle" + (i + 1)));
            Kontroller.getKontroller().getMap().getWasserquellen().get(i).getButton().setBackground(Background.EMPTY);
            Kontroller.getKontroller().getMap().getZisternen().get(i).setButton((Button) scene.lookup("#zisterne" + (i + 1)));
        }
        for (int i = 0; i < Kontroller.getKontroller().getMap().getPumpen().size(); i++) {
            Kontroller.getKontroller().getMap().getPumpen().get(i).setButton((Button) scene.lookup("#pumpe" + (i + 1)));
        }
        for (int i = 0; i < Kontroller.getKontroller().getMap().getZisternen().size(); i++) {
            Kontroller.getKontroller().getMap().getZisternen().get(i).setButton((Button) scene.lookup("#zisterne" + (i + 1)));
            Kontroller.getKontroller().getMap().getZisternen().get(i).getButton().setBackground(Background.EMPTY);
            Kontroller.getKontroller().getMap().getZisternen().get(i).setReadyPumpImage((ImageView) scene.lookup("#readyPump" + (i + 1)));
        }
        for (int i = 0; i < Kontroller.getKontroller().getMap().getRohre().size(); i++) {
            Kontroller.getKontroller().getMap().getRohre().get(i).setLine((Line) scene.lookup("#rohr" + (i + 1)));
        }

        for (int i = 0; i < 2; i++) {
            Kontroller.getKontroller().getInstallateurTeam().get(i).setButton((Button) scene.lookup("#installateur" + (i + 1)));
            Kontroller.getKontroller().getSaboteurTeam().get(i).setButton((Button) scene.lookup("#saboteur" + (i + 1)));
        }


    }

    public void refreshRoehre() {
        for (Rohr rohr : Kontroller.getKontroller().getMap().getRohre()) {
            if (rohr.isIstAktiv() && rohr.isIstKaputt()) {
                rohr.getLine().setStroke(Color.PURPLE);
            } else if (rohr.isIstAktiv() && !rohr.isIstKaputt()) {
                rohr.getLine().setStroke(Color.BLUE);
            } else if (!rohr.isIstAktiv() && rohr.isIstKaputt()) {
                rohr.getLine().setStroke(Color.RED);
            } else if (!rohr.isIstAktiv() && !rohr.isIstKaputt()) {
                rohr.getLine().setStroke(Color.BLACK);
            }
        }
    }

    public void refreshPlayerButtons() {
        if (Kontroller.getKontroller().getActionCount() % 2 == 0) {
            for (Installateur i : Kontroller.getKontroller().getInstallateurTeam()) {
                i.getButton().setDisable(false);
            }
            for (Saboteur s : Kontroller.getKontroller().getSaboteurTeam()) {
                s.getButton().setDisable(true);
            }
        } else {
            for (Installateur i : Kontroller.getKontroller().getInstallateurTeam()) {
                i.getButton().setDisable(true);
            }
            for (Saboteur s : Kontroller.getKontroller().getSaboteurTeam()) {
                s.getButton().setDisable(false);
            }
        }
    }

    public void refreshControlPanes() {
        Pane sabPumpePane = (Pane) GuiMap.getGuiMap().getScene().lookup("#sabPumpePane");
        Pane instPumpePane = (Pane) GuiMap.getGuiMap().getScene().lookup("#instPumpePane");
        Pane sabRohrPane = (Pane) GuiMap.getGuiMap().getScene().lookup("#sabRohrPane");
        Pane instRohrPane = (Pane) GuiMap.getGuiMap().getScene().lookup("#instRohrPane");

        sabPumpePane.setVisible(false);
        sabPumpePane.setDisable(true);
        instPumpePane.setVisible(false);
        instPumpePane.setDisable(true);
        sabRohrPane.setVisible(false);
        sabRohrPane.setDisable(true);
        instRohrPane.setVisible(false);
        instRohrPane.setDisable(true);

        if (Kontroller.getKontroller().getActionCount() % 2 == 0) {
            if (Kontroller.getKontroller().getSelectedPlayer().getPosition() instanceof Pumpe) {
                instPumpePane.setVisible(true);
                instPumpePane.setDisable(false);
            } else {
                instRohrPane.setVisible(true);
                instRohrPane.setDisable(false);
            }
        } else {
            if (Kontroller.getKontroller().getSelectedPlayer().getPosition() instanceof Pumpe) {
                sabPumpePane.setVisible(true);
                sabPumpePane.setDisable(false);
            } else {
                sabRohrPane.setVisible(true);
                sabRohrPane.setDisable(false);
            }
        }

    }

    public void refreshRohrPosition(Rohr rohr) {
        Pumpe nachbarnPumpe1 = Kontroller.getKontroller().getMap().findInAllePumpen(rohr.getNachbarn().get(0));
        Pumpe nachbarnPumpe2 = Kontroller.getKontroller().getMap().findInAllePumpen(rohr.getNachbarn().get(1));

        rohr.getLine().setStartX(nachbarnPumpe1.getButton().getLayoutX() + (nachbarnPumpe1.getButton().getWidth() / 2));
        rohr.getLine().setStartY(nachbarnPumpe1.getButton().getLayoutY() + (nachbarnPumpe1.getButton().getHeight() / 2));


        rohr.getLine().setEndX(nachbarnPumpe2.getButton().getLayoutX() + (nachbarnPumpe2.getButton().getWidth() / 2));
        rohr.getLine().setEndY(nachbarnPumpe2.getButton().getLayoutY() + (nachbarnPumpe2.getButton().getHeight() / 2));
        rohr.getLine().toBack();

        Kontroller.getKontroller().getSelectedPlayer().getButton().setLayoutX(calculateSpielerPos(rohr.getLine().getStartX(), rohr.getLine().getEndX()));
        Kontroller.getKontroller().getSelectedPlayer().getButton().setLayoutY(calculateSpielerPos(rohr.getLine().getEndY(), rohr.getLine().getStartY()));

    }
    public void refreshSplittedRohr(Rohr r1, Rohr r2) {
        Line l1 = new Line();
        Line l2 = new Line();

        r1.setLine(l1);
        r2.setLine(l2);
        group.getChildren().add(l1);
        group.getChildren().add(l2);

    }

    public void refreshSpieler() {
        List<Spieler> alleSpielerList = new ArrayList<>();
        alleSpielerList.addAll(Kontroller.getKontroller().getInstallateurTeam());
        alleSpielerList.addAll(Kontroller.getKontroller().getSaboteurTeam());

        Spieler spieler = Kontroller.getKontroller().getSelectedPlayer();
        Netzelement spielerPosition = spieler.getPosition();


        for (Installateur i : Kontroller.getKontroller().getInstallateurTeam()){
            if(i.getPumpeInHand() != null){
                i.getButton().setBackground(Background.fill(Color.YELLOW));
            }else{
                i.getButton().setBackground(Background.EMPTY);
            }
        }

        if (spieler.getPosition() instanceof Rohr) {
            Rohr rohr = Kontroller.getKontroller().getMap().findRohr(spielerPosition);
            spieler.getButton().setLayoutX(calculateSpielerPos(rohr.getLine().getStartX(), rohr.getLine().getEndX()));
            spieler.getButton().setLayoutY(calculateSpielerPos(rohr.getLine().getStartY(), rohr.getLine().getEndY()));
        } else {
            Pumpe pumpe = Kontroller.getKontroller().getMap().findInAllePumpen(spielerPosition);
            spieler.getButton().setLayoutX(pumpe.getButton().getLayoutX() + randomTinyShift(pumpe.getButton(), spieler));
            spieler.getButton().setLayoutY(pumpe.getButton().getLayoutY() + randomTinyShift(pumpe.getButton(), spieler));
        }
    }

    // helper function
    private double calculateSpielerPos(double startCoord, double endCoord) {
        return (abs((endCoord - startCoord)) / 2 + Math.min(startCoord, endCoord));
    }

    // helper function
    private double randomTinyShift(Button buttonSurface, Spieler spieler) {
        double pumpeButtonWidth = buttonSurface.getWidth();
        double spielerButtonWidth = spieler.getButton().getWidth();
        return Math.random() * (pumpeButtonWidth - spielerButtonWidth);
    }

    public void refreshRoundCounter() {
        Text l = (Text) GuiMap.getGuiMap().getScene().lookup("#txtAktuelleRunde");
        l.setText(String.valueOf((int) (Math.floor((Kontroller.getKontroller().getActionCount() / 2) + 1))));
    }

    public void refreshPoints() {
        Text instPunkte = (Text) GuiMap.getGuiMap().getScene().lookup("#instPunkte");
        if (Kontroller.getKontroller().getInstallateurPunkte() >= 10) {
            instPunkte.setLayoutX(271);
        }
        instPunkte.setText(String.valueOf(Kontroller.getKontroller().getInstallateurPunkte()));
        Text sabPunkte = (Text) GuiMap.getGuiMap().getScene().lookup("#sabPunkte");
        sabPunkte.setText(String.valueOf(Kontroller.getKontroller().getSaboteurPunkte()));
    }


    public void refreshPumpen() {
        for (Pumpe pumpe : Kontroller.getKontroller().getMap().getPumpen()) {

            if (pumpe.isIstAktiv()) {
                //pumpe.getButton().setBackground(Background.EMPTY);
            } else {
                pumpe.getButton().setBackground(Background.fill(Color.BLANCHEDALMOND));
            }
            if (pumpe.isIstKaputt()) {
                pumpe.getButton().setBackground(Background.fill(Color.RED));
            }
        }
    }
    public void refreshEinmontiertePumpe(Pumpe p, double startX, double startY, double endX, double endY){
            Button newPumpeButton = new Button();

            newPumpeButton.setLayoutX(calculateSpielerPos(startX, endX));
            newPumpeButton.setLayoutY(calculateSpielerPos(startY, endY));
            p.setButton(newPumpeButton);
            group.getChildren().add(newPumpeButton);

    }

    public void refreshReadyPumps() {
        for (Zisterne z : Kontroller.getKontroller().getMap().getZisternen()) {
            if (z.getPumpeZurVerfuegung() != null) {
                z.getReadyPumpImage().setOpacity(1.0);
            }else if(z.getPumpeZurVerfuegung() == null){
                z.getReadyPumpImage().setOpacity(0.5);
            }
        }
    }

    public void refreshNewRohre(Zisterne z, Rohr newRohr) {
        Random rand = new Random();
        int plusY=rand.nextInt(-40,40);

        double startX=z.getButton().getLayoutX()+(z.getButton().getWidth()/2);
        double startY=z.getButton().getLayoutY()+(z.getButton().getHeight()/2);
        Line line=new Line(startX,startY,(startX-100),(startY+plusY));

        line.setStrokeWidth(7);
        newRohr.setLine(line);
        group.getChildren().add(line);
        line.toBack();

        //line.setOnMouseClicked(n);
    }
    public void refreshAlleRohre(){
        for(Rohr rohr : Kontroller.getKontroller().getMap().getRohre()){
            if(rohr.getNachbarn().size()==2){
                refreshRohrPosition(rohr);
            }
        }
       /* Pumpe p = new Pumpe(1,2);
        p.getButton().setOnMouseClicked(group.getOnMouseClicked());*/
    }

    public Scene getScene() {
        return scene;
    }

    public Group getGroup() {
        return group;
    }
    public void setGroup(Group group) {
        this.group = group;
    }
}
