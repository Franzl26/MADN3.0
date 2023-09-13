package edu.unibw.sse.madn.datenServer.Impl;


import edu.unibw.sse.madn.impl.SpielfeldKonfigurationLadenSpeichern;
import edu.unibw.sse.madn.base.SpielfeldKonfiguration;
import edu.unibw.sse.madn.datenServer.SpielDesign;

import java.io.File;
import java.util.LinkedList;

public class SpielDesignImpl implements SpielDesign {

    @Override
    public String[] designListeHolen(boolean sechser) {
        File f = new File("./madn-server/src/main/resources/designs/");
        String[] liste = f.list();
        if (liste == null) return null;
        LinkedList<String> neu = new LinkedList<>();
        for (String s : liste) {
            if (sechser && s.endsWith("6")) neu.add(s);
            if (!sechser && !s.endsWith("6")) neu.add(s);
        }
        return neu.toArray(new String[0]);
    }

    @Override
    public SpielfeldKonfiguration spielfeldKonfigurationHolen(String name, boolean sechser) {
        return SpielfeldKonfigurationLadenSpeichern.loadBoardKonfiguration("./madn-server/src/main/resources/designs/" + name + (sechser ? "6" : "") + "/", sechser);
    }
}
