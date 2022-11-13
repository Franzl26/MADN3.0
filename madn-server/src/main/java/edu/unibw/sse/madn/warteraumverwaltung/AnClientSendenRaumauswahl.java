package edu.unibw.sse.madn.warteraumverwaltung;

import edu.unibw.sse.madn.base.Warteraum;

public interface AnClientSendenRaumauswahl {
    /**
     * Nimmt ein Array von Warteräumen und konvertiert dieses in ein array aus String arrays
     * [[id , name1 , ... , name4],[id, name1,...],...]
     * Und übermittelt diese an den Client
     *
     * @param warteraeume Alle aktuell verfügbaren Warteräume
     */
    void raeumeUpdaten(Warteraum[] warteraeume);

    /**
     * Teilt dem client des angegebenen benutzernames mit, dass das Spiel gestartet wurde
     * und übergibt welches spieldesign ausgewählt wurde.
     *
     * @param benutzername Name des Clients der informiert werden soll
     * @param design       ausgewähltes Design
     */
    void spielStartet(String benutzername, String design);
}
