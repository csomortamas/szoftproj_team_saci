package main.java.desert.control;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import lombok.Getter;
import lombok.Setter;
import org.tinylog.Logger;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 *
 */
public class Leaderboard {
    @Getter @Setter private RekordSpeicher rekordSpeicher;
    @Getter @Setter private Map<Integer, Integer> sortedPunktMap = new HashMap<>();
    @Getter @Setter private Map<Integer, String> sortedNameMap = new HashMap<>();

    public Leaderboard(){
        importRekorde();
        sortRekorde();
    }
    public void importRekorde(){
        Gson gson = new Gson ();
        try {
            JsonReader reader = new JsonReader (new FileReader("records.json"));
            rekordSpeicher = gson.fromJson (reader, new TypeToken<RekordSpeicher>() {
            }.getType ());
        } catch (FileNotFoundException e) {
            e.printStackTrace ();
        }
    }
    public void sortRekorde(){
        //Creating and sorting a list with the points of all the former attempts.
        ArrayList<Integer> punktList = new ArrayList<> ();
        for (int i = 1; i <= rekordSpeicher.getRekordAnzahl (); i++) {
            punktList.add (rekordSpeicher.getPunktMap ().get (i));
        }
        Collections.sort (punktList);
        Collections.reverse (punktList);

        //Creating list that stores which attempts have already been added to the sorted Hashmap.
        ArrayList<Integer> keyAdded = new ArrayList<> ();
        //Creating counter that stores the number of attempts sorted (+1).
        int count = 1;

        //Iterating through the sorted list of points.
        for (int num : punktList) {
            for (int i = 0; i < rekordSpeicher.getRekordAnzahl (); i++) {
                //Finding the attempt that hadn't yet been added with the same points.
                if ((int) rekordSpeicher.getPunktMap ().values ().toArray ()[i] == num && !keyAdded.contains ((Integer) rekordSpeicher.getPunktMap ().keySet ().toArray ()[i])) {
                    //Putting the attempt in the sorted HashMap
                    sortedPunktMap.put (count, num);
                    sortedNameMap.put (count, (String) rekordSpeicher.getNameMap ().values ().toArray ()[i]);
                    //Adding the key of the attempt to the list of added keys
                    keyAdded.add ((int) rekordSpeicher.getPunktMap ().keySet ().toArray ()[i]);
                    count++;
                    //If the attempt is found there is no point of iterating more.
                    break;
                }
            }
        }
    }

    public void listAll(){
        for (int i = 1; i < rekordSpeicher.getRekordAnzahl (); i++){
            System.out.println("Name: "+sortedNameMap.get(i)+" Punkt: "+sortedPunktMap.get(i));
        }
    }
}