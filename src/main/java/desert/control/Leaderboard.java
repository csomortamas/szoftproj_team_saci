package main.java.desert.control;

import org.tinylog.Logger;


/**
 *
 */
public class Leaderboard {
    private RekordSpeicher rekordSpeicher = new RekordSpeicher();

    public Leaderboard() {
        rekordSpeicher = rekordSpeicher.importRekorde();
        sortRekorde();
    }

    public void sortRekorde() {
        rekordSpeicher.getRekorde().sort((o1, o2)
                -> (o2.getPunkt()).compareTo(o1.getPunkt()));

        Logger.info("Rekorde sortiert!");
    }

    public void addRekord(String name, Integer punkt) {
        rekordSpeicher.addRekord(name, punkt);
        sortRekorde();
        rekordSpeicher.exportRekorde();
        Logger.info("Rekord hinzugefugt!");
    }

    public void listAll() {
        if (rekordSpeicher != null) {
            System.out.println("\033[1;35m\nLEADERBOARD:\033[0m");
            for (int i = 0; i < rekordSpeicher.getRekordAnzahl(); i++) {
                System.out.println(rekordSpeicher.getRekorde().get(i).toString());
            }
        } else {
            Logger.error("Leider gibt es noch keine Rekorde in den Leaderboard.");
        }

    }
}