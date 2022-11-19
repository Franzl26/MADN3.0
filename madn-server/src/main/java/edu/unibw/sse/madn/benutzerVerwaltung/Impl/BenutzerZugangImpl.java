package edu.unibw.sse.madn.benutzerVerwaltung.Impl;

import edu.unibw.sse.madn.base.RegistrierenRueckgabe;
import edu.unibw.sse.madn.benutzerVerwaltung.BenutzerVerwaltung;
import edu.unibw.sse.madn.benutzerVerwaltung.BenutzerZugang;
import edu.unibw.sse.madn.datenServer.BenutzerDaten;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BenutzerZugangImpl implements BenutzerZugang, BenutzerVerwaltung {
    private BenutzerDaten benutzerDaten;
    private final BenutzerIntern benutzer;
    private final ArrayList<String> angemeldet = new ArrayList<>();

    public BenutzerZugangImpl() {
        benutzer = new BenutzerIntern();
        benutzer.benutzerKonvertieren(benutzerDaten.benutzerLaden());
        new Timer().schedule(new NutzerLoeschen(), 3600000, 3600000);
    }

    @Override
    public synchronized boolean anmelden(String benutzername, String passwort) {
        if (angemeldet.contains(benutzername)) return false;
        byte[] pwHash = hashPasswort(passwort.getBytes());
        if (!benutzer.passwortPruefen(benutzername, pwHash)) return false;
        angemeldet.add(benutzername);
        benutzerDaten.benutzerSpeichern(benutzer.getBenutzer(benutzername));
        return true;
    }

    @Override
    public RegistrierenRueckgabe registrieren(String benutzername, String passwort) {
        if (!checkUsernameGuidelines(benutzername)) return RegistrierenRueckgabe.NAME_NICHT_GUIDELINES;
        if (benutzer.nutzernameExistent(benutzername)) return RegistrierenRueckgabe.NAME_BEREITS_VERGEBEN;
        if (!checkPasswordGuidelines(passwort)) return RegistrierenRueckgabe.PASSWORT_NICHT_GUIDELINES;
        benutzer.nutzerHinzufuegen(benutzername, hashPasswort(passwort.getBytes()));
        benutzerDaten.benutzerSpeichern(benutzer.getBenutzer(benutzername));
        return RegistrierenRueckgabe.ERFOLGREICH;
    }

    @Override
    public synchronized void abmelden(String benutzername) {
        angemeldet.remove(benutzername);
    }

    private static byte[] hashPasswort(byte[] pw) {
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Passwort-Hash kann nicht gebildet werden");
        }
        md.update(pw);
        return md.digest();
    }

    private static final Pattern pwPattern1 = Pattern.compile("[!§$%&/()=?#a-zA-Z\\d]{8,15}");
    private static final Pattern pwPattern2 = Pattern.compile(".*[!§$%&/()=?#]+.*");
    private static final Pattern pwPattern3 = Pattern.compile(".*[a-zA-Z]+.*");
    private static final Pattern pwPattern4 = Pattern.compile(".*\\d+.*");
    private static final Pattern namePattern1 = Pattern.compile("[A-Za-z]{3,8}");
    private static final Pattern namePattern2 = Pattern.compile("([bB][oO][tT]).*");

    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    public static boolean checkPasswordGuidelines(String pw) {
        Matcher match1 = pwPattern1.matcher(pw);
        Matcher match2 = pwPattern2.matcher(pw);
        Matcher match3 = pwPattern3.matcher(pw);
        Matcher match4 = pwPattern4.matcher(pw);
        return match1.matches() && match2.matches() && match3.matches() && match4.matches();
    }

    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    public static boolean checkUsernameGuidelines(String name) {
        Matcher match1 = namePattern1.matcher(name);
        Matcher match2 = namePattern2.matcher(name);
        return match1.matches() && !match2.matches();
    }

    @Override
    public BenutzerZugang benutzerZugangHolen() {
        return this;
    }

    @Override
    public void benutzerDatenSetzen(BenutzerDaten benutzerDaten) {
        this.benutzerDaten = benutzerDaten;
    }

    private class NutzerLoeschen extends TimerTask {
        @Override
        public void run() {
            benutzer.alteNutzerLoeschen(benutzerDaten);
        }
    }
}
