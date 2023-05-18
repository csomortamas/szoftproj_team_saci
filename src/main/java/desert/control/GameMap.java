package desert.control;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import desert.network.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class GameMap {
    private List<Rohr> rohre = new ArrayList<>();
    private List<Pumpe> pumpen = new ArrayList<>();
    private List<Wasserquelle> wasserquellen = new ArrayList<>();
    private List<Zisterne> zisternen = new ArrayList<>();


    private void toHash() {
        List<Netzelement> allElements = new ArrayList<>();
        allElements.addAll(rohre);
        allElements.addAll(pumpen);
        allElements.addAll(wasserquellen);
        allElements.addAll(zisternen);

        for (Netzelement netzElem : allElements) {
            netzElem.setHash(netzElem.hashCode());
        }
        for (Netzelement netzElem : allElements) {
            for (Netzelement nachbar : netzElem.getNachbarn()) {
                netzElem.getNachbarnHash().add(nachbar.getHash());
            }
        }
    }

    private void fromHash() {
        List<Netzelement> allElements = new ArrayList<>();
        allElements.addAll(rohre);
        allElements.addAll(pumpen);
        allElements.addAll(wasserquellen);
        allElements.addAll(zisternen);

        for (Netzelement n : allElements) {
            if (n.getNachbarn() == null) n.setNachbarn(new ArrayList<>());
            for (int integer : n.getNachbarnHash()) {
                for (Netzelement n2 : allElements) {
                    if (n2.getHash() == integer) {
                        n.getNachbarn().add(n2);
                        if (n2 instanceof Pumpe) {
                            Pumpe p;
                            if (n2 instanceof Wasserquelle) {
                                p = findWasserquelle(n2);
                            } else if (n2 instanceof Zisterne) {
                                p = findZisterne(n2);
                                Zisterne z = findZisterne(n2);
                                if (z.getPumpeZurVerfuegung() != null) {
                                    for (Pumpe pumAkk : pumpen) {
                                        if (pumAkk.getHash().equals(z.getPumpeZurVerfuegung().getHash())) {
                                            z.setPumpeZurVerfuegung(pumAkk);
                                            break;
                                        }
                                    }
                                }

                            } else {
                                p = findPumpe(n2);
                            }
                            if (p.getAusgangsRohr() != null) {
                                for (Rohr r : rohre) {
                                    if (r.getHash().equals(p.getAusgangsRohr().getHash())) {
                                        p.setAusgangsRohr(r);
                                        break;
                                    }
                                }
                            }
                            if (p.getEingangsRohr() != null) {
                                for (Rohr r : rohre) {
                                    if (r.getHash().equals(p.getEingangsRohr().getHash())) {
                                        p.setEingangsRohr(r);
                                        break;
                                    }
                                }
                            }
                        }
                        break;
                    }

                }
            }
            n.getNachbarnHash().clear();
        }
    }

    public void saveMap() {
        toHash();
        try {
            Writer writer = new FileWriter("gameMap.json");
            new Gson().toJson(this, writer);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public GameMap loadMap() {
        GameMap map = new GameMap();
        Gson gson = new Gson();
        try {
            JsonReader reader = new JsonReader(new FileReader("gameMap.json"));
            map = gson.fromJson(reader, new TypeToken<GameMap>() {
            }.getType());
            map.fromHash();
        } catch (FileNotFoundException ignored) {
        }
        return map;
    }


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

    //=======================================================================================
    //=======================================================================================
    public List<Rohr> getRohre() {
        return rohre;
    }

    public void setRohre(List<Rohr> rohre) {
        this.rohre = rohre;
    }

    public List<Pumpe> getPumpen() {
        return pumpen;
    }

    public void setPumpen(List<Pumpe> pumpen) {
        this.pumpen = pumpen;
    }

    public List<Wasserquelle> getWasserquellen() {
        return wasserquellen;
    }

    public void setWasserquellen(List<Wasserquelle> wasserquellen) {
        this.wasserquellen = wasserquellen;
    }

    public List<Zisterne> getZisternen() {
        return zisternen;
    }

    public void setZisternen(List<Zisterne> zisternen) {
        this.zisternen = zisternen;
    }
}
