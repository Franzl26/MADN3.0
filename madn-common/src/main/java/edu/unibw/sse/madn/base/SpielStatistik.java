package edu.unibw.sse.madn.base;

/**
 *
 * @param zahlenGewuerfelt die gewürfelten Zahlen nach Spieler und 1-6
 * @param andereGeschlagen die Anzahl, wie oft die Spieler geschlagen haben
 * @param geschlagenWorden die Anzahl, wie oft die Spieler geschlagen worden
 * @param prioZugFalsch die Anzahl, wie oft die Spieler einen Prio-Zug missachtet haben
 * @param platzierungen die Namen der Spieler in der Reihenfolge ihrer Platzierung
 * @param startZeit die Zeit zu der das Spiel gestartet wurde
 * @param namen die Namen in der Reihenfolge der Spieler entspricht der Reihenfolge von gewürfelt/geschlagen
 */
public record SpielStatistik(int[][] zahlenGewuerfelt, int[] andereGeschlagen, int[] geschlagenWorden, int[] prioZugFalsch, String[] platzierungen, long startZeit, String[] namen) {
}