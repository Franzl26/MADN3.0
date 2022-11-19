package edu.unibw.sse.madn.clientKomm.Impl;

import edu.unibw.sse.madn.base.*;
import edu.unibw.sse.madn.clientKomm.*;
import edu.unibw.sse.madn.komm.ServerVerbindung;
import edu.unibw.sse.madn.komm.Sitzung;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;

import static edu.unibw.sse.madn.clientKomm.AllgemeinerReturnWert.*;

public class ClientKommImpl implements ClientKomm, ClientKommunikation {
    private final ClientCallbackImpl clientCallback;
    private ServerVerbindung serverVerbindung;
    private Sitzung sitzung;

    public ClientKommImpl() {
        try {
            clientCallback = new ClientCallbackImpl();
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public AllgemeinerReturnWert anmelden(String ip, String benutzername, String passwort) {
        try {
            ServerVerbindung verbindung;
            if (serverVerbindung == null) {
                verbindung = (ServerVerbindung) Naming.lookup("//" + ip + "/" + "MADNLogin");
            } else {
                verbindung = serverVerbindung;
            }
            PublicKey key = verbindung.oeffenltichenSchluesselHolen();
            byte[] pw = passwortVerschluesseln(passwort, key);
            if (pw == null) return VERBINDUNG_ABGEBROCHEN;
            sitzung = verbindung.anmelden(clientCallback, benutzername, pw);
            if (sitzung == null) {
                return FEHLER;
            }
        } catch (NotBoundException | MalformedURLException | RemoteException e) {
            return VERBINDUNG_ABGEBROCHEN;
        }
        return ERFOLGREICH;
    }

    @Override
    public RegistrierenRueckgabe registrieren(String ip, String benutzername, String passwort) {
        try {
            ServerVerbindung verbindung;
            if (serverVerbindung == null) {
                verbindung = (ServerVerbindung) Naming.lookup("//" + ip + "/" + "MADNLogin");
            } else {
                verbindung = serverVerbindung;
            }
            PublicKey key = verbindung.oeffenltichenSchluesselHolen();
            byte[] pw = passwortVerschluesseln(passwort, key);
            if (pw == null) return RegistrierenRueckgabe.VERBINDUNG_ABGEBROCHEN;
            return verbindung.registrieren(benutzername, pw);
        } catch (NotBoundException | MalformedURLException | RemoteException e) {
            return RegistrierenRueckgabe.VERBINDUNG_ABGEBROCHEN;
        }
    }

    @Override
    public void abmelden() {
        try {
            sitzung.abmelden();
        } catch (RemoteException ignored) {
        }
    }

    @Override
    public String[] designListeHolen() {
        try {
            return sitzung.designListeHolen();
        } catch (RemoteException e) {
            return null;
        }
    }

    @Override
    public SpielfeldKonfiguration spielfeldKonfigurationHolen(String name) {
        try {
            return sitzung.spielfeldKonfigurationHolen(name);
        } catch (RemoteException e) {
            return null;
        }
    }

    @Override
    public AllgemeinerReturnWert warteraumErstellen() {
        try {
            return sitzung.warteraumErstellen() ? ERFOLGREICH : FEHLER;
        } catch (RemoteException e) {
            return VERBINDUNG_ABGEBROCHEN;
        }
    }

    @Override
    public AllgemeinerReturnWert warteraumBeitreten(long raumId) {
        try {
            return sitzung.warteraumBeitreten(raumId) ? ERFOLGREICH : FEHLER;
        } catch (RemoteException e) {
            return VERBINDUNG_ABGEBROCHEN;
        }
    }

    @Override
    public void warteraumVerlassen() {
        try {
            sitzung.warteraumVerlassen();
        } catch (RemoteException ignored) {
        }
    }

    @Override
    public AllgemeinerReturnWert botHinzufuegen() {
        try {
            return sitzung.botHinzufuegen() ? ERFOLGREICH : FEHLER;
        } catch (RemoteException e) {
            return VERBINDUNG_ABGEBROCHEN;
        }
    }

    @Override
    public AllgemeinerReturnWert botEntfernen() {
        try {
            return sitzung.botEntfernen() ? ERFOLGREICH : FEHLER;
        } catch (RemoteException e) {
            return VERBINDUNG_ABGEBROCHEN;
        }
    }

    @Override
    public AllgemeinerReturnWert spielStarten() {
        try {
            return sitzung.spielStarten() ? ERFOLGREICH : FEHLER;
        } catch (RemoteException e) {
            return VERBINDUNG_ABGEBROCHEN;
        }
    }

    @Override
    public ZiehenRueckgabe figurZiehen(int von, int nach) {
        try {
            return sitzung.figurZiehen(von, nach);
        } catch (RemoteException e) {
            return ZiehenRueckgabe.VERBINDUNG_ABGEBROCHEN;
        }
    }

    @Override
    public WuerfelnRueckgabe wuerfeln() {
        try {
            return sitzung.wuerfeln();
        } catch (RemoteException e) {
            return WuerfelnRueckgabe.VERBINDUNG_ABGEBROCHEN;
        }
    }

    @Override
    public Spielstatistik spielVerlassen() {
        try {
            return sitzung.spielVerlassen();
        } catch (RemoteException e) {
            return null;
        }
    }

    @Override
    public void raumverwaltungUpdaterSetzen(RaumverwaltungUpdaten update) {
        clientCallback.raumverwaltung = update;
    }

    @Override
    public void spielUpdaterSetzen(SpielUpdaten update) {
        clientCallback.spiel = update;
    }

    private byte[] passwortVerschluesseln(String passwort, PublicKey key) {
        byte[] chiffrat;
        try {
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE, key);
            chiffrat = cipher.doFinal(passwort.getBytes());
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | IllegalBlockSizeException | BadPaddingException |
                 InvalidKeyException e) {
            return null;
        }
        return chiffrat;
    }

    /**
     * Nur für Test benötigt
     * @param serverVerbindung Schnittstelle
     */
    @Override
    public void serverVerbindungSetzenTest(ServerVerbindung serverVerbindung) {
        this.serverVerbindung = serverVerbindung;
    }

    @Override
    public ClientKomm clientKommunikationHolen() {
        return this;
    }
}
