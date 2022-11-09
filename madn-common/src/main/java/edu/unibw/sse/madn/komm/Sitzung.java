package edu.unibw.sse.madn.komm;

import edu.unibw.sse.madn.base.SpielStatistik;
import edu.unibw.sse.madn.base.SpielfeldKonfigurationBytes;
import edu.unibw.sse.madn.base.WuerfelnRueckgabe;
import edu.unibw.sse.madn.base.ZiehenRueckgabe;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Sitzung extends Remote {
    // Nutzerverwaltung
    void abmelden() throws RemoteException;

    // Designs
    String[] designListeHolen() throws RemoteException;

    SpielfeldKonfigurationBytes spielfeldKonfigurationHolen(String name) throws RemoteException;

    // Warteraum
    boolean fuerWarteraumUpdatesAnmelden() throws RemoteException;

    boolean warteraumErstellen() throws RemoteException;

    boolean warteraumBeitreten(long raumId) throws RemoteException;

    void warteraumVerlassen() throws RemoteException;

    boolean botHinzufuegen() throws RemoteException;

    boolean botEntfernen() throws RemoteException;

    boolean spielStarten() throws RemoteException;

    void designAnpassen(String design) throws RemoteException;


    // Spiel
    ZiehenRueckgabe figurZiehen(int von, int nach) throws RemoteException;

    WuerfelnRueckgabe wuerfeln() throws RemoteException;

    SpielStatistik spielVerlassen() throws RemoteException;

    String benutzername() throws RemoteException;

    ClientCallback clientCallback() throws RemoteException;
}
