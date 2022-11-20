package edu.unibw.sse.madn.serverKomm.Impl;

import edu.unibw.sse.madn.base.SpielfeldKonfiguration;
import edu.unibw.sse.madn.base.Spielstatistik;
import edu.unibw.sse.madn.base.WuerfelnRueckgabe;
import edu.unibw.sse.madn.base.ZiehenRueckgabe;
import edu.unibw.sse.madn.benutzerVerwaltung.BenutzerZugang;
import edu.unibw.sse.madn.datenServer.SpielDesign;
import edu.unibw.sse.madn.komm.ClientCallback;
import edu.unibw.sse.madn.komm.Sitzung;
import edu.unibw.sse.madn.spielLogik.Spiel;
import edu.unibw.sse.madn.warteraumverwaltung.Raumauswahl;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class SitzungImpl extends UnicastRemoteObject implements Sitzung {
    private final ClientCallback clientCallback;
    private final String benutzername;
    private final SpielDesign spielDesign;
    private final BenutzerZugang benutzerZugang;
    private final Raumauswahl raumauswahl;
    private final Spiel spiel;
    private final ServerKommunikationImpl serverKommunikation;

    public SitzungImpl(ServerKommunikationImpl serverKommunikation, ClientCallback clientCallback, String benutzername, SpielDesign spielDesign, BenutzerZugang benutzerZugang, Raumauswahl raumauswahl, Spiel spiel) throws RemoteException {
        super();
        this.serverKommunikation = serverKommunikation;
        this.clientCallback = clientCallback;
        this.benutzername = benutzername;
        this.spielDesign = spielDesign;
        this.benutzerZugang = benutzerZugang;
        this.raumauswahl = raumauswahl;
        this.spiel = spiel;
    }

    @Override
    public void abmelden() throws RemoteException {
        serverKommunikation.benutzerAbmelden(benutzername);
        benutzerZugang.abmelden(benutzername);
    }

    @Override
    public String[] designListeHolen() throws RemoteException {
        return spielDesign.designListeHolen();
    }

    @Override
    public SpielfeldKonfiguration spielfeldKonfigurationHolen(String name) throws RemoteException {
        return spielDesign.spielfeldKonfigurationHolen(name);
    }

    @Override
    public boolean warteraumErstellen() throws RemoteException {
        return raumauswahl.warteraumErstellen(benutzername);
    }

    @Override
    public boolean warteraumBeitreten(long raumId) throws RemoteException {
        return raumauswahl.warteraumBeitreten(benutzername, raumId);
    }

    @Override
    public void warteraumVerlassen() throws RemoteException {
        raumauswahl.warteraumVerlassen(benutzername);
    }

    @Override
    public boolean botHinzufuegen() throws RemoteException {
        return raumauswahl.botHinzufuegen(benutzername);
    }

    @Override
    public boolean botEntfernen() throws RemoteException {
        return raumauswahl.botEntfernen(benutzername);
    }

    @Override
    public boolean spielStarten() throws RemoteException {
        return raumauswahl.spielStarten(benutzername);
    }

    @Override
    public ZiehenRueckgabe figurZiehen(int von, int nach) throws RemoteException {
        return spiel.figurZiehen(benutzername, von, nach);
    }

    @Override
    public WuerfelnRueckgabe wuerfeln() throws RemoteException {
        return spiel.wuerfeln(benutzername);
    }

    @Override
    public Spielstatistik spielVerlassen() throws RemoteException {
        return spiel.spielVerlassen(benutzername);
    }

    @Override
    public ClientCallback clientCallback() {
        return clientCallback;
    }

    @Override
    public boolean nachrichtSenden(String nachricht) throws RemoteException {
        boolean ret = raumauswahl.nachrichtSenden(benutzername, nachricht);
        if (!ret) {
            ret = spiel.nachrichtSenden(benutzername, nachricht);
        }
        return ret;
    }
}
