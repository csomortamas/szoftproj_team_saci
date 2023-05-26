package desert.control;

import desert.network.*;
import javafx.scene.control.Button;

import java.util.ArrayList;
import java.util.List;

public class GameMap {
    private final List<Rohr> rohre = new ArrayList<>();
    private final List<Pumpe> pumpen = new ArrayList<>();
    private final List<Wasserquelle> wasserquellen = new ArrayList<>();
    private final List<Zisterne> zisternen = new ArrayList<>();

    public Pumpe findPumpe(Netzelement pumpe) {
        for (Pumpe p : pumpen) {
            if (p == pumpe) {
                return p;
            }
        }
        return null;
    }

    public Rohr findRohr(Netzelement rohr) {
        for (Rohr r : rohre) {
            if (r == rohr) {
                return r;
            }
        }
        return null;
    }


    public Zisterne findZisterne(Netzelement zisterne) {
        for (Zisterne z : zisternen) {
            if (z == zisterne) {
                return z;
            }
        }
        return null;
    }

    public Pumpe findInAllePumpen(Netzelement _pumpe) {
        List<Pumpe> allePumpen = new ArrayList<>();
        allePumpen.addAll(Kontroller.getKontroller().getMap().getPumpen());
        allePumpen.addAll(Kontroller.getKontroller().getMap().getZisternen());
        allePumpen.addAll(Kontroller.getKontroller().getMap().getWasserquellen());

        Pumpe pumpe = null;
        for (Pumpe p : allePumpen) {
            if (p == _pumpe) {
                pumpe = p;
            }
        }
        return pumpe;
    }


    public Pumpe findInAllPumpeWithButton(Button butt) {
        Pumpe pumpe = null;
        List<Pumpe> allePumpen = new ArrayList<>();
        allePumpen.addAll(Kontroller.getKontroller().getMap().getPumpen());
        allePumpen.addAll(Kontroller.getKontroller().getMap().getZisternen());
        allePumpen.addAll(Kontroller.getKontroller().getMap().getWasserquellen());


        for (Pumpe p : allePumpen) {
            if (p.getButton() == butt) {
                pumpe = p;
                break;
            }
        }
        return pumpe;
    }

    //=================================GETTERS & SETTERS ====================================
    //=======================================================================================
    public List<Rohr> getRohre() {
        return rohre;
    }

    public List<Pumpe> getPumpen() {
        return pumpen;
    }

    public List<Wasserquelle> getWasserquellen() {
        return wasserquellen;
    }

    public List<Zisterne> getZisternen() {
        return zisternen;
    }
}
