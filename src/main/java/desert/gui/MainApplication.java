package desert.gui;

import desert.control.GameMap;
import desert.control.Kontroller;
import desert.network.Pumpe;
import desert.network.Rohr;
import desert.network.Wasserquelle;
import desert.network.Zisterne;
import desert.player.Installateur;
import desert.player.Saboteur;
import desert.player.Spieler;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException, InterruptedException {
        Wasserquelle wasserquelle1 = new Wasserquelle(10,10);
        wasserquelle1.setName("Q1");
        Wasserquelle wasserquelle2 = new Wasserquelle(100,100);
        wasserquelle2.setName("Q2");
        Wasserquelle wasserquelle3 = new Wasserquelle(10,100);
        wasserquelle3.setName("Q3");
        List<Wasserquelle> wasserquelleList = new ArrayList<>();
        wasserquelleList.add(wasserquelle1);
        wasserquelleList.add(wasserquelle2);
        wasserquelleList.add(wasserquelle3);
        // create 3 zisterne
        Zisterne zisterne1 = new Zisterne(30, 20);
        zisterne1.setName("Z1");
        Zisterne zisterne2 = new Zisterne(30, 10);
        zisterne2.setName("Z2");
        Zisterne zisterne3 = new Zisterne(30, 40);
        zisterne3.setName("Z3");
        List<Zisterne> zisterneList = new ArrayList<>();
        zisterneList.add(zisterne1);
        zisterneList.add(zisterne2);
        zisterneList.add(zisterne3);
        // create 1 pumpe
        Pumpe pumpe1 = new Pumpe(20, 10);
        pumpe1.setName("P1");
        Pumpe pumpe2 = new Pumpe(20, 20);
        pumpe2.setName("P2");
        Pumpe pumpe3 = new Pumpe(20, 30);
        pumpe3.setName("P3");
        List<Pumpe> pumpeList = new ArrayList<>();
        pumpeList.add(pumpe1);
        pumpeList.add(pumpe2);
        pumpeList.add(pumpe3);
        // create 5 rohr
        Rohr rohr1 = new Rohr();
        Rohr rohr2 = new Rohr();
        Rohr rohr3 = new Rohr();
        Rohr rohr4 = new Rohr();
        Rohr rohr5 = new Rohr();
        rohr1.setName("R1");
        rohr2.setName("R2");
        rohr3.setName("R3");
        rohr4.setName("R4");
        rohr5.setName("R5");
        List<Rohr> rohrList = new ArrayList<>();
        rohrList.add(rohr1);
        rohrList.add(rohr2);
        rohrList.add(rohr3);
        rohrList.add(rohr4);
        rohrList.add(rohr5);
        Kontroller.getKontroller().setupV2(wasserquelleList, zisterneList, pumpeList, rohrList, "inst", "sab");
        //Kontroller.getKontroller().setup("ins", "sab");
        Spieler installateur1 = new Installateur(wasserquelle1);
        Spieler installateur2 = new Installateur(wasserquelle3);
        Spieler saboteur1 = new Saboteur(zisterne1);
        Spieler saboteur2 = new Saboteur(zisterne3);


        GuiMap.getGuiMap().setGroup(new Group());

        //--------fxml load
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("main-view.fxml"));
        GuiMap.getGuiMap().getGroup().getChildren().add(fxmlLoader.load());

        Scene scene = new Scene(GuiMap.getGuiMap().getGroup(), 687, 580);


        GuiMap.getGuiMap().setGuiMapScene(scene);
        GuiMap.getGuiMap().sceneSetup();
        stage.setTitle("Desert");
        stage.setScene(scene);
        stage.show();
        //-------fxml load
        Kontroller.getKontroller().tick();
        Kontroller.getKontroller().setInstallateurPunkte(0);
        GuiMap.getGuiMap().refreshPoints();
        GuiMap.getGuiMap().refreshRoehre();

    }


    public static void main(String[] args) {
        launch();

        //
    }



}