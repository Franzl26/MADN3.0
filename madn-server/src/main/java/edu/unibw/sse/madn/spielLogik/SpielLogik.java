package edu.unibw.sse.madn.spielLogik;

import edu.unibw.sse.madn.serverKomm.AnClientSendenSpiel;

public interface SpielLogik {
    Spiel spielHolen();

    SpielErstellen spielErstellenHolen();

    void anClientSendenSpielSetzen(AnClientSendenSpiel anClientSendenSpiel);
}
