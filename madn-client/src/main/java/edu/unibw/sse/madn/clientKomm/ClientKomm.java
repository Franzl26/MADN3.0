package edu.unibw.sse.madn.clientKomm;

import edu.unibw.sse.madn.base.*;

public interface ClientKomm {
    // Anmelden / Registrieren / Abmelden
    AllgemeinerReturnWert anmelden(String ip, String benutzername, String passwort);

    RegistrierenRueckgabe registrieren(String ip, String benutzername, String pw1, String pw2);

    void abmelden();

    // Designs
    String[] designListeHolen();

    SpielfeldKonfigurationBytes spielfeldKonfigurationHolen(String name);

    // Warteraum
    AllgemeinerReturnWert fuerWarteraumUpdatesAnmelden();

    AllgemeinerReturnWert warteraumErstellen();

    AllgemeinerReturnWert warteraumBeitreten(long raumId);

    void warteraumVerlassen();

    AllgemeinerReturnWert botHinzufuegen();

    AllgemeinerReturnWert botEntfernen();

    AllgemeinerReturnWert spielStarten();

    void designAnpassen(String design);

    // Spiel
    ZiehenRueckgabe figurZiehen(int von, int nach);

    WuerfelnRueckgabe wuerfeln();

    SpielStatistik spielVerlassen();

    void raumauswahlUpdaterSetzen(RaumverwaltungUpdaten update);

    void spielUpdaterSetzen(SpielUpdaten update);
}
