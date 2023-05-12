package desert.gui;
//import desert.*;
import desert.control.Kontroller;
import desert.network.Rohr;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

import java.util.ArrayList;
import java.util.List;

public class GuiMap {
    Scene scene;
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
    private List<Button> buttonsOfQuelle = new ArrayList<>();
    private List<Button> buttonsOfZisterne = new ArrayList<>();
    private List<Button> buttonsOfPumpe = new ArrayList<>();
    private List<Line> linesOfRohre = new ArrayList<>();

    public void sceneSetup(){
        for(int i = 0; i < Kontroller.getKontroller().getMap().getWasserquellen().size(); i++){
          /*  buttonsOfPumpe.add((Button)scene.lookup("#pumpe"+i));
            buttonsOfQuelle.add((Button)scene.lookup("#wasserQuelle"+i));
            buttonsOfZisterne.add((Button)scene.lookup("#zisterne"+i));*/

            Kontroller.getKontroller().getMap().getPumpen().get(i).setButton((Button)scene.lookup("#pumpe"+(i+1)));
            Kontroller.getKontroller().getMap().getWasserquellen().get(i).setButton((Button)scene.lookup("#wasserQuelle"+(i+1)));
            Kontroller.getKontroller().getMap().getZisternen().get(i).setButton((Button)scene.lookup("#zisterne"+(i+1)));
          
        }
        for(int i=0; i<Kontroller.getKontroller().getMap().getRohre().size();i++){
            Kontroller.getKontroller().getMap().getRohre().get(i).setLine((Line)scene.lookup("#rohr" + (i+1)));
        }
    }

//getters  --------------------------------------
    public List<Button> getButtonsOfQuelle() {
        return buttonsOfQuelle;
    }

    public List<Button> getButtonsOfZisterne() {
        return buttonsOfZisterne;
    }

    public List<Button> getButtonsOfPumpe() {
        return buttonsOfPumpe;
    }
    public void refreshLine(){
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
}
