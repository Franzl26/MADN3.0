package edu.unibw.sse.madn.spielLogik;

import edu.unibw.sse.madn.komm.Sitzung;
import edu.unibw.sse.madn.warteraumverwaltung.WarteraumCallback;

public interface SpielErstellen {
    void spielErstellen(WarteraumCallback warteraum, Sitzung[] sitzungen, int bots, int spieler);
}
