package edu.unibw.sse.madn.serverKomm.Impl;

import edu.unibw.sse.madn.benutzerVerwaltung.BenutzerZugang;
import edu.unibw.sse.madn.datenServer.SpielDesign;
import edu.unibw.sse.madn.komm.ServerVerbindung;
import edu.unibw.sse.madn.komm.Sitzung;
import edu.unibw.sse.madn.serverKomm.ServerKommunikation;
import edu.unibw.sse.madn.spielLogik.Spiel;
import edu.unibw.sse.madn.warteraumverwaltung.Raumauswahl;

import java.rmi.RemoteException;
import java.util.Collection;
import java.util.HashMap;

public class ServerKommunikationImpl implements ServerKommunikation {
    private final ServerVerbindungImpl serverVerbindung;
    private final AnClientSendenImpl anClientSenden;

    public ServerKommunikationImpl() {
        try {
            serverVerbindung = new ServerVerbindungImpl(this);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
        anClientSenden = new AnClientSendenImpl(this);
    }

    @Override
    public ServerVerbindung serverVerbindungHolen() {
        return serverVerbindung;
    }

    @Override
    public void spielDesignSetzen(SpielDesign spielDesign) {
        serverVerbindung.spielDesign = spielDesign;
    }

    @Override
    public void benutzerZugangSetzen(BenutzerZugang benutzerZugang) {
        serverVerbindung.benutzerZugang = benutzerZugang;
    }

    @Override
    public void raumauswahlSetzen(Raumauswahl raumauswahl) {
        serverVerbindung.raumauswahl = raumauswahl;
        raumauswahl.anClientSendenRaumauswahlSetzen(anClientSenden);
    }

    @Override
    public void spielSetzen(Spiel spiel) {
        serverVerbindung.spiel = spiel;
        spiel.anClientSendenSpielSetzen(anClientSenden);
    }

    private final HashMap<String, Sitzung> clients = new HashMap<>();

    synchronized void nutzerHinzufuegen(String benutzername, Sitzung sitzung) {
        clients.put(benutzername,sitzung);
    }

    synchronized Sitzung benutzerHolen(String benutzername) {
        return clients.get(benutzername);
    }

    synchronized void benutzerAbmelden(String benutzername) {
        clients.remove(benutzername);
    }

    synchronized void benutzerAbmelden(Sitzung sitzung) {
        for (String s : clients.keySet()) {
            if (clients.get(s).equals(sitzung)) {
                clients.remove(s);
            }
        }
    }

    synchronized Collection<Sitzung> alleBenutzerHolen() {
        return clients.values();
    }
}
