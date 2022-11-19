package edu.unibw.sse.madn.app;

import edu.unibw.sse.madn.benutzerVerwaltung.BenutzerVerwaltung;
import edu.unibw.sse.madn.benutzerVerwaltung.Impl.BenutzerZugangImpl;
import edu.unibw.sse.madn.datenServer.DateizugriffServer;
import edu.unibw.sse.madn.datenServer.Impl.DateizugriffServerImpl;
import edu.unibw.sse.madn.serverKomm.Impl.ServerKommunikationImpl;
import edu.unibw.sse.madn.spielLogik.Impl.SpielLogikImpl;
import edu.unibw.sse.madn.spielLogik.SpielLogik;
import edu.unibw.sse.madn.warteraumverwaltung.Impl.WarteraumverwaltungImpl;
import edu.unibw.sse.madn.warteraumverwaltung.Warteraumverwaltung;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class ServerStart {
    public static void main(String[] args) {
        try {
            DateizugriffServer dateizugriffServer = new DateizugriffServerImpl();
            BenutzerVerwaltung benutzerVerwaltung = new BenutzerZugangImpl();
            Warteraumverwaltung warteraumverwaltung = new WarteraumverwaltungImpl();
            SpielLogik spielLogik = new SpielLogikImpl();
            ServerKommunikationImpl serverKommunikation = new ServerKommunikationImpl();
            benutzerVerwaltung.benutzerDatenSetzen(dateizugriffServer.benutzerDatenHolen());
            warteraumverwaltung.spielErstellenSetzen(spielLogik.spielErstellenHolen());
            serverKommunikation.spielDesignSetzen(dateizugriffServer.spielDesignHolen());
            serverKommunikation.benutzerZugangSetzen(benutzerVerwaltung.benutzerZugangHolen());
            serverKommunikation.raumauswahlSetzen(warteraumverwaltung.raumauswahlHolen());
            serverKommunikation.spielSetzen(spielLogik.spielHolen());

            LocateRegistry.createRegistry(Registry.REGISTRY_PORT);
            Naming.rebind("//localhost/MADNLogin", serverKommunikation.serverVerbindungHolen());
            System.out.println("Server gestartet");
        } catch (RemoteException| MalformedURLException e) {
            System.err.println("Server konnte nicht gestartet werden");
            e.printStackTrace();
        }
    }
}
