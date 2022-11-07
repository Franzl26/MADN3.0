package edu.unibw.sse.madn.benutzerVerwaltung;

import edu.unibw.sse.madn.datenServer.BenutzerDaten;

public interface BenutzerVerwaltung {
    BenutzerZugang benutzerZugangHolen();

    void benutzerDatenSetzen(BenutzerDaten benutzerDaten);
}
