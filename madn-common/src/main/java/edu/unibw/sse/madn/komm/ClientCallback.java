package edu.unibw.sse.madn.komm;

import edu.unibw.sse.madn.base.FeldBesetztStatus;
import edu.unibw.sse.madn.base.SpielStatistik;
import edu.unibw.sse.madn.base.Warteraum;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ClientCallback extends Remote {
    // Raumauswahl
    void raeumeUpdaten(Warteraum[] warteraeume) throws RemoteException;

    // Warteraum
    void spielStartet(String design) throws RemoteException;

    // im Spiel
    void spielfeldUpdaten(FeldBesetztStatus[] feld, int[] geandert) throws RemoteException;

    void spielNamenUpdaten(String[] namen) throws RemoteException;

    void aktuellenSpielerSetzen(int spieler) throws RemoteException;

    void wuerfelUpdaten(int wert) throws RemoteException;

    void wuerfelnVorbei() throws RemoteException;

    void ziehenVorbei() throws RemoteException;

    void gifAnzeigen() throws RemoteException;

    void spielVorbei(SpielStatistik statistik) throws RemoteException;
}
