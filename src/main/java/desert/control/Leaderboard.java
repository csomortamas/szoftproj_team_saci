package main.java.desert.control;

import org.tinylog.Logger;

import java.util.ArrayList;
import java.util.Collections;

/**
 *
 */
public class Leaderboard {
    private RekordSpeicher rekordSpeicher = new RekordSpeicher();
    private RekordSpeicher sortedRekordSpeicher = null;

    public Leaderboard() {
        rekordSpeicher = rekordSpeicher.importRekorde();
        sortRekorde();
    }

    public void sortRekorde() {
        sortedRekordSpeicher = new RekordSpeicher();
        //Creating and sorting a list with the points of all the former attempts.
        ArrayList<Integer> punktList = new ArrayList<>();
        for (int i = 1; i <= rekordSpeicher.getRekordAnzahl(); i++) {
            punktList.add(rekordSpeicher.getPunktMap().get(i));
        }
        Collections.sort(punktList);
        Collections.reverse(punktList);

        //Creating list that stores which attempts have already been added to the sorted Hashmap.
        ArrayList<Integer> keyAdded = new ArrayList<>();
        //Creating counter that stores the number of attempts sorted (+1).
        int count = 1;

        //Iterating through the sorted list of points.
        for (int num : punktList) {
            for (int i = 0; i < rekordSpeicher.getRekordAnzahl(); i++) {
                //Finding the attempt that hadn't yet been added with the same points.
                if ((int) rekordSpeicher.getPunktMap().values().toArray()[i] == num && !keyAdded.contains((Integer) rekordSpeicher.getPunktMap().keySet().toArray()[i])) {
                    //Putting the attempt in the sorted HashMap
                    sortedRekordSpeicher.getPunktMap().put(count, num);
                    sortedRekordSpeicher.getNameMap().put(count, (String) rekordSpeicher.getNameMap().values().toArray()[i]);
                    //Adding the key of the attempt to the list of added keys
                    keyAdded.add((int) rekordSpeicher.getPunktMap().keySet().toArray()[i]);
                    count++;
                    //If the attempt is found there is no point of iterating more.
                    break;
                }
            }
        }
        Logger.info("Rekorde sortiert!");
    }

    public void addRekord(String name, Integer punkt) {
        rekordSpeicher.addRekord(name, punkt);
        rekordSpeicher.exportRekorde();
        sortRekorde();
        Logger.info("Rekord hinzugefugt!");
    }

    public void listAll() {
        if (sortedRekordSpeicher != null) {
            System.out.println("\nLEADERBOARD:");
            for (int i = 1; i <= rekordSpeicher.getRekordAnzahl(); i++) {
                System.out.println("Name: " + sortedRekordSpeicher.getNameMap().get(i) + "\t Punkte: " + sortedRekordSpeicher.getPunktMap().get(i));
            }
        } else {
            Logger.error("Leider gibt es noch keine Rekorde in den Leaderboard.");
        }

    }
}