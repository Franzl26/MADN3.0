package edu.unibw.sse.madn.datenServer.Impl;

import edu.unibw.sse.madn.datenServer.Benutzer;
import edu.unibw.sse.madn.datenServer.BenutzerDaten;
import edu.unibw.sse.madn.datenServer.DateizugriffServer;

import java.io.*;
import java.util.LinkedList;

public class BenutzerDatenImpl implements BenutzerDaten {
    private final static String ordner = "./madn-server/src/main/resources/Benutzer/";

    @Override
    public void benutzerSpeichern(Benutzer benutzer) {
        File benutzerFile = new File(ordner);
        //noinspection ResultOfMethodCallIgnored
        benutzerFile.mkdirs();
        try (ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(ordner + benutzer.benutzername()))) {
            os.writeObject(benutzer);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void benutzerLoeschen(Benutzer benutzer) {
        File file = new File(ordner + benutzer.benutzername());
        if (file.delete()) {
            System.err.println("Benutzer: " + benutzer.benutzername() + " wurde gelöscht");
        } else {
            System.err.println("Benutzer: " + benutzer.benutzername() + " konnte nicht gelöscht werden");
        }
    }

    @Override
    public Benutzer[] benutzerLaden() {
        LinkedList<Benutzer> benutzer = new LinkedList<>();
        File file = new File(ordner);
        if (!file.isDirectory()) {
            System.err.println("Falscher Ordner angegeben");
            return null;
        }
        File[] benutzerFile = file.listFiles();
        if (benutzerFile == null) return null;
        for (File f : benutzerFile) {
            try (ObjectInputStream os = new ObjectInputStream(new FileInputStream(f))) {
                benutzer.add((Benutzer) os.readObject());
            } catch (IOException | ClassNotFoundException e) {
                System.err.println("Benutzer konnten nicht gelesen werden");
            }
        }
        return benutzer.toArray(new Benutzer[0]);
    }
}
