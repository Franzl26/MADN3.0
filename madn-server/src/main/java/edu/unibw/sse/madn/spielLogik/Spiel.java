package edu.unibw.sse.madn.spielLogik;

import edu.unibw.sse.madn.base.Spielstatistik;
import edu.unibw.sse.madn.base.WuerfelnRueckgabe;
import edu.unibw.sse.madn.base.ZiehenRueckgabe;

public interface Spiel { // Markus
    /**
     * Spielzug einreichen
     *
     * @param benutzername
     * @param von     Feld von
     * @param nach    Feld nach
     * @return ZiehenRueckgaeb
     */
    ZiehenRueckgabe figurZiehen(String benutzername, int von, int nach);

    /**
     * Würfeln
     *
     * @param benutzername      Name des Spielers der gewürfelt hat
     * @return WuerfelnRueckgabe    Ergebniss des Würfelns
     */
    WuerfelnRueckgabe wuerfeln(String benutzername);

    /**
     * Spiel Verlassen
     *
     * @param benutzername
     * @return Spielstatistik oder Null bei Fehler
     */
    Spielstatistik spielVerlassen(String benutzername);

    void anClientSendenSpielSetzen(AnClientSendenSpiel anClientSendenSpiel);
}
