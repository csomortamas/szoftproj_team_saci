package desert.network;

import javafx.scene.image.ImageView;

public class Zisterne extends Pumpe {
    private ImageView readyPumpImage;

    private Pumpe pumpeZurVerfuegung;

    public Zisterne(double x, double y) {
        super(x, y);
    }

    //=============================GETTERS & SETTERS=========================================
    //=======================================================================================

    public Pumpe getPumpeZurVerfuegung() {
        return pumpeZurVerfuegung;
    }

    public void setPumpeZurVerfuegung(Pumpe pumpeZurVerfuegung) {
        this.pumpeZurVerfuegung = pumpeZurVerfuegung;
    }

    public ImageView getReadyPumpImage() {
        return readyPumpImage;
    }

    public void setReadyPumpImage(ImageView readyPumpImage) {
        this.readyPumpImage = readyPumpImage;
    }
}
