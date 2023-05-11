package desert.control;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class Rekord {

    private String name;

    private Integer punkt;

    transient private Instant datum;

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

    //=======================================================================================
    //=======================================================================================

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPunkt() {
        return punkt;
    }

    public void setPunkt(Integer punkt) {
        this.punkt = punkt;
    }

    public Instant getDatum() {
        return datum;
    }

    public void setDatum(Instant datum) {
        this.datum = datum;
    }

    public long getEpochS() {
        return epochS;
    }

    public void setEpochS(long epochS) {
        this.epochS = epochS;
    }
}
