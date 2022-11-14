package edu.unibw.sse.madn.benutzerVerwaltung;

import edu.unibw.sse.madn.base.RegistrierenRueckgabe;

public interface BenutzerZugang {
    /**
     * Benutzer anmelden(Domenik)
     *
     * Meldet den Benutzer an, falls benutzername und passwort übereinstimmen,
     * und gibt dabei true zurück und speichert das anmeldedatum des Nutzers
     * Falls der Nutzer bereits angemeldet ist oder passwort oder benutzername
     * nicht übereinstimmen wird false zurückgegeben
     *
     * @param benutzername Benutzername
     * @param passwort     Passwort im Klartext
     * @return true: erfolgreich angemeldet, false: Fehler
     */
    boolean anmelden(String benutzername, String passwort);

    /**
     * Benutzer registrieren(Domenik)
     *
     * Überprüft zunächst ob der benutzername den festgelegten guidelines entspricht (3 bis 8 zeichen[A-Za-z])
     * und ob dieser benutzername bereits vergeben ist.
     * Falls der benutzername nicht den Guidelines entspricht wird "NAME_NICHT_GUIDELINES" zurückgegeben.
     * Ist der benutzername bereits vergeben wird "NAME_BEREITS_VERGEBEN" zurückgegeben.
     * Dann wird das passwort auf seine konformität überprüft (8 bis 15 zeichen mindesten ein Bustabe[A-Za-z], ein Sonderzeichen[!§$%&/()=?#] und eine Ziffer[0-9]).
     * Entspricht das passwort nicht den Guidelines wird "PASSWORT_NICHT_GUIDELINES" zurückgegeben.
     * Wenn alle überprüfungen erfolgreich waren werden die Benutzerdaten gespeicher und es wird "ERFOLGREICH" zurückgegeben.
     *
     * @param benutzername Benutzername
     * @param passwort     Passwort Klartext
     * @return entsprechend dem Anwendungsfall
     */
    RegistrierenRueckgabe registrieren(String benutzername, String passwort);

    /**
     * Benutzer abmelden(Domenik)
     *
     * Meldet den Benutzer ab und markiert ihn als nicht eingeloggt.
     *
     * @param benutzername Benutzername aus Sitzung
     */
    void abmelden(String benutzername);
}
