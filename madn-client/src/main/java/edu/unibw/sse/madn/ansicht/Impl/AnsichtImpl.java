package edu.unibw.sse.madn.ansicht.Impl;

import edu.unibw.sse.madn.ansicht.Ansicht;
import edu.unibw.sse.madn.base.SpielfeldKonfiguration;
import edu.unibw.sse.madn.base.Spielstatistik;
import edu.unibw.sse.madn.base.Warteraum;
import edu.unibw.sse.madn.clientKomm.ClientKomm;
import edu.unibw.sse.madn.clientKomm.RaumverwaltungUpdaten;
import edu.unibw.sse.madn.datenClient.DatenClient;
import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.media.Media;
import javafx.stage.Stage;

import java.io.ByteArrayInputStream;
import java.util.Arrays;
import java.util.HashMap;

public class AnsichtImpl implements Ansicht, RaumverwaltungUpdaten {
    private DatenClient datenClient;
    private ClientKomm clientKomm;
    private String benutzername = "";

    private String ausgewaehltesDesign = "Standard";
    private long aktuellerWarteraum = -1;

    public void setAusgewaehltesDesign(String ausgewaehltesDesign) {
        this.ausgewaehltesDesign = ausgewaehltesDesign;
    }

    public void setAktuellerWarteraum(long aktuellerWarteraum) {
        this.aktuellerWarteraum = aktuellerWarteraum;
    }

    @Override
    public void dateizugriffClientSetzen(DatenClient datenClient) {
        this.datenClient = datenClient;
        SpielfeldKonfiguration config = datenClient.konfigurationLaden("Standard", false);
        if (config == null) {
            Meldungen.zeigeInformation("Fehler beim Starten","Standarddesign konnte nicht geladen werden");
            System.exit(-1);
        }
        designs4.put("Standard", new SpielfeldKonfigurationIntern(config));
        SpielfeldKonfiguration config2 = datenClient.konfigurationLaden("Standard", true);
        if (config2 == null) {
            Meldungen.zeigeInformation("Fehler beim Starten","Standarddesign konnte nicht geladen werden");
            System.exit(-1);
        }
        designs6.put("Standard", new SpielfeldKonfigurationIntern(config2));
        gifs = datenClient.alleGifsLaden();
    }

    @Override
    public void clientKommunikationSetzen(ClientKomm clientKomm) {
        this.clientKomm = clientKomm;
        clientKomm.raumverwaltungUpdaterSetzen(this);
    }

    @Override
    public void anwendungStarten() {
        dialogAnmelden = DialogAnmelden.dialogAnmeldenStart(this);
        dialogAnmelden.anzeigen(null, null);
    }

    private final HashMap<String, SpielfeldKonfigurationIntern> designs4 = new HashMap<>();
    private final HashMap<String, SpielfeldKonfigurationIntern> designs6 = new HashMap<>();

    SpielfeldKonfigurationIntern spielfeldKonfigurationLaden(String design, Boolean sechser) {
        if (sechser == null) sechser = this.sechser;
        if (design == null) design = ausgewaehltesDesign;
        if (sechser) {
            return spielfeldKonfigurationLadenIntern6(design);
        } else {
            return spielfeldKonfigurationLadenIntern4(design);
        }
    }

    private SpielfeldKonfigurationIntern spielfeldKonfigurationLadenIntern4(String design) {
        if (!designs4.containsKey(design)) {
            SpielfeldKonfiguration config = datenClient.konfigurationLaden(design, false);
            if (config == null) {
                config = clientKomm.spielfeldKonfigurationHolen(design, false);
                if (config != null) {
                    datenClient.KonfigurationSpeichern(config, design);
                } else {
                    design = "Standard";
                }
            }
            if (config != null && !design.equals("Standard")) designs4.put(design, new SpielfeldKonfigurationIntern(config));
        }
        return designs4.get(design);
    }

    private SpielfeldKonfigurationIntern spielfeldKonfigurationLadenIntern6(String design) {
        if (!designs6.containsKey(design)) {
            SpielfeldKonfiguration config = datenClient.konfigurationLaden(design, true);
            if (config == null) {
                config = clientKomm.spielfeldKonfigurationHolen(design, true);
                if (config != null) {
                    datenClient.KonfigurationSpeichern(config, design);
                } else {
                    design = "Standard";
                }
            }
            if (config != null && !design.equals("Standard")) designs6.put(design, new SpielfeldKonfigurationIntern(config));
        }
        return designs6.get(design);
    }

    private Media[] gifs;

    Media zufaelligesGif() {
        if (gifs == null || gifs.length == 0) return null;
        return gifs[(int) (Math.random()*gifs.length)];
    }

    private Image balkenBild;

    Image balkenBild() {
        if (balkenBild == null) balkenBild = new Image(new ByteArrayInputStream(datenClient.balkenBildLaden()));
        return balkenBild;
    }

    ClientKomm getClientKomm() {
        return clientKomm;
    }

    private DialogAnmelden dialogAnmelden;
    private DialogDesignauswahl dialogDesignauswahl;
    private DialogRaumauswahl dialogRaumauswahl;
    private DialogRegistrieren dialogRegistrieren;
    private DialogSpiel dialogSpiel;
    private DialogWarteraum dialogWarteraum;
    private DialogChat dialogChat;

    void dialogRegistrierenOeffnen(String ip, String name) {
        if (dialogRegistrieren == null) {
            dialogRegistrieren = DialogRegistrieren.dialogRegistrierenStart(this);
        }
        dialogRegistrieren.anzeigen(ip, name);
    }

    void dialogAnmeldenOeffnen(String ip, String name) {
        dialogAnmelden.anzeigen(ip, name);
    }

    void erfolgreichAngemeldet(String benutzername) {
        this.benutzername = benutzername;
        ((Stage) dialogAnmelden.getScene().getWindow()).close();
        if (dialogRegistrieren != null) ((Stage) dialogRegistrieren.getScene().getWindow()).close();
        dialogAnmelden = null;
        dialogRegistrieren = null;
    }

    void dialogRaumauswahlOeffnen() {
        if (dialogRaumauswahl == null) {
            dialogRaumauswahl = DialogRaumauswahl.dialogRaumauswahlStart(this);
        }
        dialogRaumauswahl.namenSetzen(benutzername);
        dialogRaumauswahl.anzeigen();
        if (dialogChat != null) {
            dialogChat.verstecken();
            dialogChat.chatLoeschen();
        }
    }

    void dialogDesignauswahlOeffnen() {
        if (dialogDesignauswahl == null) {
            dialogDesignauswahl = DialogDesignauswahl.dialogDesignauswahlStart(this);
        }
        String[] designs = clientKomm.designListeHolen(sechser);
        System.out.println(sechser + " designs: " + Arrays.toString(designs));
        //noinspection ReplaceNullCheck
        if (designs != null) {
            dialogDesignauswahl.designsAktualisieren(designs);
        } else {
            dialogDesignauswahl.designsAktualisieren(new String[]{"Standard"});
        }
        dialogDesignauswahl.anzeigen();
    }

    void dialogSpielOeffnen() {
        if (dialogSpiel == null) {
            dialogSpiel = DialogSpiel.dialogSpielStart(this, designs4.get("Standard"));
        }
        dialogSpiel.setConfig(spielfeldKonfigurationLaden(ausgewaehltesDesign, sechser));
        dialogSpiel.loeschen();
        dialogSpiel.anzeigen();
    }

    void dialogSpielstatistikOeffnen(Spielstatistik spielstatistik) {
        if (dialogChat != null) {
            dialogChat.verstecken();
            dialogChat.chatLoeschen();
        }
        DialogSpielstatistik.dialogSpielstatistikStart(this, spielstatistik);
    }

    void dialogWarteraumOeffnen() {
        if (dialogWarteraum == null) {
            dialogWarteraum = DialogWarteraum.dialogWarteraumStart(this);
        }
        String[] namen = namenAktuellerRaum();
        if (namen != null) dialogWarteraum.drawNames(namen);
        if (dialogChat == null) {
            dialogChat = DialogChat.dialogChatStart(this);
        }
        dialogChat.anzeigen();
        dialogWarteraum.anzeigen();
    }

    private Warteraum[] warteraeume;
    boolean sechser = false;

    @Override
    public void raeumeUpdaten(Warteraum[] warteraeumeUeb) {
        warteraeume = warteraeumeUeb;
        Platform.runLater(() -> {
            if (aktuellerWarteraum == -2) setAktuellerWarteraumAusListDurchNamen();
            dialogRaumauswahl.displayRooms(warteraeume);
            String[] namen = namenAktuellerRaum();
            if (namen != null) {
                dialogWarteraum.drawNames(namen);
                sechser = namen.length == 3 || namen.length > 4;
            }
        });
    }

    @Override
    public void nachrichtSenden(String nachricht) {
        Platform.runLater(() -> {
            if (dialogChat != null) dialogChat.nachrichtHinzufuegen(nachricht);
        });
    }

    private void setAktuellerWarteraumAusListDurchNamen() {
        for (Warteraum r : warteraeume) {
            for (String s : r.namen()) {
                if (s.equals(benutzername)) {
                    aktuellerWarteraum = r.id();
                    return;
                }
            }
        }
    }

    private String[] namenAktuellerRaum() {
        if (aktuellerWarteraum >= 0) {
            /*List<Warteraum> rest = Arrays.stream(warteraeume).filter(warteraum -> warteraum.id() == aktuellerWarteraum).toList();
            if (rest.size() > 0) {
                dialogWarteraum.drawNames(rest.get(0).namen());
            }*/
            for (Warteraum r : warteraeume) {
                if (r.id() == aktuellerWarteraum) return r.namen();
            }
        }
        return null;
    }

    public void startSpiel() {
        dialogSpiel = DialogSpiel.dialogSpielStart(this, designs6.get("Standard"));
        dialogSpiel.anzeigen();
    }
}
