package main.java.desert.network;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NetzelementTest {
    @Test
    void kaputtMachen() {
        Rohr r1= new Rohr();
        r1.kaputtMachen();
        Assertions.assertEquals(true, r1.istKaputt);
    }

    @Test
    void reparierenEinKaputtesRohr() {
        Rohr r2= new Rohr();
        r2.kaputtMachen();
        r2.reparieren();
        Assertions.assertEquals(false,r2.istKaputt);
    }
    @Test
    void reparierenEinNichtKaputtesRohr() {
        Rohr r2= new Rohr();
        r2.reparieren();
        Assertions.assertEquals(false,r2.istKaputt);
    }
}