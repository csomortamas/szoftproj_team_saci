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
    @Getter private Integer currentID=1;
    @Getter private Integer rekordAnzahl=0;
    @Getter private Map<Integer, Integer> punktMap = new HashMap<>();
    @Getter private Map<Integer, String> nameMap = new HashMap<>();

    public RekordSpeicher(){

    }

    public void addRekord(String name,Integer punkt){
        punktMap.put(currentID,punkt);
        nameMap.put(currentID,name);
        currentID++;
        rekordAnzahl++;
        exportRekorde();
    }

    public void exportRekorde(){
        try {
            Writer writer = new FileWriter("records.json");
            new Gson().toJson (this, writer);
            writer.close ();
        } catch (IOException e) {
            throw new RuntimeException (e);
        }
    }
}
