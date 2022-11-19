package edu.unibw.sse.madn.warteraumverwaltung.Impl;

import edu.unibw.sse.madn.base.Warteraum;

import java.util.ArrayList;

public class WarteraumImpl {
    private static long ID_ZAEHLER = 0;

    private final long id = ID_ZAEHLER++;
    private final ArrayList<String> namen = new ArrayList<>(4);
    private final ArrayList<String> clients = new ArrayList<>(4);
    private int botAnzahl = 0;
    private int spielerAnzahl = 0;

    int anzahlSpieler() {
        return spielerAnzahl;
    }

    void spielerHinzufuegen(String sitzung, String benutzername) {
        clients.add(sitzung);
        namen.add(benutzername);
        spielerAnzahl++;
    }

    void spielerEntfernen(String sitzung, String benutzername) {
        clients.remove(sitzung);
        namen.remove(benutzername);
        spielerAnzahl--;
    }

    void botHinzufuegen() {
        botAnzahl++;
        namen.add("Bot" + botAnzahl);
    }

    void botEntfernen() {
        namen.remove("Bot" + botAnzahl);
        botAnzahl--;
    }

    int botAnzahl() {
        return botAnzahl;
    }

    String[] clients() {
        return clients.toArray(new String[0]);
    }

    Warteraum toWarteraum() {
        return new Warteraum(id, namen.toArray(new String[0]));
    }

    public long id() {
        return id;
    }
}
