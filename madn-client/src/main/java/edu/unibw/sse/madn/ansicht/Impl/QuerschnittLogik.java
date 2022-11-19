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
import java.util.HashMap;

public class QuerschnittLogik implements Ansicht, RaumverwaltungUpdaten {
    private DatenClient datenClient;
    private ClientKomm clientKomm;
    private String benutzername = "";

    private String ausgewaehltesDesign = "Standard";
    private long aktuellerWarteraum = -1;

    public String getBenutzername() {
        return benutzername;
    }

    public void setAusgewaehltesDesign(String ausgewaehltesDesign) {
        this.ausgewaehltesDesign = ausgewaehltesDesign;
    }

    public void setAktuellerWarteraum(long aktuellerWarteraum) {
        this.aktuellerWarteraum = aktuellerWarteraum;
    }

    @Override
    public void dateizugriffClientSetzen(DatenClient datenClient) {
        this.datenClient = datenClient;
        SpielfeldKonfiguration config = datenClient.konfigurationLaden("Standard");
        if (config == null) {
            Meldungen.zeigeInformation("Fehler beim Starten","Standarddesign konnte nicht geladen werden");
            System.exit(-1);
        }
        designs.put("Standard", new SpielfeldKonfigurationIntern(config));
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
        dialogAnmelden.anzeigen();
        dialogRaumauswahl = DialogRaumauswahl.dialogRaumauswahlStart(this);
    }

    private final HashMap<String, SpielfeldKonfigurationIntern> designs = new HashMap<>();

    /**
     * @param design null: wie gespeichert, sonst wie Parameter
     */
    SpielfeldKonfigurationIntern spielfeldKonfigurationLaden(String design) {
        if (design == null) design = ausgewaehltesDesign;
        if (!designs.containsKey(design)) {
            SpielfeldKonfiguration config = datenClient.konfigurationLaden(design);
            if (config == null) {
                config = clientKomm.spielfeldKonfigurationHolen(design);
                if (config != null) {
                    datenClient.KonfigurationSpeichern(config, design);
                } else {
                    design = "Standard";
                }
            }
            if (config != null) designs.put(design, new SpielfeldKonfigurationIntern(config));
        }
        return designs.get(design);
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

    void dialogRegistrierenOeffnen() {
        if (dialogRegistrieren == null) {
            dialogRegistrieren = DialogRegistrieren.dialogRegistrierenStart(this);
        }
        dialogRegistrieren.anzeigen();
    }

    void dialogAnmeldenOeffnen() {
        dialogAnmelden.anzeigen();
    }

    void erfolgreichAngemeldet(String benutzername) {
        this.benutzername = benutzername;
        ((Stage) dialogAnmelden.getScene().getWindow()).close();
        ((Stage) dialogRegistrieren.getScene().getWindow()).close();
        dialogAnmelden = null;
        dialogRegistrieren = null;
    }

    void dialogRaumauswahlOeffnen() {
        dialogRaumauswahl.anzeigen();
    }

    void dialogDesignauswahlOeffnen() {
        if (dialogDesignauswahl == null) {
            dialogDesignauswahl = DialogDesignauswahl.dialogDesignauswahlStart(this);
        }
        String[] designs = clientKomm.designListeHolen();
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
            dialogSpiel = DialogSpiel.dialogSpielStart(this, designs.get("Standard"));
        }
        dialogSpiel.setConfig(spielfeldKonfigurationLaden(ausgewaehltesDesign));
        dialogSpiel.loeschen();
        dialogSpiel.anzeigen();
    }

    void dialogSpielstatistikOeffnen(Spielstatistik spielstatistik) {
        DialogSpielstatistik.dialogSpielstatistikStart(this, spielstatistik);
    }

    void dialogWarteraumOeffnen() {
        if (dialogWarteraum == null) {
            dialogWarteraum = DialogWarteraum.dialogWarteraumStart(this);
        }
        String[] namen = namenAktuellerRaum();
        if (namen != null) dialogWarteraum.drawNames(namen);
        dialogWarteraum.anzeigen();
    }

    private Warteraum[] warteraeume;

    @Override
    public void raeumeUpdaten(Warteraum[] warteraeumeUeb) {
        warteraeume = warteraeumeUeb;
        Platform.runLater(() -> {
            dialogRaumauswahl.displayRooms(warteraeume);
            String[] namen = namenAktuellerRaum();
            if (namen != null) dialogWarteraum.drawNames(namen);
        });
    }

    private String[] namenAktuellerRaum() {
        if (aktuellerWarteraum != -1) {
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
}
