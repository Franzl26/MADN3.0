package edu.unibw.sse.madn.base;

import java.io.Serializable;

public interface SpielfeldKonfigurationBytes extends Serializable {
    void konfigurationSpeichern(String verzeichnis);
}
