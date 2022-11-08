package edu.unibw.sse.madn.warteraumverwaltung;

import edu.unibw.sse.madn.serverKomm.AnClientSendenRaumauswahl;
import edu.unibw.sse.madn.spielLogik.SpielErstellen;

public interface Warteraumverwaltung {
    Warteraumverwaltung warteraumverwaltungHolen();

    WarteraumCallback warteraumCallbackHolen();

    void spielErstellenSetzen(SpielErstellen spielErstellen);

    void anClientSendenRaumauswahlSetzen(AnClientSendenRaumauswahl anClientSendenRaumauswahl);
}
