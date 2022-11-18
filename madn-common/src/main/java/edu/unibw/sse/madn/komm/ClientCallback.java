package edu.unibw.sse.madn.komm;

import edu.unibw.sse.madn.base.FeldBesetztStatus;
import edu.unibw.sse.madn.base.Spielstatistik;
import edu.unibw.sse.madn.base.Warteraum;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Tom
 */
public interface ClientCallback extends Remote {
    // Raumauswahl

    /**
     * Alle Warteräume übermitteln
     *
     * @param warteraeume Alle aktuell verfügbaren Warteräume
     */
    void raeumeUpdaten(Warteraum[] warteraeume) throws RemoteException;


    // im Spiel

    /**
     * Spielfeld aktualisieren
     *
     * @param feld     Feld
     * @param geandert array der geänderten Felder, wenn null komplett neu zeichnen
     */
    void spielfeldUpdaten(FeldBesetztStatus[] feld, int[] geandert) throws RemoteException;

    /**
     * Namen der Spieler übermitteln
     *
     * @param namen Namen
     */
    void spielNamenUpdaten(String[] namen) throws RemoteException;

    /**
     * aktuellen Spieler setzen
     *
     * @param spieler Spieler
     */
    void aktuellenSpielerSetzen(int spieler) throws RemoteException;

    /**
     * Würfelwert übermitteln
     *
     * @param wert Würfelwert
     */
    void wuerfelUpdaten(int wert) throws RemoteException;

    /**
     * Anzeigen, dass Zeit fürs Würfeln abgelaufen
     */
    void wuerfelnVorbei() throws RemoteException;

    /**
     * Anzeigen, dass Zeit fürs Ziehen abgelaufen
     */
    void ziehenVorbei() throws RemoteException;

    /**
     * Gif Anzeigen, wenn Zeit für Ziehen fortgeschritten
     */
    void gifAnzeigen() throws RemoteException;

    /**
     * Teilt Client mit, dass Spiel vorbei und übermittelt ihm Spielstatistik
     *
     * @param statistik Spielstatistik
     */
    void spielVorbei(Spielstatistik statistik) throws RemoteException;
}
