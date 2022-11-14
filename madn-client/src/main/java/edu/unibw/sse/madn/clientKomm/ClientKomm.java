package edu.unibw.sse.madn.clientKomm;

import edu.unibw.sse.madn.base.*;

public interface ClientKomm { // Frank
    // Anmelden / Registrieren / Abmelden

    /**
     * Benutzer anmelden (AF Benutzer Anmelden)
     *
     * @param ip           Server IP
     * @param benutzername Benutzername
     * @param passwort     Passwort im Klartext
     * @return ERFOLGREICH: wenn angemeldet <br>
     * FEHLER: wenn Benutzername nicht vorhanden/schon angemeldet/PW falsch<br>
     * VERBINDUNG_ABGEBROCHEN: wenn Server nicht gefunden oder Fehler bei der Kommunikation/Verschlüsselung
     */
    AllgemeinerReturnWert anmelden(String ip, String benutzername, String passwort);

    /**
     * Benutzer registrieren (AF Registrieren)
     *
     * @param ip           Server IP
     * @param benutzername Benutzername
     * @param passwort     Passwort im Klartext
     * @return entsprechend des Anwendungsfalls,
     * "Passwörter nicht gleich" muss von der Ansicht-Komponente selber geprüft werden
     */
    RegistrierenRueckgabe registrieren(String ip, String benutzername, String passwort);

    /**
     * meldet Client ab, wenn Anwendung geschlossen wird
     */
    void abmelden();


    // Designs

    /**
     * @return Liste der Namen aller verfügbaren Designs/Spielfeld-Konfigurationen oder null bei Fehler
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
     * @return ERFOLGREICH: Warteraum wurde erstellt <br>
     * FEHLER: maximale Warteraumanzahl erreicht <br>
     * VERBINDUNG_ABGEBROCHEN: Fehler bei der Kommunikation
     */
    AllgemeinerReturnWert warteraumBeitreten(long raumId);

    /**
     * Warteraum verlassen
     *
     * @param raumId ID des Warteraums, dem der Nutzer beigetreten ist
     */
    void warteraumVerlassen(long raumId);

    /**
     * Bot in Warteraum hinzufügen
     *
     * @param raumId ID des Warteraums, dem der Nutzer beigetreten ist
     * @return ERFOLGREICH: Bot hinzugefügt <br>
     * FEHLER: Warteraum schon voll/nicht in Warteraum <br>
     * VERBINDUNG_ABGEBROCHEN: Fehler bei der Kommunikation
     */
    AllgemeinerReturnWert botHinzufuegen(long raumId);

    /**
     * Bot aus Warteraum Entfernen
     *
     * @param raumId ID des Warteraums, dem der Nutzer beigetreten ist
     * @return ERFOLGREICH: Bot entfernt <br>
     * FEHLER: kein Bot mehr im Warteraum/nicht in Warteraum <br>
     * VERBINDUNG_ABGEBROCHEN: Fehler bei der Kommunikation
     */
    AllgemeinerReturnWert botEntfernen(long raumId);

    /**
     * Spiel aus Warteraum starten
     *
     * @param raumId ID des Warteraums, dem der Nutzer beigetreten ist
     * @return ERFOLGREICH: Spiel wurde gestartet <br>
     * FEHLER: nicht genügend Spieler in Warteraum <br>
     * VERBINDUNG_ABGEBROCHEN: Fehler bei der Kommunikation
     */
    AllgemeinerReturnWert spielStarten(long raumId);

    /**
     * Spieldesign im Warteraum ändern
     *
     * @param design Design, dass gesetzt werden soll
     * @param raumId ID des Warteraums, dem der Nutzer beigetreten ist
     */
    void designAnpassen(String design, long raumId);


    // Spiel

    /**
     * Spielzug einreichen
     *
     * @param von  Feld von
     * @param nach Feld nach
     * @return entsprechend dem Anwendungsfall
     */
    ZiehenRueckgabe figurZiehen(int von, int nach);

    /**
     * Würfeln
     *
     * @return entsprechend dem Anwendungsfall
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
