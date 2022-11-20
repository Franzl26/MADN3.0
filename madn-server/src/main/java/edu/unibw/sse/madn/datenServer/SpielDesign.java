package edu.unibw.sse.madn.datenServer;

import edu.unibw.sse.madn.base.SpielfeldKonfiguration;

/**
 * Nico
 */
public interface SpielDesign {
    /**
     * Gibt die Liste aller Spielfeldkonfigurationen zurück
     * 
     * @return List aller verfügbaren Designs/Spielfeld-Konfigurationen
     */
    String[] designListeHolen();

    /**
     * Gibt die Konfiguration, welche zu dem ausgewählten Namen gehört, zurück
     * 
     * @param name Name des Designs
     * @return die geladene Spielfeld-Konfiguration oder null bei Fehler
     */
    SpielfeldKonfiguration spielfeldKonfigurationHolen(String name, boolean sechser);
}
