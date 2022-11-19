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

public class ServerStart {
    public static void main(String[] args) {
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
    }
}
