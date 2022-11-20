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

    /**
     * Sendet eine Nachricht im Chat in Warteraum an Client
     *
     * @param nachricht Nachricht die gesendet werden soll
     */
    void nachrichtSenden(String nachricht);
}
