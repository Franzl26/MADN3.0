package edu.unibw.sse.madn.warteraumverwaltung;

import edu.unibw.sse.madn.serverKomm.AnClientSendenRaumauswahl;
import edu.unibw.sse.madn.komm.Sitzung;

import java.io.Serializable;

public interface Raumauswahl extends Serializable {
    /**
     * meldet Client für die aktualisierung der Warteräume an
     *
     * @param sitzung Sitzung
     */
    void fuerUpdatesAnmelden(Sitzung sitzung);

    /**
     * Warteraum erstellen
     *
     * @param sitzung Sitzung
     * @return Warteraum erstellt: true, sonst false
     */
    boolean warteraumErstellen(Sitzung sitzung);

    /**
     * Warteraum beitreten
     *
     * @param sitzung Sitzung
     * @param raumId  Raum-ID
     * @return Warteraum beigetreten: true, sonst false
     */
    boolean warteraumBeitreten(Sitzung sitzung, long raumId);

    /**
     * Warteraum verlassen
     *
     * @param sitzung Sitzung
     */
    void warteraumVerlassen(Sitzung sitzung);

    /**
     * Bot hinzufügen
     *
     * @param sitzung Sitzung
     * @return Bot hinzugefügt: true, sonst false
     */
    boolean botHinzufuegen(Sitzung sitzung);

    /**
     * Bot Entfernen
     *
     * @param sitzung Sitzung
     * @return Bot entfernt: true, sonst false
     */
    boolean botEntfernen(Sitzung sitzung);

    /**
     * Spiel starten
     *
     * @param sitzung sitzung
     * @return Spiel gestartet: true, sonst false
     */
    boolean spielStarten(Sitzung sitzung);

    /**
     * Spieldesign ändern
     *
     * @param sitzung Sitzung
     * @param design  Design
     */
    void designAnpassen(Sitzung sitzung, String design);
}
