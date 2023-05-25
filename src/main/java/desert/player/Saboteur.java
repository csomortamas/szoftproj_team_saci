package desert.player;

import desert.control.Kontroller;
import desert.network.Zisterne;

/**
 *
 */
public class Saboteur extends Spieler {
    /**
     * Default Konstruktor
     */
    public Saboteur(Zisterne startPunkt) {
        super(startPunkt);
        Kontroller.getKontroller().getSaboteurTeam().add(this);
    }
//

    public void rohrLoechern() {
        this.position.kaputtMachen();
    }
}
