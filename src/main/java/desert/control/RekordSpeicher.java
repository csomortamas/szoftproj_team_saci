package main.java.desert.control;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

import java.time.Instant;
import lombok.Getter;
import java.io.*;
import java.util.*;

public class RekordSpeicher {
    @Getter
    private List<Rekord> rekorde = new ArrayList<>();

    public RekordSpeicher() {

    }

    public void addRekord(String name, Integer punkt) {
        rekorde.add(new Rekord(name, punkt, Instant.now()));
        exportRekorde();
    }

    public void exportRekorde() {
        try {
            for (Rekord r : rekorde) {
                r.serialize();
            }
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
        for (Rekord r : rekordSpeicher.rekorde) {
            r.deserialize();
        }
        return rekordSpeicher;
    }

    public int getRekordAnzahl() {
        return rekorde.size();
    }
}
