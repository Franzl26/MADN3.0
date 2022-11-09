package edu.unibw.sse.madn.warteraumverwaltung;

public interface Raumauswahl {
    /**
     * meldet Client für die aktualisierung der Warteräume an
     *
     * @param benutzername
     */
    void fuerUpdatesAnmelden(String benutzername);

    /**
     * Warteraum erstellen
     *
     * @param benutzername
     * @return Warteraum erstellt: true, sonst false
     */
    boolean warteraumErstellen(String benutzername);

    /**
     * Warteraum beitreten
     *
     * @param benutzername
     * @param raumId  Raum-ID
     * @return Warteraum beigetreten: true, sonst false
     */
    boolean warteraumBeitreten(String benutzername, long raumId);

    /**
     * Warteraum verlassen
     *
     * @param benutzername
     */
    void warteraumVerlassen(String benutzername);

    /**
     * Bot hinzufügen
     *
     * @param benutzername
     * @return Bot hinzugefügt: true, sonst false
     */
    boolean botHinzufuegen(String benutzername);

    /**
     * Bot Entfernen
     *
     * @param benutzername
     * @return Bot entfernt: true, sonst false
     */
    boolean botEntfernen(String benutzername);

    /**
     * Spiel starten
     *
     * @param benutzername
     * @return Spiel gestartet: true, sonst false
     */
    boolean spielStarten(String benutzername);

    /**
     * Spieldesign ändern
     *
     * @param benutzername
     * @param design  Design
     */
    void designAnpassen(String benutzername, String design);
    void anClientSendenRaumauswahlSetzen(AnClientSendenRaumauswahl anClientSendenRaumauswahl);
}
