package edu.unibw.sse.madn.base;

/**
 *
 * @param id id des Warteraums
 * @param namen Array der Spielernamen im Warteraum, Länge muss Spieleranzahl+Botanzahl entsprechen
 */
public record Warteraum(long id, String[] namen) {
}
