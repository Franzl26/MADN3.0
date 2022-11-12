 package edu.unibw.sse.madn.spielLogik;

public interface SpielErstellen { // Markus
    /**
     * Erstellt ein neues Spiel
     *
     * @param benutzernamen String-Array mit allen im Spiel vertretenen Spielernamen
     * @param bots      Anzahl Bots
     * @param spieler   Anzahl Spieler
     */
    void spielErstellen(String[] benutzernamen, int bots, int spieler);

    void warteraumCallbackSetzen(WarteraumCallback warteraumCallback);
}
