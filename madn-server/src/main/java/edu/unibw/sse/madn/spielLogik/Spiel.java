package edu.unibw.sse.madn.spielLogik;

import edu.unibw.sse.madn.base.SpielStatistik;
import edu.unibw.sse.madn.komm.WuerfelnRueckgabe;
import edu.unibw.sse.madn.komm.ZiehenRueckgabe;
import edu.unibw.sse.madn.komm.Sitzung;
import edu.unibw.sse.madn.serverKomm.AnClientSendenSpiel;

public interface Spiel {
    ZiehenRueckgabe figurZiehen(Sitzung sitzung, int von, int nach);

    WuerfelnRueckgabe wuerfeln(Sitzung sitzung);

    SpielStatistik spielVerlassen(Sitzung sitzung);

    void anClientSendenSpielSetzen(AnClientSendenSpiel anClientSendenSpiel);
}
