package edu.unibw.sse.madn.datenServer.Impl;

import edu.unibw.sse.madn.datenServer.BenutzerDaten;
import edu.unibw.sse.madn.datenServer.DateizugriffServer;
import edu.unibw.sse.madn.datenServer.SpielDesign;

public class DateizugriffServerImpl implements DateizugriffServer {
    private final BenutzerDatenImpl benutzerDaten;
    private final SpielDesignImpl spielDesign;

    public DateizugriffServerImpl() {
        benutzerDaten = new BenutzerDatenImpl();
        spielDesign = new SpielDesignImpl();
    }

    @Override
    public BenutzerDaten benutzerDatenHolen() {
        return benutzerDaten;
    }

    @Override
    public SpielDesign spielDesignHolen() {
        return spielDesign;
    }
}
