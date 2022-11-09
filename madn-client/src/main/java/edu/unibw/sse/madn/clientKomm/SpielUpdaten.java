package edu.unibw.sse.madn.clientKomm;

import edu.unibw.sse.madn.base.FeldBesetztStatus;
import edu.unibw.sse.madn.base.SpielStatistik;

public interface SpielUpdaten {
    void spielfeldUpdaten(FeldBesetztStatus[] feld, int[] geandert);

    void spielNamenUpdaten(String[] namen);

    void aktuellenSpielerSetzen(int spieler);

    void wuerfelUpdaten(int wert);

    void wuerfelnVorbei();

    void ziehenVorbei();

    void gifAnzeigen();

    void spielVorbei(SpielStatistik statistik);
}
