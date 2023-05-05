package main.java.desert.control;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class Rekord {
    @Getter
    @Setter
    private String name;
    @Getter
    @Setter
    private Integer punkt;
    @Getter
    @Setter
    transient private Instant datum;
    @Getter
    @Setter
    private long epochS;

    public Rekord(String name, int punkt, Instant datum) {
        this.name = name;
        this.punkt = punkt;
        this.datum = datum;
    }

    public void serialize() {
        epochS = datum.getEpochSecond();
    }

    public void deserialize() {
        datum = Instant.ofEpochSecond(epochS);
    }

    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm - dd.MM.yyyy").withZone(ZoneId.systemDefault());
        return ("Name: " + name + " Punkte: " + punkt + " Datum: " + formatter.format(datum));
    }
}
