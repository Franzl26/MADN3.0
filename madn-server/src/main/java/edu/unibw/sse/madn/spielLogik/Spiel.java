package edu.unibw.sse.madn.spielLogik;

import edu.unibw.sse.madn.base.SpielStatistik;
import edu.unibw.sse.madn.base.WuerfelnRueckgabe;
import edu.unibw.sse.madn.base.ZiehenRueckgabe;

public interface Spiel {
    ZiehenRueckgabe figurZiehen(String benutzername, int von, int nach);

    WuerfelnRueckgabe wuerfeln(String benutzername);

    SpielStatistik spielVerlassen(String benutzername);

    void anClientSendenSpielSetzen(AnClientSendenSpiel anClientSendenSpiel);
}
