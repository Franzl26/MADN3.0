package edu.unibw.sse.madn.warteraumverwaltung.Impl;

import edu.unibw.sse.madn.base.Warteraum;
import edu.unibw.sse.madn.spielLogik.SpielErstellen;
import edu.unibw.sse.madn.spielLogik.WarteraumCallback;
import edu.unibw.sse.madn.warteraumverwaltung.AnClientSendenRaumauswahl;
import edu.unibw.sse.madn.warteraumverwaltung.Raumauswahl;
import edu.unibw.sse.madn.warteraumverwaltung.Warteraumverwaltung;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

public class WarteraumverwaltungImpl implements Raumauswahl, WarteraumCallback, Warteraumverwaltung {
    private final static int MAX_RAEUME = 25;

    private AnClientSendenRaumauswahl anClient;
    private SpielErstellen spielErstellen;
    private final HashMap<String, Long> istInRaum = new HashMap<>();
    private final HashMap<Long, WarteraumImpl> idZuRaum = new HashMap<>();
    private int raumAnzahl = 0;

    @Override
    public Raumauswahl raumauswahlHolen() {
        return this;
    }

    @Override
    public void spielErstellenSetzen(SpielErstellen spielErstellen) {
        this.spielErstellen = spielErstellen;
        spielErstellen.warteraumCallbackSetzen(this);
    }

    @Override
    public synchronized boolean warteraumErstellen(String sitzung) {
        if (raumAnzahl >= MAX_RAEUME) return false;
        WarteraumImpl neuerRaum = new WarteraumImpl();
        neuerRaum.spielerHinzufuegen(sitzung, sitzung);
        idZuRaum.put(neuerRaum.id(), neuerRaum);
        istInRaum.put(sitzung, neuerRaum.id());
        raumAnzahl++;
        updateClients();
        return true;
    }

    @Override
    public synchronized boolean warteraumBeitreten(String sitzung, long raumId) {
        WarteraumImpl raum = idZuRaum.get(raumId);
        if (raum == null) return false;
        if (raum.anzahlSpieler() + raum.botAnzahl() >= 4) return false;
        raum.spielerHinzufuegen(sitzung, sitzung);
        istInRaum.put(sitzung, raum.id());
        updateClients();
        return true;
    }


    // in Warteraum
    @Override
    public synchronized void warteraumVerlassen(String sitzung) {
        Long id = istInRaum.get(sitzung);
        if (id == null) return;
        WarteraumImpl raum = idZuRaum.get(id);
        raum.spielerEntfernen(sitzung, sitzung);
        if (raum.anzahlSpieler() <= 0) {
            idZuRaum.remove(id);
            raumAnzahl--;
        }
        istInRaum.remove(sitzung);
        updateClients();
    }

    @Override
    public synchronized boolean botHinzufuegen(String sitzung) {
        Long id = istInRaum.get(sitzung);
        if (id == null) return false;
        WarteraumImpl raum = idZuRaum.get(id);
        if (raum.anzahlSpieler() + raum.botAnzahl() >= 4) return false;
        raum.botHinzufuegen();
        updateClients();
        return true;
    }

    @Override
    public synchronized boolean botEntfernen(String sitzung) {
        Long id = istInRaum.get(sitzung);
        if (id == null) return false;
        WarteraumImpl raum = idZuRaum.get(id);
        if (raum.botAnzahl() <= 0) return false;
        raum.botEntfernen();
        updateClients();
        return true;
    }

    @Override
    public synchronized boolean spielStarten(String sitzung) {
        Long id = istInRaum.get(sitzung);
        if (id == null) return false;
        WarteraumImpl raum = idZuRaum.get(id);
        if (raum.anzahlSpieler() + raum.botAnzahl() <= 1) return false;
        spielErstellen.spielErstellen(raum.clients(), raum.botAnzahl(), raum.anzahlSpieler());
        alleInRaumEntfernen(id);
        idZuRaum.remove(id);
        updateClients();
        return true;
    }

    @Override
    public void anClientSendenRaumauswahlSetzen(AnClientSendenRaumauswahl anClientSendenRaumauswahl) {
        anClient = anClientSendenRaumauswahl;
    }

    @Override
    public synchronized void spielBeendet() {
        raumAnzahl--;
    }

    private void updateClients() {
        LinkedList<Warteraum> warteraeume = new LinkedList<>();
        idZuRaum.values().forEach(r -> warteraeume.add(r.toWarteraum()));
        new Thread(() -> anClient.raeumeUpdaten(warteraeume.toArray(new Warteraum[0]))).start();
    }

    private synchronized void alleInRaumEntfernen(long id) {
        ArrayList<String> entfernen = new ArrayList<>();
        istInRaum.keySet().forEach(e -> {
            if (istInRaum.get(e).equals(id)) entfernen.add(e);
        });
        entfernen.forEach(istInRaum::remove);
    }
}
