package edu.unibw.sse.madn.spielLogik;

import edu.unibw.sse.madn.base.FeldBesetztStatus;
import edu.unibw.sse.madn.base.SpielStatistik;

public interface AnClientSendenSpiel {
    void spielfeldUpdaten(String benutzername, FeldBesetztStatus[] feld, int[] geandert);

    void spielNamenUpdaten(String benutzername, String[] namen);

    void aktuellenSpielerSetzen(String benutzername, int spieler);

    void wuerfelUpdaten(String benutzername, int wert);

    void wuerfelnVorbei(String benutzername);

    void ziehenVorbei(String benutzername);

    void gifAnzeigen(String benutzername);

    void spielVorbei(String benutzername, SpielStatistik statistik);
}
