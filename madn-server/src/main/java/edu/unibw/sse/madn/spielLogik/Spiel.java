package edu.unibw.sse.madn.spielLogik;

import edu.unibw.sse.madn.base.SpielStatistik;
import edu.unibw.sse.madn.base.WuerfelnRueckgabe;
import edu.unibw.sse.madn.base.ZiehenRueckgabe;
import edu.unibw.sse.madn.komm.Sitzung;

public interface Spiel {
    ZiehenRueckgabe figurZiehen(Sitzung sitzung, int von, int nach);

    WuerfelnRueckgabe wuerfeln(Sitzung sitzung);

    SpielStatistik spielVerlassen(Sitzung sitzung);

    void anClientSendenSpielSetzen(AnClientSendenSpiel anClientSendenSpiel);
}
