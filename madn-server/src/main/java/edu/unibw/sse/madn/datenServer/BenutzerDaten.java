package edu.unibw.sse.madn.datenServer;

/**
 * Nico
 */
public interface BenutzerDaten {
    /**
     * Speichert einen Benutzer ab
     *
     * @param benutzer alle Benutzer
     */
    void benutzerSpeichern(Benutzer benutzer);

    /**
     * Löscht den Benutzer aus dem Speicher
     *
     * @param benutzer Benutzer
     */
    void benutzerLoeschen(Benutzer benutzer);

    /**
     * Lädt Benutzerdaten
     *
     * @return geladene Benutzer oder null, wenn keine Datei gefunden/Fehler aufgetreten
     */
    Benutzer[] benutzerLaden();
}
