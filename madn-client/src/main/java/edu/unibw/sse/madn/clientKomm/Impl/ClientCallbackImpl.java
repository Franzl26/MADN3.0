package edu.unibw.sse.madn.clientKomm.Impl;

import edu.unibw.sse.madn.base.FeldBesetztStatus;
import edu.unibw.sse.madn.base.Spielstatistik;
import edu.unibw.sse.madn.base.Warteraum;
import edu.unibw.sse.madn.clientKomm.RaumverwaltungUpdaten;
import edu.unibw.sse.madn.clientKomm.SpielUpdaten;
import edu.unibw.sse.madn.komm.ClientCallback;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ClientCallbackImpl extends UnicastRemoteObject implements ClientCallback {
    RaumverwaltungUpdaten raumverwaltung;
    SpielUpdaten spiel;

    protected ClientCallbackImpl() throws RemoteException {
    }

    @Override
    public void spielfeldUpdaten(FeldBesetztStatus[] feld, int[] geandert) throws RemoteException {
        if (spiel == null) return;
        spiel.spielfeldUpdaten(feld, geandert);
    }

    @Override
    public void raeumeUpdaten(Warteraum[] warteraeume) throws RemoteException {
        raumverwaltung.raeumeUpdaten(warteraeume);
    }

    @Override
    public void spielNamenUpdaten(String[] namen) throws RemoteException {
        if (spiel == null) return;
        spiel.spielNamenUpdaten(namen);
    }

    @Override
    public void aktuellenSpielerSetzen(int spieler) throws RemoteException {
        if (spiel == null) return;
        spiel.aktuellenSpielerSetzen(spieler);
    }

    @Override
    public void wuerfelUpdaten(int wert) throws RemoteException {
        if (spiel == null) return;
        spiel.wuerfelUpdaten(wert);
    }

    @Override
    public void wuerfelnVorbei() throws RemoteException {
        if (spiel == null) return;
        spiel.wuerfelnVorbei();
    }

    @Override
    public void ziehenVorbei() throws RemoteException {
        if (spiel == null) return;
        spiel.ziehenVorbei();
    }

    @Override
    public void gifAnzeigen() throws RemoteException {
        if (spiel == null) return;
        spiel.gifAnzeigen();
    }

    @Override
    public void spielVorbei(Spielstatistik statistik) throws RemoteException {
        if (spiel == null) return;
        spiel.spielVorbei(statistik);
    }
}
