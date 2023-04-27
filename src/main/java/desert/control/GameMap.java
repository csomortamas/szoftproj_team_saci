package main.java.desert.control;

import lombok.Getter;
import lombok.Setter;
import main.java.desert.network.*;

import java.util.ArrayList;
import java.util.List;

public class GameMap {
    @Getter
    @Setter
    private List<Rohr> rohre = new ArrayList<>();
    @Getter
    @Setter
    private List<Pumpe> pumpen = new ArrayList<>();
    @Getter
    @Setter
    private List<Wasserquelle> wasserquellen = new ArrayList<>();
    @Getter
    @Setter
    private List<Zisterne> zisternen = new ArrayList<>();


/*
    public void saveMap(){
        try {
            Writer writer = new FileWriter("gameMap.json");
            new Gson().toJson (this, writer);
            writer.close ();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadMap(GameMap map){
        Gson gson = new Gson ();
        try {
            JsonReader reader = new JsonReader (new FileReader("gameMap.json"));
            map = gson.fromJson (reader, new TypeToken<GameMap>() {
            }.getType ());
        } catch (FileNotFoundException e) {
            e.printStackTrace ();
        }
    }
*/

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

    public Wasserquelle findWasserquelle(Netzelement wasserquelle) {
        for (Wasserquelle w : wasserquellen) {
            if (w == wasserquelle) {
                return w;
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
}
