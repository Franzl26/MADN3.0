package edu.unibw.sse.madn.spielLogik;

import edu.unibw.sse.madn.base.Spielstatistik;
import edu.unibw.sse.madn.base.WuerfelnRueckgabe;
import edu.unibw.sse.madn.base.ZiehenRueckgabe;

public interface Spiel {
    /**
     * Spielzug einreichen
     *
     * @param benutzername
     * @param von     Feld von
     * @param nach    Feld nach
     */
    ZiehenRueckgabe figurZiehen(String benutzername, int von, int nach);

    /**
     * Würfeln
     *
     * @param benutzername
     */
    WuerfelnRueckgabe wuerfeln(String benutzername);

    /**
     * Spiel Verlassen
     *
     * @param benutzername
     * @return Spielstatistik oder null bei Fehler
     */
    Spielstatistik spielVerlassen(String benutzername);

    void anClientSendenSpielSetzen(AnClientSendenSpiel anClientSendenSpiel);
}
