package edu.unibw.sse.madn.spielLogik;

import edu.unibw.sse.madn.base.FeldBesetztStatus;
import edu.unibw.sse.madn.base.Spielstatistik;

public interface AnClientSendenSpiel {
    /**
     * Spielfeld aktualisieren
     *
     * @param benutzername Name des Clients der informiert werden soll
     * @param feld         Feld
     * @param geandert     array der geänderten Felder, wenn null komplett neu zeichnen
     */
    void spielfeldUpdaten(String benutzername, FeldBesetztStatus[] feld, int[] geandert);

    /**
     * Namen der Spieler übermitteln
     *
     * @param benutzername Name des Clients der informiert werden soll
     * @param namen        Namen
     */
    void spielNamenUpdaten(String benutzername, String[] namen);

    /**
     * aktuellen Spieler setzen
     *
     * @param benutzername Name des Clients der informiert werden soll
     * @param spieler      Spieler
     */
    void aktuellenSpielerSetzen(String benutzername, int spieler);

    /**
     * Würfelwert übermitteln
     *
     * @param benutzername Name des Clients der informiert werden soll
     * @param wert         Würfelwert
     */
    void wuerfelUpdaten(String benutzername, int wert);

    /**
     * Anzeigen, dass Zeit fürs Würfeln abgelaufen
     *
     * @param benutzername Name des Clients der informiert werden soll
     */
    void wuerfelnVorbei(String benutzername);

    /**
     * Anzeigen, dass Zeit fürs Ziehen abgelaufen
     *
     * @param benutzername Name des Clients der informiert werden soll
     */
    void ziehenVorbei(String benutzername);

    /**
     * Gif Anzeigen, wenn Zeit für Ziehen fortgeschritten
     *
     * @param benutzername Name des Clients der informiert werden soll
     */
    void gifAnzeigen(String benutzername);

    /**
     * Teilt Client mit, dass Spiel vorbei und übermittelt ihm Spielstatistik
     *
     * @param benutzername Name des Clients der informiert werden soll
     * @param statistik    Spielstatistik
     */
    void spielVorbei(String benutzername, Spielstatistik statistik);
}
