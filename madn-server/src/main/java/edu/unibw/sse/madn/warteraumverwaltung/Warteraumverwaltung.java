package edu.unibw.sse.madn.warteraumverwaltung;

import edu.unibw.sse.madn.spielLogik.SpielErstellen;

public interface Warteraumverwaltung {
    Raumauswahl raumauswahlHolen();

    void spielErstellenSetzen(SpielErstellen spielErstellen);
}
