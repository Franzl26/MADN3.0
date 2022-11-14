package edu.unibw.sse.madn.spielLogik;

import edu.unibw.sse.madn.base.Spielstatistik;
import edu.unibw.sse.madn.base.WuerfelnRueckgabe;
import edu.unibw.sse.madn.base.ZiehenRueckgabe;

public interface Spiel { // Markus
    /**
     * Spielzug einreichen
     *
     * Überprüfen ob der Parameter benutzername == aktiver Spieler entspricht. Wenn 'false' dann ret(NICHT_DRAN), sonst
     * wird überprüft ob Spiel.spielzugstatus == Spielzug.figurBeween. Trifft das nicht zu ret(NICHT_GEWUERFELT), sonst wird
     * die Bewegung der Spielfigur vom 'int von' zu 'int nach' überprüft. Wird falsch gezogen oder rausrücken nicht beachtet dann
     * ret(ZUG_FEHLERHAFT), sonst wird im Anschluss die Spielstatistik aktualisiert. Abschließend wird überprüft ob die Priorität
     * des Schlagens beachtet wurde. Trifft das zu dann ret(ERFOLGREICH), sonst ret(BESTRAFT).
     *
     * @param benutzername  Name des Spielers der gewürfelt hat
     * @param von           Feld von dem die Spielfigur bewegt wurde
     * @param nach          Feld zum dem die Spielfigur bewegt wurde
     * @return ZiehenRueckgabe
     */
    ZiehenRueckgabe figurZiehen(String benutzername, int von, int nach);

    /**
     * Würfeln
     *
     * Überprüfen ob der Parameter benutzername == aktiver Spieler entspricht. Wenn 'false' dann ret(NICHT_DRAN), sonst
     * wird überprüft ob gewürfelt werden darf (Spiel.spielzugstatus == würfeln && Spiel.würfelzahl > 0). Wenn 'false'
     * dann ret(FALSCHE_PHASE oder KEIN_ZUG_MOEGLICH), sonst wird gewürfelt. Das Ergebnis des Würfelns wird überprüft. Ist
     * damit ein Spielzug möglich, dann ret(ERFOLGREICH) wenn nicht dann ret(KEIN_ZUG_MOEGLICH)
     *
     * @param benutzername  Name des Spielers der gewürfelt hat
     * @return WuerfelnRueckgabe    Enum-Wert zur Statusangabe nach der Aktion des Würfelns
     */
    WuerfelnRueckgabe wuerfeln(String benutzername);

    /**
     * Spiel Verlassen
     *
     * Es wird überprüft, ob der verlassende Spieler letzer echter Spieler ist, wenn nein wir überprüft ob der Spieler am
     * Zug ist. Trifft das zu wird figurZiehen() von einem Bot ausgeführt. Ansonsten wird dann die Spielstatistik angepasst.
     * Spieler.name = [neuerBotName];
     * Spieler.bot = true;
     * Spiel.botanzahl += 1;
     * (die restlichen Werte können gleich bleiben)
     *
     * Verlässt der letzte echte Spieler das Spiel wird die Spielstatistik aller Bots und des verlassenden Spielers an aller
     * Spieler gesendet und das Spiel beendet ?? (AF_SpielBeenden)
     *
     * @param benutzername  Name des zu verlassendes Spielers
     * @return aktualisierte Spielstatistik wenn erfolgreich bzw. Null bei Fehler
     */
    Spielstatistik spielVerlassen(String benutzername);

    void anClientSendenSpielSetzen(AnClientSendenSpiel anClientSendenSpiel);
}
