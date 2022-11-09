package edu.unibw.sse.madn.komm;

import edu.unibw.sse.madn.base.SpielStatistik;
import edu.unibw.sse.madn.base.SpielfeldKonfiguration;
import edu.unibw.sse.madn.base.WuerfelnRueckgabe;
import edu.unibw.sse.madn.base.ZiehenRueckgabe;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Sitzung extends Remote {
    // Nutzerverwaltung

    /**
     * meldet Client ab
     */
    void abmelden() throws RemoteException;

    // Designs

    /**
     * @return List aller verfügbaren Designs/Spielfeld-Konfigurationen
     */
    String[] designListeHolen() throws RemoteException;

    /**
     * @param name Name des Designs
     * @return die geladene Spielfeld-Konfiguration oder null bei Fehler
     */
    SpielfeldKonfiguration spielfeldKonfigurationHolen(String name) throws RemoteException;


    // Warteraum

    /**
     * Warteraum erstellen
     *
     * @return Warteraum erstellt: true, sonst false
     */
    boolean warteraumErstellen() throws RemoteException;

    /**
     * Warteraum beitreten
     *
     * @param raumId Raum-ID
     * @return Warteraum beigetreten: true, sonst false
     */
    boolean warteraumBeitreten(long raumId) throws RemoteException;

    /**
     * Warteraum verlassen
     */
    void warteraumVerlassen() throws RemoteException;

    /**
     * Bot hinzufügen
     *
     * @return Bot hinzugefügt: true, sonst false
     */
    boolean botHinzufuegen() throws RemoteException;

    /**
     * Bot Entfernen
     *
     * @return Bot entfernt: true, sonst false
     */
    boolean botEntfernen() throws RemoteException;

    /**
     * Spiel starten
     *
     * @return Spiel gestartet: true, sonst false
     */
    boolean spielStarten() throws RemoteException;

    /**
     * Spieldesign ändern
     *
     * @param design Design
     */
    void designAnpassen(String design) throws RemoteException;


    // Spiel

    /**
     * Spielzug einreichen
     *
     * @param von  Feld von
     * @param nach Feld nach
     */
    ZiehenRueckgabe figurZiehen(int von, int nach) throws RemoteException;

    /**
     * Würfeln
     */
    WuerfelnRueckgabe wuerfeln() throws RemoteException;

    /**
     * Spiel Verlassen
     *
     * @return Spielstatistik oder null bei Fehler
     */
    SpielStatistik spielVerlassen() throws RemoteException;

    /**
     * @return Benutzernamen der Session
     */
    String benutzername() throws RemoteException;

    /**
     * @return Client Callback der Session
     */
    ClientCallback clientCallback() throws RemoteException;
}
