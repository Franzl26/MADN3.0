package edu.unibw.sse.madn.warteraumverwaltung;

import edu.unibw.sse.madn.base.Warteraum;

public interface AnClientSendenRaumauswahl {
    void raeumeUpdaten(Warteraum[] warteraeume);

    void spielStartet(String benutzername, String design);
}
