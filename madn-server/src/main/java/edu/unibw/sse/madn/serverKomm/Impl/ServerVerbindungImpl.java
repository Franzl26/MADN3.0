package edu.unibw.sse.madn.serverKomm.Impl;

import edu.unibw.sse.madn.base.RegistrierenRueckgabe;
import edu.unibw.sse.madn.benutzerVerwaltung.BenutzerZugang;
import edu.unibw.sse.madn.datenServer.SpielDesign;
import edu.unibw.sse.madn.komm.ClientCallback;
import edu.unibw.sse.madn.komm.ServerVerbindung;
import edu.unibw.sse.madn.komm.Sitzung;
import edu.unibw.sse.madn.spielLogik.Spiel;
import edu.unibw.sse.madn.warteraumverwaltung.Raumauswahl;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.security.*;

public class ServerVerbindungImpl extends UnicastRemoteObject implements ServerVerbindung {
    SpielDesign spielDesign;
    BenutzerZugang benutzerZugang;
    Raumauswahl raumauswahl;
    Spiel spiel;
    ServerKommunikationImpl serverKommunikation;
    private KeyPair keyPair;

    public ServerVerbindungImpl(ServerKommunikationImpl serverKommunikation) throws RemoteException {
        this.serverKommunikation = serverKommunikation;
        try {
            keyPair = KeyPairGenerator.getInstance("RSA").generateKeyPair();
        } catch (NoSuchAlgorithmException e) {
            System.err.println("Neues Schlüsselpaar konnte nicht erstellt werden");
            System.exit(-1);
        }
    }

    @Override
    public Sitzung anmelden(ClientCallback client, String benutzername, byte[] passwort) throws RemoteException {
        if (client == null || benutzername == null || passwort == null) return null;
        String pw = passwortEntschluesseln(passwort);
        if (pw == null) return null;
        if (!benutzerZugang.anmelden(benutzername, pw)) return null;
        Sitzung sitzung = new SitzungImpl(serverKommunikation, client, benutzername, spielDesign, benutzerZugang, raumauswahl, spiel);
        serverKommunikation.nutzerHinzufuegen(benutzername, sitzung);
        return sitzung;
    }

    @Override
    public RegistrierenRueckgabe registrieren(String benutzername, byte[] passwort) throws RemoteException {
        if (benutzername == null || passwort == null) return null;
        String pw = passwortEntschluesseln(passwort);
        if (pw == null) return RegistrierenRueckgabe.VERBINDUNG_ABGEBROCHEN;
        return benutzerZugang.registrieren(benutzername, pw);
    }

    @Override
    public PublicKey oeffenltichenSchluesselHolen() throws RemoteException {
        return keyPair.getPublic();
    }

    private String passwortEntschluesseln(byte[] pw) {
        try {
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE, keyPair.getPrivate());
            byte[] ent = cipher.doFinal(pw);
            return new String(ent);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException |
                 BadPaddingException e) {
            System.err.println("Passwort entschlüsseln nicht möglich");
            return null;
        }
    }
}
