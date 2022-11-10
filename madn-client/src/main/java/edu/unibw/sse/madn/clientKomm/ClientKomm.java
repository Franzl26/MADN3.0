package edu.unibw.sse.madn.clientKomm;

import edu.unibw.sse.madn.base.*;

public interface ClientKomm { // Frank
    // Anmelden / Registrieren / Abmelden

    /**
     * Benutzer anmelden
     *
     * @param ip           Server IP
     * @param benutzername Benutzername
     * @param passwort     Passwort verschlüsselt
     * @return erfolgreich: true, sonst false
     */
    AllgemeinerReturnWert anmelden(String ip, String benutzername, String passwort);

    /**
     * Benutzer registrieren
     *
     * @param ip           Server IP
     * @param benutzername Benutzername
     * @param passwort     Passwort RSA verschlüsselt
     * @return entsprechend des Anwendungsfalls
     */
    RegistrierenRueckgabe registrieren(String ip, String benutzername, String passwort);

    /**
     * meldet Client ab, wenn Anwendung geschlossen wird
     */
    void abmelden();


    // Designs

    /**
     * @return List aller verfügbaren Designs/Spielfeld-Konfigurationen oder null bei Fehler
     */
    String[] designListeHolen();

    /**
     * @param name Name des Designs
     * @return die geladene Spielfeld-Konfiguration oder null bei Fehler
     */
    SpielfeldKonfiguration spielfeldKonfigurationHolen(String name);


    // Warteraum

    /**
     * Warteraum erstellen
     *
     * @return Warteraum erstellt: RET_ERFOLGREICH, Verbindungsfehler RET_VERBINDUNG_ABGEBROCHEN, sonst RET_FEHLER
     */
    AllgemeinerReturnWert warteraumErstellen();

    /**
     * Warteraum beitreten
     *
     * @param raumId Raum-ID
     * @return Warteraum beigetreten: RET_ERFOLGREICH, Verbindungsfehler RET_VERBINDUNG_ABGEBROCHEN, sonst RET_FEHLER
     */
    AllgemeinerReturnWert warteraumBeitreten(long raumId);

    /**
     * Warteraum verlassen
     */
    void warteraumVerlassen();

    /**
     * Bot in Warteraum hinzufügen
     *
     * @return Bot hinzugefügt: RET_ERFOLGREICH, Verbindungsfehler RET_VERBINDUNG_ABGEBROCHEN, sonst RET_FEHLER
     */
    AllgemeinerReturnWert botHinzufuegen();

    /**
     * Bot aus Warteraum Entfernen
     *
     * @return Bot entfernt: RET_ERFOLGREICH, Verbindungsfehler RET_VERBINDUNG_ABGEBROCHEN, sonst RET_FEHLER
     */
    AllgemeinerReturnWert botEntfernen();

    /**
     * Spiel aus Warteraum starten
     *
     * @return Spiel gestartet: RET_ERFOLGREICH, Verbindungsfehler RET_VERBINDUNG_ABGEBROCHEN, sonst RET_FEHLER
     */
    AllgemeinerReturnWert spielStarten();

    /**
     * Spieldesign im Warteraum ändern
     *
     * @param design Design
     */
    void designAnpassen(String design);


    // Spiel

    /**
     * Spielzug einreichen
     *
     * @param von  Feld von
     * @param nach Feld nach
     */
    ZiehenRueckgabe figurZiehen(int von, int nach);

    /**
     * Würfeln
     */
    WuerfelnRueckgabe wuerfeln();

    /**
     * ein aktives Spiel Verlassen
     *
     * @return Spielstatistik oder null bei Fehler
     */
    Spielstatistik spielVerlassen();

    /**
     * Setzt für ClientCallback den Callback zur Raumauswahl/dem Warteraum
     *
     * @param update UpdateInterface
     */
    void raumverwaltungUpdaterSetzen(RaumverwaltungUpdaten update);


    /**
     * Setzt für ClientCallback den Callback in den Dialog Spiel
     *
     * @param update UpdateInterface
     */
    void spielUpdaterSetzen(SpielUpdaten update);
}
