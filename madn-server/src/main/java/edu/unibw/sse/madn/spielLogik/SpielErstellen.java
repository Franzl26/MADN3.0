package edu.unibw.sse.madn.spielLogik;

/**
 * Markus
 */
public interface SpielErstellen {
    /**
     * Erstellt ein neues Spiel
     *
     *Zusammenfassen und Speichern aller relevanten Informationen die benötigt werden um ein neues Spiel zu starten
     * Zu beginn muss überprüft werden, ob die Spieleranzahl in einem Warteraum, inklusive Bots 2, 3 oder 4 Spieler beträgt.
     * Trifft das nicht zu, erfolgt keine Aktion, ansonsten wird Spiel.startzeitpunkt gespeichert und die Initialwerte für die
     * Spiel Spieler und Feld geladen und gesetzt (siehe AF_SpielStarten).
     *
     * @param benutzernamen String-Array mit allen im Spiel vertretenen Spielernamen
     * @param bots          Anzahl Bots
     * @param spieler       Anzahl Spieler
     */
    void spielErstellen(String[] benutzernamen, int bots, int spieler);

    void warteraumCallbackSetzen(WarteraumCallback warteraumCallback);
}
