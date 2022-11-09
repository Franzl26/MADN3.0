package edu.unibw.sse.madn.warteraumverwaltung;

import edu.unibw.sse.madn.base.Warteraum;

public interface AnClientSendenRaumauswahl {
    /**
     * Alle Warteräume übermitteln
     *
     * @param warteraeume Alle aktuell verfügbaren Warteräume
     */
    void raeumeUpdaten(Warteraum[] warteraeume);

    /**
     * mitteilen, dass Spiel gestartet wurde
     *
     * @param benutzername Name des Clients der informiert werden soll
     * @param design       ausgewähltes Design
     */
    void spielStartet(String benutzername, String design);
}
