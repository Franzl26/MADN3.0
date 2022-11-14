package edu.unibw.sse.madn.datenServer;

public interface BenutzerDaten {
    /**
     * Speichert einen Benutzer ab
     *
     * @param benutzer alle Benutzer
     */
    void benutzerSpeichern(Benutzer benutzer);

    /**
     * Lädt Benutzerdaten
     *
     * @return geladene Benutzer oder null, wenn keine Datei gefunden/Fehler aufgetreten
     */
    Benutzer[] benutzerLaden();
}
