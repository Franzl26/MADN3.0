package edu.unibw.sse.madn.ansicht;

import edu.unibw.sse.madn.base.SpielfeldKonfiguration;
import edu.unibw.sse.madn.datenClient.DatenClient;
import javafx.scene.media.Media;

public class DateiZugriffDummy implements DatenClient {
    @Override
    public byte[] balkenBildLaden() {
        return new byte[0];
    }

    @Override
    public SpielfeldKonfiguration konfigurationLaden(String name) {
        return null;
    }

    @Override
    public void KonfigurationSpeichern(SpielfeldKonfiguration konfiguration, String name) {

    }

    @Override
    public Media[] alleGifsLaden() {
        return new Media[0];
    }
}
