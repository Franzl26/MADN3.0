package edu.unibw.sse.madn.warteraumverwaltung;

import edu.unibw.sse.madn.komm.Sitzung;
import edu.unibw.sse.madn.serverKomm.AnClientSendenRaumauswahl;

public interface Raumauswahl {
    void fuerUpdatesAnmelden(Sitzung sitzung);

    boolean warteraumErstellen(Sitzung sitzung);

    boolean warteraumBeitreten(Sitzung sitzung, long raumId);

    void warteraumVerlassen(Sitzung sitzung);

    boolean botHinzufuegen(Sitzung sitzung);

    boolean botEntfernen(Sitzung sitzung);

    boolean spielStarten(Sitzung sitzung);

    void designAnpassen(Sitzung sitzung, String design);

    void anClientSendenRaumauswahlSetzen(AnClientSendenRaumauswahl anClientSendenRaumauswahl);
}
