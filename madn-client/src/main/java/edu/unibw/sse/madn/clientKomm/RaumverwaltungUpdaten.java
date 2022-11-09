package edu.unibw.sse.madn.clientKomm;

import edu.unibw.sse.madn.base.Warteraum;

public interface RaumverwaltungUpdaten { // Frank
    /**
     * senden der Warteräume an den die Komponente Ansicht, damit diese angezeigt werden können
     *
     * @param warteraeume Array aller aktuell aktiven Warteräume
     */
    void raeumeUpdaten(Warteraum[] warteraeume);

    /**
     * Teil der Komponente Ansicht mit, dass das Spiel gestartet wurde und übermittelt das zu verwendende Design
     *
     * @param design Name des SpielDesigns, dass ausgewählt wurde
     */
    void spielStartet(String design);
}
