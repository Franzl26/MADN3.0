package edu.unibw.sse.madn.benutzerVerwaltung.Impl;

import edu.unibw.sse.madn.datenServer.Benutzer;
import edu.unibw.sse.madn.datenServer.BenutzerDaten;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class BenutzerIntern implements Serializable {
    private static final long NUTZER_LOESCHEN_NACH = 259200000; // 72h

    private final HashMap<String, Nutzer> user = new HashMap<>();

    synchronized void nutzerHinzufuegen(String benutzername, byte[] pwHash) {
        if (user.containsKey(benutzername)) return;
        user.put(benutzername, new Nutzer(pwHash));
    }

    synchronized boolean nutzernameExistent(String benutzername) {
        return user.containsKey(benutzername);
    }

    synchronized boolean passwortPruefen(String benutzername, byte[] pwHash) {
        Nutzer nutzer = user.get(benutzername);
        if (nutzer == null) return false;
        if (!Arrays.equals(pwHash, nutzer.passwordHash)) return false;
        nutzer.letzterLogin = System.currentTimeMillis();
        return true;
    }

    synchronized void alteNutzerLoeschen(BenutzerDaten benutzerDaten) {
        user.keySet().forEach(n -> {
            Nutzer u = user.get(n);
            if (u.letzterLogin + NUTZER_LOESCHEN_NACH < System.currentTimeMillis()) benutzerDaten.benutzerLoeschen(new Benutzer(n,u.passwordHash,u.letzterLogin));
        });
    }

    public Benutzer getBenutzer(String benutzername) {
        Nutzer u = user.get(benutzername);
        return new Benutzer(benutzername,u.passwordHash, u.letzterLogin);
    }


    private static class Nutzer implements Serializable {
        private final byte[] passwordHash;
        private long letzterLogin;

        public Nutzer(byte[] passwordHash) {
            this.passwordHash = passwordHash;
            letzterLogin = System.currentTimeMillis();
        }
    }

    void benutzerKonvertieren(Benutzer[] benutzer) {
        if (benutzer == null) return;
        for (Benutzer b : benutzer) {
            user.put(b.benutzername(), new Nutzer(b.passwortHash()));
        }
    }
}
