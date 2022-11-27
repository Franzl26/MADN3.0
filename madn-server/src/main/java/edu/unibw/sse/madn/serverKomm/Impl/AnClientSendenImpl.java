package edu.unibw.sse.madn.serverKomm.Impl;

import edu.unibw.sse.madn.base.FeldBesetztStatus;
import edu.unibw.sse.madn.base.Spielstatistik;
import edu.unibw.sse.madn.base.Warteraum;
import edu.unibw.sse.madn.komm.Sitzung;
import edu.unibw.sse.madn.spielLogik.AnClientSendenSpiel;
import edu.unibw.sse.madn.warteraumverwaltung.AnClientSendenRaumauswahl;

import java.rmi.RemoteException;

public class AnClientSendenImpl implements AnClientSendenSpiel, AnClientSendenRaumauswahl {
    private final ServerKommunikationImpl serverKommunikation;

    public AnClientSendenImpl(ServerKommunikationImpl serverKommunikation) {
        this.serverKommunikation = serverKommunikation;

    }

    @Override
    public void spielfeldUpdaten(String[] benutzernamen, FeldBesetztStatus[] feld, int[] geandert) {
        for (String s : benutzernamen) {
            if (s == null) continue;
            new Thread(() -> {
                Sitzung benutzer = serverKommunikation.benutzerHolen(s);
                try {
                    if (benutzer != null) benutzer.clientCallback().spielfeldUpdaten(feld, geandert);
                } catch (RemoteException e) {
                    serverKommunikation.benutzerAbmelden(s);
                }
            }).start();
        }
    }

    @Override
    public void spielNamenUpdaten(String[] benutzernamen, String[] namen) {
        for (String s : benutzernamen) {
            if (s == null) continue;
            new Thread(() -> {
                Sitzung benutzer = serverKommunikation.benutzerHolen(s);
                try {
                    if (benutzer != null) benutzer.clientCallback().spielNamenUpdaten(namen);
                } catch (RemoteException e) {
                    serverKommunikation.benutzerAbmelden(s);
                }
            }).start();
        }
    }

    @Override
    public void aktuellenSpielerSetzen(String[] benutzernamen, int spieler) {
        for (String s : benutzernamen) {
            if (s == null) continue;
            new Thread(() -> {
                Sitzung benutzer = serverKommunikation.benutzerHolen(s);
                try {
                    if (benutzer != null) benutzer.clientCallback().aktuellenSpielerSetzen(spieler);
                } catch (RemoteException e) {
                    serverKommunikation.benutzerAbmelden(s);
                }
            }).start();
        }
    }

    @Override
    public void wuerfelUpdaten(String[] benutzernamen, int wert) {
        for (String s : benutzernamen) {
            if (s == null) continue;
            new Thread(() -> {
                Sitzung benutzer = serverKommunikation.benutzerHolen(s);
                try {
                    if (benutzer != null) benutzer.clientCallback().wuerfelUpdaten(wert);
                } catch (RemoteException e) {
                    serverKommunikation.benutzerAbmelden(s);
                }
            }).start();
        }
    }

    @Override
    public void wuerfelnVorbei(String benutzername) {
        Sitzung benutzer = serverKommunikation.benutzerHolen(benutzername);
        try {
            if (benutzer != null) benutzer.clientCallback().wuerfelnVorbei();
        } catch (RemoteException e) {
            serverKommunikation.benutzerAbmelden(benutzername);
        }
    }

    @Override
    public void ziehenVorbei(String benutzername) {
        Sitzung benutzer = serverKommunikation.benutzerHolen(benutzername);
        try {
            if (benutzer != null) benutzer.clientCallback().ziehenVorbei();
        } catch (RemoteException e) {
            serverKommunikation.benutzerAbmelden(benutzername);
        }
    }

    @Override
    public void gifAnzeigen(String benutzername) {
        Sitzung benutzer = serverKommunikation.benutzerHolen(benutzername);
        try {
            if (benutzer != null) benutzer.clientCallback().gifAnzeigen();
        } catch (RemoteException e) {
            serverKommunikation.benutzerAbmelden(benutzername);
        }
    }

    @Override
    public void spielVorbei(String[] benutzernamen, Spielstatistik statistik) {
        for (String s : benutzernamen) {
            new Thread(() -> {
                Sitzung benutzer = serverKommunikation.benutzerHolen(s);
                try {
                    if (benutzer != null) benutzer.clientCallback().spielVorbei(statistik);
                } catch (RemoteException e) {
                    serverKommunikation.benutzerAbmelden(s);
                }
            }).start();
        }
    }

    @Override
    public void nachrichtSenden(String[] benutzernamen, String nachricht) {
        for (String s : benutzernamen) {
            if (s == null) continue;
            new Thread(() -> {
                Sitzung benutzer = serverKommunikation.benutzerHolen(s);
                try {
                    if (benutzer != null) benutzer.clientCallback().nachrichtSenden(nachricht);
                } catch (RemoteException e) {
                    serverKommunikation.benutzerAbmelden(s);
                }
            }).start();
        }
    }

    @Override
    public void raeumeUpdaten(Warteraum[] warteraeume) {
        for (Sitzung s : serverKommunikation.alleBenutzerHolen()) {
            new Thread(() -> {
                try {
                    s.clientCallback().raeumeUpdaten(warteraeume);
                } catch (RemoteException e) {
                    serverKommunikation.benutzerAbmelden(s);
                }
            }).start();
        }
    }
}
