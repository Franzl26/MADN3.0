package edu.unibw.sse.madn.datenServer;

public interface BenutzerDaten {
    void benutzerSpeichern(Benutzer benutzer);

    Benutzer benutzerLaden();
}
