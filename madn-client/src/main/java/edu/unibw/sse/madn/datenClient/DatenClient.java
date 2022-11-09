package edu.unibw.sse.madn.datenClient;

import edu.unibw.sse.madn.base.SpielfeldKonfigurationBytes;
import javafx.scene.image.Image;
import javafx.scene.media.Media;

public interface DatenClient {
    Image balkenBildLaden();

    SpielfeldKonfiguration konfigurationLaden(String name);

    void konfigurationSpeichern(SpielfeldKonfigurationBytes konfiguration, String name);

    Media zufaelligesGif();
}
