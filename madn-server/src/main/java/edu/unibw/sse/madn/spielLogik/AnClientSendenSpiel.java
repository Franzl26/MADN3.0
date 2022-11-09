package edu.unibw.sse.madn.spielLogik;

import edu.unibw.sse.madn.base.FeldBesetztStatus;
import edu.unibw.sse.madn.komm.Sitzung;
import edu.unibw.sse.madn.base.SpielStatistik;

public interface AnClientSendenSpiel {
    boolean spielfeldUpdaten(Sitzung sitzung, FeldBesetztStatus[] feld, int[] geandert);

    boolean spielNamenUpdaten(Sitzung sitzung, String[] namen);

    boolean aktuellenSpielerSetzen(Sitzung sitzung, int spieler);

    boolean wuerfelUpdaten(Sitzung sitzung, int wert);

    boolean wuerfelnVorbei(Sitzung sitzung);

    boolean ziehenVorbei(Sitzung sitzung);

    boolean gifAnzeigen(Sitzung sitzung);

    void spielVorbei(Sitzung sitzung, SpielStatistik statistik);
}
