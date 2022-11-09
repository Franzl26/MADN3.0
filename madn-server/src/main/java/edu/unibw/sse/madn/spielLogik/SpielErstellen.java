package edu.unibw.sse.madn.spielLogik;

import edu.unibw.sse.madn.komm.Sitzung;
import edu.unibw.sse.madn.warteraumverwaltung.WarteraumCallback;

public interface SpielErstellen {
    void spielErstellen(Sitzung[] sitzungen, int bots, int spieler);

    void warteraumCallbackSetzen(WarteraumCallback warteraumCallback);
}
