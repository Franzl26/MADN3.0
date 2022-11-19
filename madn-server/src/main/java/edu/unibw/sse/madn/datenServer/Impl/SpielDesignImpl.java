package edu.unibw.sse.madn.datenServer.Impl;


import edu.unibw.sse.madn.impl.SpielfeldKonfigurationLadenSpeichern;
import edu.unibw.sse.madn.base.SpielfeldKonfiguration;
import edu.unibw.sse.madn.datenServer.SpielDesign;

import java.io.File;

public class SpielDesignImpl implements SpielDesign {
    @Override
    public String[] designListeHolen() {
        File f = new File("./madn-server/src/main/resources/designs/");
        return f.list();
    }

    @Override
    public SpielfeldKonfiguration spielfeldKonfigurationHolen(String name) {
        return SpielfeldKonfigurationLadenSpeichern.loadBoardKonfiguration("./madn-server/src/main/resources/designs/" + name + "/");
    }
}
