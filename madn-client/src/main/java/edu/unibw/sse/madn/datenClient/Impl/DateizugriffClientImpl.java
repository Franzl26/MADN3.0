package edu.unibw.sse.madn.datenClient.Impl;

import edu.unibw.sse.madn.impl.SpielfeldKonfigurationLadenSpeichern;
import edu.unibw.sse.madn.base.SpielfeldKonfiguration;
import edu.unibw.sse.madn.datenClient.DateizugriffClient;
import edu.unibw.sse.madn.datenClient.DatenClient;
import javafx.scene.media.Media;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.LinkedList;

public class DateizugriffClientImpl implements DatenClient, DateizugriffClient {
    @Override
    public byte[] balkenBildLaden() {
        File f = new File("./madn-client/src/main/resources/gradient.png");
        try (DataInputStream dis = new DataInputStream(new FileInputStream(f))) {
            int length = (int) f.length();
            byte[] file = new byte[length];
            dis.readFully(file);
            return file;
        } catch (IOException e) {
            //throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public SpielfeldKonfiguration konfigurationLaden(String name, boolean sechser) {
        return SpielfeldKonfigurationLadenSpeichern.loadBoardKonfiguration("./madn-client/src/main/resources/designs/" + name + (sechser?"6":"") + "/", sechser);
    }

    @Override
    public void KonfigurationSpeichern(SpielfeldKonfiguration konfiguration, String name) {
        SpielfeldKonfigurationLadenSpeichern.saveConfiguration(konfiguration, "./madn-client/src/main/resources/designs/" + name + "/");
    }

    @Override
    public Media[] alleGifsLaden() {
        File f = new File("./madn-client/src/main/resources/waiting/gifs/");
        File[] gifList = f.listFiles();
        if (gifList == null) return null;
        LinkedList<Media> gifs = new LinkedList<>();
        for (File file : gifList) {
            Media tmp = new Media(file.toURI().toString());
            gifs.add(tmp);
        }
        return gifs.toArray(new Media[0]);
    }

    @Override
    public DatenClient datenClientHolen() {
        return this;
    }
}
