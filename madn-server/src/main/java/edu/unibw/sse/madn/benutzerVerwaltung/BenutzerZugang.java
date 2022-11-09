package edu.unibw.sse.madn.benutzerVerwaltung;

import edu.unibw.sse.madn.base.RegistrierenRueckgabe;

public interface BenutzerZugang {
    boolean anmelden(String benutzername, String passwort);

    RegistrierenRueckgabe registrieren(String benutzername, String pw1, String pw2);

    void abmelden(String benutzername);
}
