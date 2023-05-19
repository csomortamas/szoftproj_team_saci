package desert.gui;
import desert.control.Kontroller;
import desert.network.Pumpe;
import desert.network.Rohr;
import desert.player.Spieler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

import java.util.ArrayList;
import java.util.List;
//Water Pump pic: <a href="https://www.flaticon.com/free-icons/water-pump" title="water pump icons">Water pump icons created by Freepik - Flaticon</a>
//Zistern pic: <a href="https://www.flaticon.com/free-icons/water-tank" title="water tank icons">Water tank icons created by Enes Gozcu - Flaticon</a>
//Water source/Waterfall pic: <a href="https://www.flaticon.com/free-icons/waterfall" title="waterfall icons">Waterfall icons created by photo3idea_studio - Flaticon</a>
//Installateur pic: <a href="https://www.flaticon.com/free-icons/installation" title="installation icons">Installation icons created by Muhammad_Usman - Flaticon</a>
//Saboteur pic: <a href="https://www.flaticon.com/free-icons/thief" title="thief icons">Thief icons created by Freepik - Flaticon</a>
public class GuiMap {
    private Scene scene;
    private final static GuiMap guiMap = new GuiMap();
    public static GuiMap getGuiMap (){
        return guiMap;
    }
    public void setGuiMapScene(Scene _scene){
        this.scene = _scene;
    }
    private GuiMap(){

    }
    public GuiMap(Scene _scene){
        this.scene = _scene;
    }

    public void sceneSetup(){
        for(int i = 0; i < Kontroller.getKontroller().getMap().getWasserquellen().size(); i++){

            Kontroller.getKontroller().getMap().getPumpen().get(i).setButton((Button)scene.lookup("#pumpe"+(i+1)));
            Kontroller.getKontroller().getMap().getWasserquellen().get(i).setButton((Button)scene.lookup("#wasserQuelle"+(i+1)));
            Kontroller.getKontroller().getMap().getZisternen().get(i).setButton((Button)scene.lookup("#zisterne"+(i+1)));
          
        }
        for(int i=0; i<Kontroller.getKontroller().getMap().getRohre().size();i++){
            Kontroller.getKontroller().getMap().getRohre().get(i).setLine((Line)scene.lookup("#rohr" + (i+1)));
        }


        for(int i = 0; i < 2; i++){
            Kontroller.getKontroller().getInstallateurTeam().get(i).setButton((Button)scene.lookup("#installateur"+(i+1)));
            Kontroller.getKontroller().getSaboteurTeam().get(i).setButton((Button)scene.lookup("#saboteur"+(i+1)));
        }

    }

    public void refreshRoehre(){
        for (Rohr rohr : Kontroller.getKontroller().getMap().getRohre()){
            if(rohr.isIstKaputt() == true){
                rohr.getLine().setStroke(Color.RED);
            }else if(rohr.isIstAktiv() == true){
                rohr.getLine().setStroke(Color.BLUE);
            }else{
                rohr.getLine().setStroke(Color.BLACK);
            }
        }
    }

    public void repositionRohr(Rohr rohr){


        rohr.getLine().setStartX(nachbarnPumpe1.getButton().getLayoutX() + (nachbarnPumpe1.getButton().getWidth() / 2));
        rohr.getLine().setStartY(nachbarnPumpe1.getButton().getLayoutY() + (nachbarnPumpe1.getButton().getHeight() / 2));


        rohr.getLine().setEndX(nachbarnPumpe2.getButton().getLayoutX() + (nachbarnPumpe2.getButton().getWidth() / 2));
        rohr.getLine().setEndY(nachbarnPumpe2.getButton().getLayoutY() + (nachbarnPumpe2.getButton().getHeight() / 2));
        rohr.getLine().toBack();

        Kontroller.getKontroller().getSelectedPlayer().getButton().setLayoutX(calculateSpielerPos(rohr.getLine().getStartX(), rohr.getLine().getEndX()));
        Kontroller.getKontroller().getSelectedPlayer().getButton().setLayoutY(calculateSpielerPos(rohr.getLine().getEndY(), rohr.getLine().getStartY()));

    }

    public void refreshSpieler() {
        List<Spieler> alleSpielerList = new ArrayList<>();
        alleSpielerList.addAll(Kontroller.getKontroller().getInstallateurTeam());
        alleSpielerList.addAll(Kontroller.getKontroller().getSaboteurTeam());

        for (Spieler spieler : alleSpielerList) {
            spieler.getButton().setLayoutX(spieler.getPosition().);
        }

        //sp.getButton().setLayoutX(netzelement.getButton().getLayoutX() + (netzelement.getButton().getWidth() / 2));
        //sp.getButton().setLayoutY(netzelement.getButton().getLayoutY() + (netzelement.getButton().getHeight() / 2));
    }

    public void






    public Scene getScene() {
        return scene;
    }
}
