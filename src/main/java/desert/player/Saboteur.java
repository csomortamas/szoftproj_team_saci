package desert.player;

import desert.network.Zisterne;
import desert.player.Spieler;

/**
 *
 */
public class Saboteur extends Spieler {
    /**
     * Default Konstruktor
     */
    public Saboteur(Zisterne startPunkt) {
        super(startPunkt);
    }
//

    public void rohrLoechern() {
        this.position.kaputtMachen();
    }
}
