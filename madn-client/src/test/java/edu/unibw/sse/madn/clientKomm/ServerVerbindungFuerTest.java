package edu.unibw.sse.madn.clientKomm;

import edu.unibw.sse.madn.base.*;
import edu.unibw.sse.madn.komm.ClientCallback;
import edu.unibw.sse.madn.komm.ServerVerbindung;
import edu.unibw.sse.madn.komm.Sitzung;

import java.rmi.RemoteException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;

public class ServerVerbindungFuerTest implements ServerVerbindung { // todo Rückgabewerte anpassen entsprechend Parameter
    private ServerVerbindung serverVerbindung;

    KeyPair keyPair;
    ClientCallback clientCallback;
    String benutzernameAnmelden;
    byte[] passwortAnmelden;
    String benutzerNameRegistrieren;
    byte[] passwortRegistrieren;

    public ServerVerbindungFuerTest() {
        try {
            keyPair = KeyPairGenerator.getInstance("RSA").generateKeyPair();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public void setServerVerbindung(ServerVerbindung serverVerbindung) {
        this.serverVerbindung = serverVerbindung;
    }

    @Override
    public Sitzung anmelden(ClientCallback client, String benutzername, byte[] passwort) throws RemoteException {
        clientCallback = client;
        benutzernameAnmelden = benutzername;
        passwortAnmelden = passwort;
        if (serverVerbindung == null) {
            return new SitzungTest();
        } else {
            return serverVerbindung.anmelden(client, benutzername, passwort);
        }
    }

    @Override
    public RegistrierenRueckgabe registrieren(String benutzername, byte[] passwort) throws RemoteException {
        benutzerNameRegistrieren = benutzername;
        passwortRegistrieren = passwort;
        if (serverVerbindung == null) {
            return RegistrierenRueckgabe.ERFOLGREICH;
        } else {
            return serverVerbindung.registrieren(benutzername, passwort);
        }
    }

    @Override
    public PublicKey oeffenltichenSchluesselHolen() throws RemoteException {
        if (serverVerbindung == null) {
            return keyPair.getPublic();
        } else {
            return serverVerbindung.oeffenltichenSchluesselHolen();
        }
    }

    private static class SitzungTest implements Sitzung {

        @Override
        public void abmelden() throws RemoteException {

        }

        @Override
        public String[] designListeHolen() throws RemoteException {
            return new String[0];
        }

        @Override
        public SpielfeldKonfiguration spielfeldKonfigurationHolen(String name) throws RemoteException {
            return null;
        }

        @Override
        public boolean warteraumErstellen() throws RemoteException {
            return false;
        }

        @Override
        public boolean warteraumBeitreten(long raumId) throws RemoteException {
            return false;
        }

        @Override
        public void warteraumVerlassen(long raumId) throws RemoteException {

        }

        @Override
        public boolean botHinzufuegen(long raumId) throws RemoteException {
            return false;
        }

        @Override
        public boolean botEntfernen(long raumId) throws RemoteException {
            return false;
        }

        @Override
        public boolean spielStarten(long raumId) throws RemoteException {
            return false;
        }

        @Override
        public ZiehenRueckgabe figurZiehen(int von, int nach) throws RemoteException {
            return null;
        }

        @Override
        public WuerfelnRueckgabe wuerfeln() throws RemoteException {
            return null;
        }

        @Override
        public Spielstatistik spielVerlassen() throws RemoteException {
            return null;
        }

        @Override
        public String benutzername() throws RemoteException {
            return null;
        }

        @Override
        public ClientCallback clientCallback() throws RemoteException {
            return null;
        }
    }
}
