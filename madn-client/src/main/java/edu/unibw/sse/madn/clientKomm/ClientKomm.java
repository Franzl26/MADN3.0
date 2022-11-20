package edu.unibw.sse.madn.clientKomm;

import edu.unibw.sse.madn.base.*;

/**
 * Frank
 */
public interface ClientKomm {
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
    SpielfeldKonfiguration spielfeldKonfigurationHolen(String name, boolean sechser);


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
     */
    void warteraumVerlassen();

    /**
     * Bot in Warteraum hinzufügen
     *
     * @return ERFOLGREICH: Bot hinzugefügt <br>
     * FEHLER: Warteraum schon voll/nicht in Warteraum <br>
     * VERBINDUNG_ABGEBROCHEN: Fehler bei der Kommunikation
     */
    AllgemeinerReturnWert botHinzufuegen();

    /**
     * Bot aus Warteraum Entfernen
     *
     * @return ERFOLGREICH: Bot entfernt <br>
     * FEHLER: kein Bot mehr im Warteraum/nicht in Warteraum <br>
     * VERBINDUNG_ABGEBROCHEN: Fehler bei der Kommunikation
     */
    AllgemeinerReturnWert botEntfernen();

    /**
     * Spiel aus Warteraum starten
     *
     * @return ERFOLGREICH: Spiel wurde gestartet <br>
     * FEHLER: nicht genügend Spieler in Warteraum <br>
     * VERBINDUNG_ABGEBROCHEN: Fehler bei der Kommunikation
     */
    AllgemeinerReturnWert spielStarten();


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

    /**
     * Sendet eine Nachricht im Chat in Warteraum/Spiel
     *
     * @param nachricht Nachricht die gesendet werden soll
     * @return AllgemeinerReturnWert
     */
    AllgemeinerReturnWert nachrichtSenden(String nachricht);
}
