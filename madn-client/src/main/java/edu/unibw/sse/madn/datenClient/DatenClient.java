package edu.unibw.sse.madn.datenClient;

import edu.unibw.sse.madn.base.SpielfeldKonfiguration;
import javafx.scene.image.Image;
import javafx.scene.media.Media;

/**
 * Nico
 */
public interface DatenClient {
    /**
     * @return byte-Array von Bild, welches für Balken in Spielstatistik verwendet wird
     */
    byte[] balkenBildLaden();

    /**
     * lädt die Konfiguration mit dem ausgewählten Namen
     * 
     * @param name Name des Designs
     * @return null bei Fehler sonst: Spielfeldkonfiguration
     */
    SpielfeldKonfiguration konfigurationLaden(String name, boolean sechser);

    /**
     * Speichert die Konfiguration, wie sie vom Server übermittelt wurde
     *
     * @param konfiguration von Server übermittelte Konfiguration
     * @param name          Name unter dem die Konfiguration abgespeichert werden soll
     */
    void KonfigurationSpeichern(SpielfeldKonfiguration konfiguration, String name);

    /**
     * lädt alle gespeicherten Gifs
     *
     * @return MP4-Gifs oder null, wenn Fehler aufgetreten sit
     */
    Media[] alleGifsLaden();
}
