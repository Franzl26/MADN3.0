package edu.unibw.sse.madn.spielLogik;

public interface SpielErstellen {
    /**
     * Erstellt ein neues Spiel
     *
     * @param benutzernamen
     * @param bots      Anzahl Bots
     * @param spieler   Anzahl Spieler
     */
    void spielErstellen(String[] benutzernamen, int bots, int spieler);

    void warteraumCallbackSetzen(WarteraumCallback warteraumCallback);
}
