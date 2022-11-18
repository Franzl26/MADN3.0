package edu.unibw.sse.madn.ansicht;

import edu.unibw.sse.madn.base.*;
import edu.unibw.sse.madn.clientKomm.AllgemeinerReturnWert;
import edu.unibw.sse.madn.clientKomm.ClientKomm;
import edu.unibw.sse.madn.clientKomm.RaumverwaltungUpdaten;
import edu.unibw.sse.madn.clientKomm.SpielUpdaten;

public class ClientCommDummy implements ClientKomm {
    @Override
    public AllgemeinerReturnWert anmelden(String ip, String benutzername, String passwort) {
        return null;
    }

    @Override
    public RegistrierenRueckgabe registrieren(String ip, String benutzername, String passwort) {
        return null;
    }

    @Override
    public void abmelden() {

    }

    @Override
    public String[] designListeHolen() {
        return new String[0];
    }

    @Override
    public SpielfeldKonfiguration spielfeldKonfigurationHolen(String name) {
        return null;
    }

    @Override
    public AllgemeinerReturnWert warteraumErstellen() {
        return null;
    }

    @Override
    public AllgemeinerReturnWert warteraumBeitreten(long raumId) {
        return null;
    }

    @Override
    public void warteraumVerlassen() {

    }

    @Override
    public AllgemeinerReturnWert botHinzufuegen() {
        return null;
    }

    @Override
    public AllgemeinerReturnWert botEntfernen() {
        return null;
    }

    @Override
    public AllgemeinerReturnWert spielStarten() {
        return null;
    }


    @Override
    public ZiehenRueckgabe figurZiehen(int von, int nach) {
        return null;
    }

    @Override
    public WuerfelnRueckgabe wuerfeln() {
        return null;
    }

    @Override
    public Spielstatistik spielVerlassen() {
        return null;
    }

    @Override
    public void raumverwaltungUpdaterSetzen(RaumverwaltungUpdaten update) {

    }

    @Override
    public void spielUpdaterSetzen(SpielUpdaten update) {

    }
}
