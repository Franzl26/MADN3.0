package edu.unibw.sse.madn.clientKomm;

import edu.unibw.sse.madn.base.Warteraum;

/**
 * Frank
 */
public interface RaumverwaltungUpdaten {
    /**
     * senden der Warteräume an den die Komponente Ansicht, damit diese angezeigt werden können
     *
     * @param warteraeume Array aller Warteräume
     */
    void raeumeUpdaten(Warteraum[] warteraeume);
}
