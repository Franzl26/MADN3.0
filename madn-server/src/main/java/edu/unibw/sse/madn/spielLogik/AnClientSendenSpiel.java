package edu.unibw.sse.madn.spielLogik;

import edu.unibw.sse.madn.base.FeldBesetztStatus;
import edu.unibw.sse.madn.base.Spielstatistik;

public interface AnClientSendenSpiel {
    /**
     * Spielfeld aktualisieren
     *
     * @param benutzernamen Namen der Clients die informiert werden sollen
     * @param feld         Feld
     * @param geandert     array der geänderten Felder, wenn null komplett neu zeichnen
     */
    void spielfeldUpdaten(String[] benutzernamen, FeldBesetztStatus[] feld, int[] geandert);

    /**
     * Namen der Spieler übermitteln
     *
     * @param benutzernamen Namen der Clients die informiert werden sollen
     * @param namen        Namen
     */
    void spielNamenUpdaten(String[] benutzernamen, String[] namen);

    /**
     * aktuellen Spieler setzen
     *
     * @param benutzernamen Namen der Clients die informiert werden sollen
     * @param spieler      Spieler
     */
    void aktuellenSpielerSetzen(String[] benutzernamen, int spieler);

    /**
     * Würfelwert übermitteln
     *
     * @param benutzernamen Namen der Clients die informiert werden sollen
     * @param wert         Würfelwert
     */
    void wuerfelUpdaten(String[] benutzernamen, int wert);

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
     * @param benutzername Namen der Clients die informiert werden sollen
     * @param statistik    Spielstatistik
     */
    void spielVorbei(String[] benutzername, Spielstatistik statistik);

    /**
     * Sendet eine Nachricht im Chat in Warteraum an Client
     *
     * @param benutzernamen Benutzernamen der Clients, an die die Nachricht gesendet werden soll
     * @param nachricht Nachricht die gesendet werden soll
     */
    void nachrichtSenden(String[] benutzernamen, String nachricht);
}
