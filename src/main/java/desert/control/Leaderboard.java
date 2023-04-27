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
    @Getter @Setter private RekordSpeicher rekordSpeicher=new RekordSpeicher();
    @Getter @Setter private RekordSpeicher sortedRekordSpeicher=new RekordSpeicher();
    //@Getter @Setter private Map<Integer, Integer> sortedPunktMap = new HashMap<>(); //<ID,Punkt>
    //@Getter @Setter private Map<Integer, String> sortedNameMap = new HashMap<>(); //<ID,Name>

    public Leaderboard(){
        rekordSpeicher = rekordSpeicher.importRekorde();
        sortRekorde();
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
                    sortedRekordSpeicher.getPunktMap().put (count, num);
                    sortedRekordSpeicher.getNameMap().put (count, (String) rekordSpeicher.getNameMap ().values ().toArray ()[i]);
                    //Adding the key of the attempt to the list of added keys
                    keyAdded.add ((int) rekordSpeicher.getPunktMap ().keySet ().toArray ()[i]);
                    count++;
                    //If the attempt is found there is no point of iterating more.
                    break;
                }
            }
        }
    }

    public void addRekord(String name,Integer punkt){
        rekordSpeicher.addRekord(name,punkt);
        rekordSpeicher.exportRekorde();
        sortRekorde();
        Logger.info("Rekord hinzugefugt!");
    }

    public void listAll(){
        System.out.println("\nLEADERBOARD:");
        for (int i = 1; i <= rekordSpeicher.getRekordAnzahl(); i++){
            System.out.println("Name: "+sortedRekordSpeicher.getNameMap().get(i)+"\t Punkte: "+sortedRekordSpeicher.getPunktMap().get(i));
        }
    }
}