package desert.network;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


class NetzelementTest {
    @Test
    void kaputtMachen() {
        Rohr r1= new Rohr();
        r1.kaputtMachen();
        Assertions.assertTrue(r1.istKaputt);
    }

    @Test
    void reparierenEinKaputtesRohr() {
        Rohr r2= new Rohr();
        r2.kaputtMachen();
        r2.reparieren();
        Assertions.assertFalse(r2.istKaputt);
    }
    @Test
    void reparierenEinNichtKaputtesRohr() {
        Rohr r2= new Rohr();
        r2.reparieren();
        Assertions.assertFalse(r2.istKaputt);
    }
}