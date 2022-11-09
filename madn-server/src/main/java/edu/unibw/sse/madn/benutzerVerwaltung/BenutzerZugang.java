package edu.unibw.sse.madn.benutzerVerwaltung;

import edu.unibw.sse.madn.base.RegistrierenRueckgabe;

public interface BenutzerZugang {
    boolean anmelden(String benutzername, String passwort);

    RegistrierenRueckgabe registrieren(String benutzername, String pw1);

    void abmelden(String benutzername);
}
