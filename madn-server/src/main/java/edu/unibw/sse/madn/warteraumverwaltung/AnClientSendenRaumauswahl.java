package edu.unibw.sse.madn.warteraumverwaltung;

import edu.unibw.sse.madn.base.Warteraum;

public interface AnClientSendenRaumauswahl {
    /**
     * Nimmt ein Array von Warteräumen und übermittelt dieses an den Client (Domenik)
     *
     * @param warteraeume Alle aktuell verfügbaren Warteräume
     */
    void raeumeUpdaten(Warteraum[] warteraeume);

    /**
     * (Domenik)
     * Teilt dem client des angegebenen benutzernames mit, dass das Spiel gestartet wurde
     * und übergibt welches spieldesign ausgewählt wurde.
     *
     * @param benutzername Name des Clients der informiert werden soll
     * @param design       ausgewähltes Design
     */
    void spielStartet(String benutzername, String design);
}
