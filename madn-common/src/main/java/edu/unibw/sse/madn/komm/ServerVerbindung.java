package edu.unibw.sse.madn.komm;

import edu.unibw.sse.madn.base.RegistrierenRueckgabe;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.security.PublicKey;

/**
 * Tom
 */
public interface ServerVerbindung extends Remote {
    /**
     * Benutzer anmelden
     *
     * @param client       Rückkanal zum Client
     * @param benutzername Benutzername
     * @param passwort     Passwort verschlüsselt
     * @return null: Fehler, sonst Sitzung
     */
    Sitzung anmelden(ClientCallback client, String benutzername, byte[] passwort) throws RemoteException;

    /**
     * Benutzer registrieren
     *
     * @param benutzername Benutzername
     * @param passwort     Passwort verschlüsselt
     * @return Fehler oder Erfolg
     */
    RegistrierenRueckgabe registrieren(String benutzername, byte[] passwort) throws RemoteException;

    /**
     * @return öffentlichen Schlüssel zur RSA-Verschlüsselung
     */
    PublicKey oeffenltichenSchluesselHolen() throws RemoteException;
}
