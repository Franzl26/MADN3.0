package edu.unibw.sse.madn.clientKomm;

import edu.unibw.sse.madn.base.RegistrierenRueckgabe;
import edu.unibw.sse.madn.base.SpielfeldKonfiguration;
import edu.unibw.sse.madn.base.WuerfelnRueckgabe;
import edu.unibw.sse.madn.base.ZiehenRueckgabe;
import edu.unibw.sse.madn.komm.ServerVerbindung;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import static org.junit.jupiter.api.Assertions.*;

public abstract class ClientKommTest { // todo Werte der Funktionen anpassen
    private ClientKomm clientKomm;
    private final ServerVerbindungFuerTest serverVerbindung = new ServerVerbindungFuerTest();
    private final RaumverwaltungUpdatenFuerTest raumverwaltungUpdatenFuerTest = new RaumverwaltungUpdatenFuerTest();
    private final SpielUpdatenFuerTest spielUpdatenFuerTest = new SpielUpdatenFuerTest();

    abstract ServerVerbindung getServerVerbindung();
    abstract ClientKommunikation getClientKommunikation();


    {
        serverVerbindung.setServerVerbindung(getServerVerbindung());

        clientKomm.spielUpdaterSetzen(spielUpdatenFuerTest);
        clientKomm.raumverwaltungUpdaterSetzen(raumverwaltungUpdatenFuerTest);
    }

    @BeforeEach
    void initBeforeEach() {
        ClientKommunikation clientKommunikation = getClientKommunikation();
        clientKomm = clientKommunikation.clientKommunikationHolen();
    }

    @Test
    void testAnmeldenErfolgreich() {
        String name = "benutzername";
        String pw = "pw";
        AllgemeinerReturnWert result = clientKomm.anmelden("ip", name, pw);
        assertNotNull(serverVerbindung.clientCallback);
        assertEquals(AllgemeinerReturnWert.ERFOLGREICH, result);
        assertEquals(name, serverVerbindung.benutzernameAnmelden);
        assertEquals(pwVerschluesseln(pw), serverVerbindung.passwortAnmelden);
    }

    @Test
    void testAnmeldenFehler() {
        String name = "benutzername";
        String pw = "pw";
        AllgemeinerReturnWert result = clientKomm.anmelden("ip", name, pw);
        assertEquals(AllgemeinerReturnWert.FEHLER, result);
        assertEquals(name, serverVerbindung.benutzernameAnmelden);
        assertEquals(pwVerschluesseln(pw), serverVerbindung.passwortAnmelden);
    }

    @Test
    void testRegistrierenErfolgreich() {
        String name = "benutzername";
        String pw = "pw";
        RegistrierenRueckgabe result = clientKomm.registrieren("ip", name, pw);
        assertEquals(RegistrierenRueckgabe.ERFOLGREICH, result);
        assertEquals(name, serverVerbindung.benutzerNameRegistrieren);
        assertEquals(pwVerschluesseln(pw), serverVerbindung.passwortRegistrieren);
    }

    @Test
    void testDesignListe() {
        String[] result = clientKomm.designListeHolen();
        assertNotNull(result);
    }

    @Test
    void testSpielfeldKonfiguration() {
        SpielfeldKonfiguration feld = clientKomm.spielfeldKonfigurationHolen("Standard");
        assertNotNull(feld);
        assertNotNull(feld.position());
        assertNotNull(feld.board());
        assertNotNull(feld.pathNormal());
        assertNotNull(feld.rotation());
        assertNotNull(feld.path());
        assertNotNull(feld.dice());
        assertNotNull(feld.figure());
        assertNotNull(feld.figureHigh());
        assertNotNull(feld.personal());
    }

    @Test
    void testWarteraumErstellen() {
        AllgemeinerReturnWert result = clientKomm.warteraumErstellen();
        assertEquals(AllgemeinerReturnWert.ERFOLGREICH, result);
        raumverwaltungUpdatenFuerTest.waitForWarteraume(1000);
        assertNotNull(raumverwaltungUpdatenFuerTest.warteraeume);
    }

    @Test
    void testWuerfeln() {
        WuerfelnRueckgabe result = clientKomm.wuerfeln();
        assertEquals(WuerfelnRueckgabe.ERFOLGREICH, result);
        spielUpdatenFuerTest.waitForUpdate(1000);
        assertNotEquals(-1, spielUpdatenFuerTest.wuerfelWert);
    }

    @Test
    void testSpielzug() {
        ZiehenRueckgabe result = clientKomm.figurZiehen(0,32);
        assertEquals(ZiehenRueckgabe.ERFOLGREICH, result);
        spielUpdatenFuerTest.waitForUpdate(1000);
        assertNotNull(spielUpdatenFuerTest.feld);
        assertNotNull(spielUpdatenFuerTest.geandert);
    }

    byte[] pwVerschluesseln(String pw) {
        byte[] chiffrat;
        try {
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE, serverVerbindung.keyPair.getPublic());
            chiffrat = cipher.doFinal(pw.getBytes());
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | IllegalBlockSizeException | BadPaddingException |
                 InvalidKeyException e) {
            return null;
        }
        return chiffrat;
    }
}
