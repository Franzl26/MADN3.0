package edu.unibw.sse.madn.datenClient;

import edu.unibw.sse.madn.base.SpielfeldKonfiguration;
import javafx.scene.image.Image;
import javafx.scene.media.Media;

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
    SpielfeldKonfiguration konfigurationLaden(String name);

    /**
     * Speichert die Konfiguration, wie sie vom Server übermittelt wurde
     *
     * @param konfiguration von Server übermittelte Konfiguration
     * @param name          Name unter dem die Konfiguration abgespeichert werden soll
     */
    void KonfigurationSpeichern(SpielfeldKonfiguration konfiguration, String name);

    /**
     * lädt ein zufälliges Gif
     *
     * @return MP4-Gif oder null, wenn keins geladen werden konnte
     */
    Media zufaelligesGif();
}
