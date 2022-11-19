package edu.unibw.sse.madn.spielLogik.Impl;

import edu.unibw.sse.madn.base.Spielstatistik;
import edu.unibw.sse.madn.base.WuerfelnRueckgabe;
import edu.unibw.sse.madn.base.ZiehenRueckgabe;
import edu.unibw.sse.madn.spielLogik.*;

import java.util.ArrayList;
import java.util.HashMap;

public class SpielLogikImpl implements Spiel, SpielErstellen, SpielLogik {
    private AnClientSendenSpiel anClient;
    private final HashMap<String, SpielObjekt> istInSpiel = new HashMap<>();
    private WarteraumCallback warteraumCallback;

    @Override
    public synchronized void spielErstellen(String[] benutzer, int bots, int spieler) {
        String[] neueBenutzer = new String[bots + spieler];
        if (bots >= 1) {
            neueBenutzer[0] = benutzer[0];
            System.arraycopy(benutzer, 1, neueBenutzer, 2, spieler - 1);
        } else {
            System.arraycopy(benutzer, 0, neueBenutzer, 0, spieler);
        }
        SpielObjekt spiel = new SpielObjekt(this, anClient, neueBenutzer, bots + spieler);
        for (String s : benutzer) {
            istInSpiel.put(s, spiel);
        }
    }

    @Override
    public void warteraumCallbackSetzen(WarteraumCallback warteraumCallback) {
        this.warteraumCallback = warteraumCallback;
    }

    @Override
    public synchronized ZiehenRueckgabe figurZiehen(String sitzung, int von, int nach) {
        SpielObjekt spiel = istInSpiel.get(sitzung);
        if (spiel == null) return ZiehenRueckgabe.VERBINDUNG_ABGEBROCHEN;
        return spiel.submitMove(sitzung, von, nach);
    }

    @Override
    public synchronized WuerfelnRueckgabe wuerfeln(String sitzung) {
        SpielObjekt spiel = istInSpiel.get(sitzung);
        if (spiel == null) return WuerfelnRueckgabe.VERBINDUNG_ABGEBROCHEN;
        return spiel.throwDice(sitzung);
    }

    @Override
    public synchronized Spielstatistik spielVerlassen(String sitzung) {
        SpielObjekt spiel = istInSpiel.get(sitzung);
        if (spiel == null) return null;
        return spiel.leaveGame(sitzung);
    }

    @Override
    public void anClientSendenSpielSetzen(AnClientSendenSpiel anClientSendenSpiel) {
        anClient = anClientSendenSpiel;
    }

    public synchronized void spielBeendet(SpielObjekt spielObjekt) {
        ArrayList<String> entfernen = new ArrayList<>();
        istInSpiel.keySet().forEach(s -> {
            if (istInSpiel.get(s).equals(spielObjekt)) entfernen.add(s);
        });
        entfernen.forEach(istInSpiel::remove);
        warteraumCallback.spielBeendet();
    }

    @Override
    public Spiel spielHolen() {
        return this;
    }

    @Override
    public SpielErstellen spielErstellenHolen() {
        return this;
    }
}
