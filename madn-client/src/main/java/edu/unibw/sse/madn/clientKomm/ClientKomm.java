package edu.unibw.sse.madn.clientKomm;

import edu.unibw.sse.madn.ansicht.RaumauswahlUpdaten;
import edu.unibw.sse.madn.ansicht.SpielUpdaten;
import edu.unibw.sse.madn.ansicht.WarteraumUpdaten;
import edu.unibw.sse.madn.base.SpielStatistik;
import edu.unibw.sse.madn.base.SpielfeldKonfigurationBytes;
import edu.unibw.sse.madn.komm.RegistrierenRueckgabe;
import edu.unibw.sse.madn.komm.WuerfelnRueckgabe;
import edu.unibw.sse.madn.komm.ZiehenRueckgabe;

public interface ClientKomm {
    String benutzernamenHolen();

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

    void raumauswahlUpdaterSetzen(RaumauswahlUpdaten update);

    void warteraumUpdaterSetzen(WarteraumUpdaten update);

    void spielUpdaterSetzen(SpielUpdaten update);
}
