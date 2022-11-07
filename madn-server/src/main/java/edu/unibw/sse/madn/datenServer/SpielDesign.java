package edu.unibw.sse.madn.datenServer;

import edu.unibw.sse.madn.base.SpielfeldKonfigurationBytes;

public interface SpielDesign {
    String[] designListeHolen();

    SpielfeldKonfigurationBytes spielfeldKonfigurationHolen(String name);

    boolean existiertDesign(String design);
}
