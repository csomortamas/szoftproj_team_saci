package main.java.desert;
import org.tinylog.Logger;
/**
 *
 */
public class Saboteur extends Spieler {
    /**
     * Default Konstruktor
     */
    public Saboteur(Netzelement startPunkt, Kontroller kontroller) {
        super(startPunkt, kontroller);

    }

    /**
     *
     */
    public void rohrLochern() {
        if(aktuelleRohr!=null){
            if (aktuelleRohr.isIstKaputt()==false){
                aktuelleRohr.setIstKaputt(true);
                Logger.info("Rohr sabotiert!");
            }else{
                Logger.error("Der Rohr ist schon kaputt!");
            }
        }else{
            Logger.error("Der Spieler steht derzeit nicht auf eine Rohr!");
        }
    }
}
