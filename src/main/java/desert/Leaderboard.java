package main.java.desert;
import lombok.Getter;
import lombok.Setter;
import org.tinylog.Logger;
import java.util.Map;

/**
 *
 */
public class Leaderboard {
    /**
     *
     */
    @Getter @Setter private String spielendeTeam1;

    /**
     *
     */
    @Getter @Setter private String spielendeTeam2;

    /**
     *
     */
    @Getter @Setter private int punkteTeam1;

    /**
     *
     */
    @Getter @Setter private int punkteTeam2;

    /**
     *
     */
    @Getter @Setter private Map daten;

    /**
     *
     */
    public void fileSchreiben() {
        // TODO implement here
    }

    /**
     *
     */
    public void fileLesen() {
        // TODO implement here
    }

    /**
     * Constructor
     */
    public Leaderboard(String team1Name, String team2Name) {
        spielendeTeam1 = team1Name;
        spielendeTeam2 = team2Name;

        punkteTeam1 = 0;
        punkteTeam2 = 0;
    }
}