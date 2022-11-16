package edu.unibw.sse.madn.warteraumverwaltung;

import edu.unibw.sse.madn.base.Warteraum;

/**
 * Domenik
 */
public interface AnClientSendenRaumauswahl {
    /**
     * Nimmt ein Array von Warteräumen und übermittelt dieses an den Client
     *
     * @param warteraeume Alle aktuell verfügbaren Warteräume
     */
    void raeumeUpdaten(Warteraum[] warteraeume);
}
