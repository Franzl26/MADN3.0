package edu.unibw.sse.madn.komm;

import edu.unibw.sse.madn.base.RegistrierenRueckgabe;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.security.PublicKey;

public interface ServerVerbindung extends Remote {
    Sitzung anmelden(ClientCallback client, String benutzername, byte[] passwort) throws RemoteException;

    RegistrierenRueckgabe registrieren(String benutzername, byte[] pw1, byte[] pw2) throws RemoteException;

    PublicKey oeffenltichenSchluesselHolen() throws RemoteException;
}
