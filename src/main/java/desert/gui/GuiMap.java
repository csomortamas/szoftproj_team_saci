package desert.gui;

import desert.control.Kontroller;
import desert.network.Netzelement;
import desert.network.Pumpe;
import desert.network.Rohr;
import desert.network.Zisterne;
import desert.player.Installateur;
import desert.player.Saboteur;
import desert.player.Spieler;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import static java.lang.Math.abs;

//Water Pump pic: <a href="https://www.flaticon.com/free-icons/water-pump" title="water pump icons">Water pump icons created by Freepik - Flaticon</a>
//Zistern pic: <a href="https://www.flaticon.com/free-icons/water-tank" title="water tank icons">Water tank icons created by Enes Gozcu - Flaticon</a>
//Water source/Waterfall pic: <a href="https://www.flaticon.com/free-icons/waterfall" title="waterfall icons">Waterfall icons created by photo3idea_studio - Flaticon</a>
//Installateur pic: <a href="https://www.flaticon.com/free-icons/installation" title="installation icons">Installation icons created by Muhammad_Usman - Flaticon</a>
//Saboteur pic: <a href="https://www.flaticon.com/free-icons/thief" title="thief icons">Thief icons created by Freepik - Flaticon</a>
public class GuiMap {
    private Group group = null;
    private Scene scene;
    private  Scene settingsScene;
    private Stage settingsStage;

    private DropShadow borderGlow;
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
            Kontroller.getKontroller().getMap().getPumpen().get(i).getButton().setBackground(Background.EMPTY);
            Kontroller.getKontroller().getMap().getWasserquellen().get(i).setButton((Button) scene.lookup("#wasserQuelle" + (i + 1)));
            Kontroller.getKontroller().getMap().getWasserquellen().get(i).getButton().setBackground(Background.EMPTY);
            Kontroller.getKontroller().getMap().getZisternen().get(i).setButton((Button) scene.lookup("#zisterne" + (i + 1)));
            Kontroller.getKontroller().getMap().getZisternen().get(i).getButton().setBackground(Background.EMPTY);
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
        borderGlow = new DropShadow();
        borderGlow.setOffsetY(0f);
        borderGlow.setOffsetX(0f);
        borderGlow.setWidth(20);
        borderGlow.setHeight(20);
    }

    public void loadGame(){
        TextField instTName = (TextField) GuiMap.getGuiMap().getScene().lookup("#txtInstTName");
        TextField sabTName = (TextField) GuiMap.getGuiMap().getScene().lookup("#txtSabTName");
        Kontroller.getKontroller().setInstallateurTeamName(instTName.getText());
        Kontroller.getKontroller().setSaboteurTeamName(sabTName.getText());

        group=new Group();

        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("main-view.fxml"));
        try {
            group.getChildren().add(fxmlLoader.load());
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        scene.setRoot(GuiMap.getGuiMap().getGroup());
    }

    public void openSettings(){
        Group settingsGroup = new Group();

        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("settings.fxml"));
        try {
            settingsGroup.getChildren().add(fxmlLoader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }

        settingsScene = new Scene(settingsGroup, 600, 314);
        settingsStage=new Stage();
        settingsStage.setTitle("Desert - Einstellungen");
        settingsStage.getIcons().add(new Image(MainApplication.class.getResourceAsStream("settings.png")));
        settingsStage.setScene(settingsScene);
        settingsStage.show();

        TextField rundeAnzahl = (TextField) settingsScene.lookup("#rundeAnzahl");
        rundeAnzahl.setText(String.valueOf(Kontroller.getKontroller().getMaxRunde()));

        TextField neueP = (TextField) settingsScene.lookup("#neueP");
        neueP.setText(String.valueOf(Kontroller.getKontroller().getNeuePumpeChance()));

        TextField neueR = (TextField) settingsScene.lookup("#neueR");
        neueR.setText(String.valueOf(Kontroller.getKontroller().getNeueRohrChance()));

        TextField pumpeK = (TextField) settingsScene.lookup("#pumpeK");
        pumpeK.setText(String.valueOf(Kontroller.getKontroller().getPumpeKaputtGehtChance()));
    }

    public void endOfGameDialog(){
        Alert dialog = new Alert(Alert.AlertType.INFORMATION);
        dialog.setTitle("Game Over");
        dialog.setHeaderText("Das Spiel ist zu Ende!");
        if(Kontroller.getKontroller().getInstallateurPunkte()>Kontroller.getKontroller().getSaboteurPunkte()){
            dialog.setContentText("Der Team "+Kontroller.getKontroller().getInstallateurTeamName()+" hat gewonnen!");
        } else if(Kontroller.getKontroller().getInstallateurPunkte()<Kontroller.getKontroller().getSaboteurPunkte()){
            dialog.setContentText("Der Team "+Kontroller.getKontroller().getSaboteurTeamName()+" hat gewonnen!");
        }else {
            dialog.setContentText("Das Spiel ist unentschieden!");
        }

        dialog.setResizable(false);
        dialog.setOnCloseRequest(new EventHandler<DialogEvent>() {
            @Override
            public void handle(DialogEvent event) {
                System.exit(0);
            }
        });

        Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image(MainApplication.class.getResourceAsStream("desert_smallLogo.png")));

        ButtonType exitButtonType = new ButtonType("Exit");
        //ButtonType leadeboardButtonType = new ButtonType("Leaderboard anzeigen");

        dialog.getButtonTypes().setAll(exitButtonType);

        Optional<ButtonType> result = dialog.showAndWait();
        if (result.get() == exitButtonType) {
            System.exit(0);
        }
    }

    public void refreshRoehre() {
        for (Rohr rohr : Kontroller.getKontroller().getMap().getRohre()) {
            if (rohr.isIstAktiv() && rohr.isIstKaputt()) {
                rohr.getLine().setStroke(Color.ORANGE);
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


        if (Kontroller.getKontroller().getSelectedPlayer() instanceof Installateur) {
            borderGlow.setColor(Color.GREEN);
        } else {
            borderGlow.setColor(Color.RED);
        }
        Kontroller.getKontroller().getSelectedPlayer().getButton().setEffect(borderGlow);
        Kontroller.getKontroller().getLastSelectedPlayer().getButton().setEffect(null);
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

        Zisterne zisterne = Kontroller.getKontroller().getMap().findZisterne(Kontroller.getKontroller().getSelectedPlayer().getPosition());
        Button instPumpeAufnehmen = (Button) GuiMap.getGuiMap().getScene().lookup("#instPumpeAufnehmen");
        if (zisterne != null && zisterne.getPumpeZurVerfuegung() != null) {
            instPumpeAufnehmen.setDisable(false);
        } else {
            instPumpeAufnehmen.setDisable(true);
        }

        Spieler selSp = Kontroller.getKontroller().getSelectedPlayer();
        Installateur inst = null;
        for (Installateur i : Kontroller.getKontroller().getInstallateurTeam()) {
            if (i == selSp) {
                inst = i;
                break;
            }
        }

        Button instEinmontierenButton = (Button) GuiMap.getGuiMap().getScene().lookup("#instEinmontierenButton");
        if (inst != null) {
            if (inst.getPumpeInHand() != null) {
                instEinmontierenButton.setDisable(false);
            } else {
                instEinmontierenButton.setDisable(true);
            }
        }

    }

    public void refreshRohrPosition(Rohr rohr) {
        Pumpe nachbarnPumpe1 = Kontroller.getKontroller().getMap().findInAllePumpen(rohr.getNachbarn().get(0));
        Pumpe nachbarnPumpe2 = Kontroller.getKontroller().getMap().findInAllePumpen(rohr.getNachbarn().get(1));

        if (nachbarnPumpe1.getButton().getWidth() != 0 && nachbarnPumpe1.getButton().getHeight() != 0) {
            rohr.getLine().setStartX(nachbarnPumpe1.getButton().getLayoutX() + (nachbarnPumpe1.getButton().getWidth() / 2));
            rohr.getLine().setStartY(nachbarnPumpe1.getButton().getLayoutY() + (nachbarnPumpe1.getButton().getHeight() / 2));
        } else {
            rohr.getLine().setStartX(nachbarnPumpe1.getButton().getLayoutX() + (nachbarnPumpe1.getButton().getPrefWidth() / 2));
            rohr.getLine().setStartY(nachbarnPumpe1.getButton().getLayoutY() + (nachbarnPumpe1.getButton().getPrefHeight() / 2));
        }

        if (nachbarnPumpe2.getButton().getWidth() != 0 && nachbarnPumpe2.getButton().getHeight() != 0) {
            rohr.getLine().setEndX(nachbarnPumpe2.getButton().getLayoutX() + (nachbarnPumpe2.getButton().getWidth() / 2));
            rohr.getLine().setEndY(nachbarnPumpe2.getButton().getLayoutY() + (nachbarnPumpe2.getButton().getHeight() / 2));
        } else {
            rohr.getLine().setEndX(nachbarnPumpe2.getButton().getLayoutX() + (nachbarnPumpe2.getButton().getPrefWidth() / 2));
            rohr.getLine().setEndY(nachbarnPumpe2.getButton().getLayoutY() + (nachbarnPumpe2.getButton().getPrefHeight() / 2));
        }

        Kontroller.getKontroller().getSelectedPlayer().getButton().setLayoutX(calculateSpielerPos(rohr.getLine().getStartX(), rohr.getLine().getEndX()));
        Kontroller.getKontroller().getSelectedPlayer().getButton().setLayoutY(calculateSpielerPos(rohr.getLine().getEndY(), rohr.getLine().getStartY()));

    }

    public void refreshSplittedRohr(Rohr r1, Rohr r2) {
        Line l1 = new Line();
        Line l2 = new Line();
        l1.setStrokeWidth(7);
        l2.setStrokeWidth(7);
        l1.setOnMouseClicked(MainController.getMainController().getLineClickAction());
        l2.setOnMouseClicked(MainController.getMainController().getLineClickAction());


        r1.setLine(l1);
        r2.setLine(l2);
        Pane pane = (Pane) GuiMap.getGuiMap().getScene().lookup("#mainPane");
        pane.getChildren().add(l1);
        pane.getChildren().add(l2);
        l1.toBack();
        l2.toBack();
    }

    public void refreshSpieler() {
        List<Spieler> alleSpielerList = new ArrayList<>();
        alleSpielerList.addAll(Kontroller.getKontroller().getInstallateurTeam());
        alleSpielerList.addAll(Kontroller.getKontroller().getSaboteurTeam());

        Spieler spieler = Kontroller.getKontroller().getSelectedPlayer();
        Netzelement spielerPosition = spieler.getPosition();


        for (Installateur i : Kontroller.getKontroller().getInstallateurTeam()) {
            if (i.getPumpeInHand() != null) {
                i.getButton().setBackground(Background.fill(Color.YELLOW));
            } else {
                i.getButton().setStyle(null);
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
                pumpe.getButton().setBackground(Background.EMPTY);
            } else {
                pumpe.getButton().setBackground(Background.fill(Color.BLANCHEDALMOND));
            }
            if (pumpe.isIstKaputt()) {
                pumpe.getButton().setBackground(Background.fill(Color.RED));
            }
        }
    }

    public void refreshEinmontiertePumpe(Pumpe p, double startX, double startY, double endX, double endY) {
        Button newPumpeButton = new Button();

        newPumpeButton.setLayoutX(calculateSpielerPos(startX, endX) - 34);
        newPumpeButton.setLayoutY(calculateSpielerPos(startY, endY) - 30);
        newPumpeButton.setPrefWidth(69);
        newPumpeButton.setPrefHeight(61);

        Image image = new Image(System.getProperty("user.dir") + File.separator + "src" + File.separator + "main" + File.separator + "resources" + File.separator + "desert" + File.separator + "gui" + File.separator + "water-pump.png");
        ImageView imageView = new ImageView(image);
        imageView.setPreserveRatio(true);
        imageView.setFitHeight(59);
        //imageView.setFitWidth(62);
        newPumpeButton.setGraphic(imageView);
        newPumpeButton.setBackground(Background.EMPTY);
        p.setButton(newPumpeButton);

        Pane pane = (Pane) GuiMap.getGuiMap().getScene().lookup("#mainPane");
        pane.getChildren().add(newPumpeButton);
        newPumpeButton.toBack();
        Rohr r1 = Kontroller.getKontroller().getMap().findRohr(p.getNachbarn().get(0));
        Rohr r2 = Kontroller.getKontroller().getMap().findRohr(p.getNachbarn().get(1));
        r1.getLine().toBack();
        r2.getLine().toBack();
    }

    public void refreshReadyPumps() {
        for (Zisterne z : Kontroller.getKontroller().getMap().getZisternen()) {
            if (z.getPumpeZurVerfuegung() != null) {
                z.getReadyPumpImage().setOpacity(1.0);
            } else if (z.getPumpeZurVerfuegung() == null) {
                z.getReadyPumpImage().setOpacity(0.5);
            }
        }
    }

    public void refreshNewRohre(Zisterne z, Rohr newRohr) {
        Random rand = new Random();
        int plusY = rand.nextInt(-40, 40);

        double startX = z.getButton().getLayoutX() + 35;
        double startY = z.getButton().getLayoutY() + 31;
        Line line = new Line(startX, startY, (startX - 100), (startY + plusY));

        line.setStrokeWidth(7);
        newRohr.setLine(line);
        line.setOnMouseClicked(MainController.getMainController().getLineClickAction());

        Pane pane = (Pane) GuiMap.getGuiMap().getScene().lookup("#mainPane");
        pane.getChildren().add(line);
        line.toBack();
        //group.getChildren().add(line);
        //z.getButton().toFront();
        //line.toBack();

    }

    public void refreshAlleRohre() {
        for (Rohr rohr : Kontroller.getKontroller().getMap().getRohre()) {
            if (rohr.getNachbarn().size() == 2) {
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

    public Scene getSettingsScene() {
        return settingsScene;
    }

    public void setSettingsScene(Scene settingsScene) {
        this.settingsScene = settingsScene;
    }

    public Stage getSettingsStage() {
        return settingsStage;
    }

    public void setSettingsStage(Stage settingsStage) {
        this.settingsStage = settingsStage;
    }
}
