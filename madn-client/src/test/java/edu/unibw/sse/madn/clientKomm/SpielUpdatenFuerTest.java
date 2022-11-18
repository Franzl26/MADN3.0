package edu.unibw.sse.madn.clientKomm;

import edu.unibw.sse.madn.base.FeldBesetztStatus;
import edu.unibw.sse.madn.base.Spielstatistik;

public class SpielUpdatenFuerTest implements SpielUpdaten {
    FeldBesetztStatus[] feld = null;
    int[] geandert = null;
    String[] spielerNamen = null;
    int aktiverSpieler = -1;
    int wuerfelWert = -1;
    Spielstatistik spielstatistik = null;

    @Override
    public void spielfeldUpdaten(FeldBesetztStatus[] feld, int[] geandert) {

    }

    @Override
    public void spielNamenUpdaten(String[] namen) {

    }

    @Override
    public void aktuellenSpielerSetzen(int spieler) {

    }

    @Override
    public void wuerfelUpdaten(int wert) {
        wuerfelWert = wert;
        notifyAll();
    }

    @Override
    public void wuerfelnVorbei() {

    }

    @Override
    public void ziehenVorbei() {

    }

    @Override
    public void gifAnzeigen() {

    }

    @Override
    public void spielVorbei(Spielstatistik statistik) {

    }

    public synchronized void waitForUpdate(long time) {
        try {
            wait(time);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
