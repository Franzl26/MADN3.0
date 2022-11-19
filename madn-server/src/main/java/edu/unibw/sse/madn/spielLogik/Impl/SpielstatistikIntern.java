package edu.unibw.sse.madn.spielLogik.Impl;

import edu.unibw.sse.madn.base.Spielstatistik;

public class SpielstatistikIntern {
    private String[] names;
    private final int[][] zahlenGewuerfelt;
    private final int[] andereGeschlagen;
    private final int[] geschlagenWorden;
    private final int[] prioZugFalsch;
    private final String[] platzierungen;
    private final long startZeit;

    SpielstatistikIntern() {
        this.names = new String[4];
        zahlenGewuerfelt = new int[4][6];
        andereGeschlagen = new int[4];
        geschlagenWorden = new int[4];
        platzierungen = new String[4];
        prioZugFalsch = new int[4];
        startZeit = System.currentTimeMillis();
    }

    void namenSetzen(String[] names) {
        this.names = names;
    }

    void incZahlGewuerfelt(int spieler, int zahl) {
        zahlenGewuerfelt[spieler][zahl]++;
    }

    void incAndereGeschlagen(int spieler) {
        andereGeschlagen[spieler]++;
    }

    void incGeschlagenWorden(int spieler) {
        geschlagenWorden[spieler]++;
    }

    void incPrioZugIgnoriert(int spieler) {
        prioZugFalsch[spieler]++;
    }

    void platzierungSetzen(int platz, String name) {
        platzierungen[platz] = name;
    }

    Spielstatistik holeZumSenden() {
        return new Spielstatistik(zahlenGewuerfelt, andereGeschlagen, geschlagenWorden, prioZugFalsch, platzierungen, startZeit, names);
    }
}
