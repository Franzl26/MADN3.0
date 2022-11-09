package edu.unibw.sse.madn.clientKomm;

import edu.unibw.sse.madn.base.Warteraum;

public interface RaumverwaltungUpdaten {
    void raeumeUpdaten(Warteraum[] warteraeume);

    void spielStartet(String design);
}
