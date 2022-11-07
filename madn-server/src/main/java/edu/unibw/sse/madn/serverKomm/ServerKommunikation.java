package edu.unibw.sse.madn.serverKomm;

import edu.unibw.sse.madn.benutzerVerwaltung.BenutzerZugang;
import edu.unibw.sse.madn.datenServer.SpielDesign;
import edu.unibw.sse.madn.komm.ServerVerbindung;
import edu.unibw.sse.madn.spielLogik.Spiel;
import edu.unibw.sse.madn.warteraumverwaltung.Raumauswahl;

public interface ServerKommunikation {
    ServerVerbindung serverVerbindungHolen();

    AnClientSendenRaumauswahl anClientSendenRaumauswahlHolen();

    AnClientSendenSpiel anClientSendenSpielHolen();

    void spielDesignSetzen(SpielDesign spielDesign);

    void benutzerZugangSetzen(BenutzerZugang benutzerZugang);

    void raumauswahlSetzen(Raumauswahl raumauswahl);

    void spielSetzen(Spiel spiel);
}
