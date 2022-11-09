package edu.unibw.sse.madn.spielLogik;

import edu.unibw.sse.madn.serverKomm.AnClientSendenSpiel;
import edu.unibw.sse.madn.warteraumverwaltung.WarteraumCallback;

public interface SpielLogik {
    Spiel spielHolen();

    SpielErstellen spielErstellenHolen();
}
