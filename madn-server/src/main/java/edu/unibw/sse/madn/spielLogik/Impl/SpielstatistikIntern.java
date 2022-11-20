package edu.unibw.sse.madn.spielLogik.Impl;

import edu.unibw.sse.madn.base.Spielstatistik;

import java.util.Arrays;

public class SpielstatistikIntern {
    private String[] names;
    private final int[][] zahlenGewuerfelt;
    private final int[] andereGeschlagen;
    private final int[] geschlagenWorden;
    private final int[] prioZugFalsch;
    private final String[] platzierungen;
    private final long startZeit;

    SpielstatistikIntern() {
        this.names = new String[6];
        zahlenGewuerfelt = new int[6][6];
        andereGeschlagen = new int[6];
        geschlagenWorden = new int[6];
        platzierungen = new String[6];
        prioZugFalsch = new int[6];
        startZeit = System.currentTimeMillis();
    }

    void namenSetzen(String[] names) {
        this.names = names.clone();
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
