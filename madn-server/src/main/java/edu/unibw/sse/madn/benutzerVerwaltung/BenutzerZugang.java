package edu.unibw.sse.madn.benutzerVerwaltung;

import edu.unibw.sse.madn.base.RegistrierenRueckgabe;

public interface BenutzerZugang {
    /**
     * Benutzer anmelden
     *
     * @param benutzername Benutzername
     * @param passwort     Passwort im Klartext
     * @return true: erfolgreich angemeldet, false: Fehler
     */
    boolean anmelden(String benutzername, String passwort);

    /**
     * Benutzer registrieren
     *
     * @param benutzername Benutzername
     * @param passwort     Passwort Klartext
     * @return entsprechend des Anwendungsfalls
     */
    RegistrierenRueckgabe registrieren(String benutzername, String passwort);

    /**
     * meldet Client ab
     *
     * @param benutzername Benutzername aus Sitzung
     */
    void abmelden(String benutzername);
}
