package main.java.desert.control;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import lombok.Getter;
import lombok.Setter;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class RekordSpeicher {
    @Getter
    private final Map<Integer, Integer> punktMap = new HashMap<>();
    @Getter
    private final Map<Integer, String> nameMap = new HashMap<>();

    public RekordSpeicher() {

    }

    public void addRekord(String name, Integer punkt) {
        punktMap.put((punktMap.size() + 1), punkt);
        nameMap.put((nameMap.size() + 1), name);
        exportRekorde();
    }

    public void exportRekorde() {
        try {
            Writer writer = new FileWriter("records.json");
            new Gson().toJson(this, writer);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public RekordSpeicher importRekorde() {
        RekordSpeicher rekordSpeicher = new RekordSpeicher();
        Gson gson = new Gson();
        try {
            JsonReader reader = new JsonReader(new FileReader("records.json"));
            rekordSpeicher = gson.fromJson(reader, new TypeToken<RekordSpeicher>() {
            }.getType());
        } catch (FileNotFoundException ignored) {

        }
        return rekordSpeicher;
    }

    public int getRekordAnzahl() {
        return punktMap.size();
    }
}
