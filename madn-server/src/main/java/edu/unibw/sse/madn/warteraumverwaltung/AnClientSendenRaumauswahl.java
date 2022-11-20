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

    /**
     * Sendet eine Nachricht im Chat in Warteraum an Client
     *
     * @param benutzernamen Benutzernamen der Clients, an die die Nachricht gesendet werden soll
     * @param nachricht Nachricht die gesendet werden soll
     */
    void nachrichtSenden(String[] benutzernamen, String nachricht);
}
