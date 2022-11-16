package edu.unibw.sse.madn.clientKomm;

import edu.unibw.sse.madn.komm.ServerVerbindung;

public interface ClientKommunikation {
    /**
     * wird nur fürs Testen benötigt, da sich die Komponente die Schnittstelle sonst selber aus der Registry holt
     * @param serverVerbindung Schnittstelle
     */
    void serverVerbindungSetzenTest(ServerVerbindung serverVerbindung);

    ClientKomm clientKommunikationHolen();
}
