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
     * teilt dem client des angegebenen Benutzernamen mit, dass das Spiel gestartet wurde
     * und übergibt welches spieldesign ausgewählt wurde.
     *
     * @param benutzername Name des Clients der informiert werden soll
     * @param design       ausgewähltes Design
     */
    void spielStartet(String benutzername, String design);
}
