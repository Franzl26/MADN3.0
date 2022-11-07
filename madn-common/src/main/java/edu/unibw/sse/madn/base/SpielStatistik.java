package edu.unibw.sse.madn.base;

import java.io.Serializable;

public interface SpielStatistik extends Serializable {
    int[][] zahlenGewuerfelt();

    int[] andereGeschlagen();

    int[] geschlagenWorden();

    int[] prioZugFalsch();

    String[] platzierungen();

    long startZeit();

    String[] namen();
}
