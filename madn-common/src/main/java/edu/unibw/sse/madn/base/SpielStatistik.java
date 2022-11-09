package edu.unibw.sse.madn.base;

public record SpielStatistik(int[][] zahlenGewuerfelt, int[] andereGeschlagen, int[] geschlagenWorden, int[] prioZugFalsch, String[] platzierungen, long startZeit, String[] namen) {
}
