package main.java.desert;
import org.tinylog.Logger;


/**
 * Eintrittspunkt der Programm.
 */
public class Main {
    public static void main(String[] args) {
        // SKELETON TEST

        // INIT
        Kontroller kontroller = new Kontroller(100);
        Wasserquelle wasserquelle = new Wasserquelle();
        Pumpe pumpe1 = new Pumpe(4);
        Pumpe pumpe2 = new Pumpe(4);
        Rohr rohr1 = new Rohr();
        Rohr rohr2 = new Rohr();
        Rohr rohr3 = new Rohr();
    }
}
