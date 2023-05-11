package desert.network;

/**
 *
 */
public class Zisterne extends Pumpe {
    /**
     *
     */
    private Pumpe pumpeZurVerfuegung;

    /**
     * Default Konstruktor
     *
     * @param x
     * @param y
     */
    public Zisterne(double x, double y) {
        super(x, y);
    }
    //=======================================================================================
    //=======================================================================================

    public Pumpe getPumpeZurVerfuegung() {
        return pumpeZurVerfuegung;
    }

    public void setPumpeZurVerfuegung(Pumpe pumpeZurVerfuegung) {
        this.pumpeZurVerfuegung = pumpeZurVerfuegung;
    }
}
