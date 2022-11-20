package edu.unibw.sse.madn.ansicht.Impl;

import edu.unibw.sse.madn.base.FeldBesetztStatus;
import edu.unibw.sse.madn.base.Spielstatistik;
import edu.unibw.sse.madn.base.WuerfelnRueckgabe;
import edu.unibw.sse.madn.base.ZiehenRueckgabe;
import edu.unibw.sse.madn.clientKomm.SpielUpdaten;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.Arrays;

import static edu.unibw.sse.madn.ansicht.Impl.BrettZeichnen.drawBoardAll;
import static edu.unibw.sse.madn.ansicht.Impl.BrettZeichnen.drawBoardSingleFieldAll;
import static edu.unibw.sse.madn.base.FeldBesetztStatus.FELD_LEER;

public class DialogSpiel extends AnchorPane implements SpielUpdaten {
    private SpielfeldKonfigurationIntern config;
    private final GraphicsContext gcBoard;
    private final GraphicsContext gcDice;
    private final GraphicsContext gcName;
    private final Canvas gifCanvas;
    private final MediaView gifView;
    private final AnsichtImpl ansichtImpl;
    private final FigurZiehen figurZiehen = new FigurZiehen();
    private FeldBesetztStatus[] brettStatus = new FeldBesetztStatus[72];
    private String[] namen = new String[0];
    private int aktiverSpieler = 0;

    public DialogSpiel(AnsichtImpl ansichtImpl, SpielfeldKonfigurationIntern config) {
        this.ansichtImpl = ansichtImpl;
        this.config = config;

        ansichtImpl.getClientKomm().spielUpdaterSetzen(this);
        Arrays.fill(brettStatus, FELD_LEER);

        setBackground(Background.fill(Color.LIGHTSLATEGRAY));
        Canvas nameCanvas = new Canvas(980, 50);
        gcName = nameCanvas.getGraphicsContext2D();

        Canvas diceCanvas = new Canvas(100, 100);
        gcDice = diceCanvas.getGraphicsContext2D();
        diceCanvas.setOnMouseClicked(e -> {
            WuerfelnRueckgabe ret = ansichtImpl.getClientKomm().wuerfeln();
            switch (ret) {
                case NICHT_DRAN -> Meldungen.zeigeInformation("Nicht am Zug", "Warte mit würfeln bis du am Zug bist!");
                case FALSCHE_PHASE ->
                        Meldungen.zeigeInformation("Erst Ziehen", "Du musst zuerst eine Spielfigur bewegen bevor du erneut würfeln darfst!");
                case ERFOLGREICH, KEIN_ZUG_MOEGLICH -> {
                }
                case VERBINDUNG_ABGEBROCHEN -> {
                    Meldungen.kommunikationAbgebrochen();
                    System.exit(-1);
                }
            }
        });

        Canvas boardCanvas = new Canvas(500, 500);
        gcBoard = boardCanvas.getGraphicsContext2D();
        boardCanvas.setOnMouseClicked(e -> figurZiehen.onMouseClickedField(e.getX(), e.getY()));

        gifCanvas = new Canvas(360, 360);
        GraphicsContext gcGif = gifCanvas.getGraphicsContext2D();
        gcGif.setFill(Color.LIGHTSLATEGRAY);
        gcGif.fillRect(0, 0, 360, 360);

        gifView = new MediaView();
        gifView.setFitWidth(360);
        gifView.setFitHeight(360);

        Button spielVerlassenButton = new Button("Spiel verlassen");
        spielVerlassenButton.addEventHandler(ActionEvent.ACTION, e -> spielVerlassen());

        AnchorPane.setLeftAnchor(nameCanvas, 10.0);
        AnchorPane.setTopAnchor(nameCanvas, 10.0);
        AnchorPane.setLeftAnchor(diceCanvas, 10.0);
        AnchorPane.setBottomAnchor(diceCanvas, 210.0);
        AnchorPane.setLeftAnchor(boardCanvas, 120.0);
        AnchorPane.setBottomAnchor(boardCanvas, 10.0);
        AnchorPane.setRightAnchor(gifCanvas, 10.0);
        AnchorPane.setTopAnchor(gifCanvas, 90.0);
        AnchorPane.setRightAnchor(gifView, 10.0);
        AnchorPane.setTopAnchor(gifView, 90.0);
        AnchorPane.setRightAnchor(spielVerlassenButton, 10.0);
        AnchorPane.setBottomAnchor(spielVerlassenButton, 10.0);

        getChildren().addAll(nameCanvas, boardCanvas, diceCanvas, gifCanvas, gifView, spielVerlassenButton);
        drawDiceIntern(0);
        drawBoardIntern(brettStatus);
    }

    public void setConfig(SpielfeldKonfigurationIntern config) {
        this.config = config;
    }

    public void loeschen() {
        FeldBesetztStatus[] feld = new FeldBesetztStatus[72];
        Arrays.fill(feld, FELD_LEER);
        drawBoardIntern(feld);
        drawDiceIntern(0);
        drawNamesIntern(new String[]{}, -1);
    }

    private void drawDiceIntern(int number) {
        gcDice.setFill(Color.LIGHTSLATEGRAY);
        gcDice.fillRect(0, 0, 100, 100);
        gcDice.drawImage(config.dice[number], 0, 0, 100, 100);
    }

    private void drawBoardIntern(FeldBesetztStatus[] board) {
        drawBoardAll(gcBoard, config, board);
    }

    private void drawBoardSingleFieldIntern(FeldBesetztStatus state, int i, boolean highlight) {
        drawBoardSingleFieldAll(gcBoard, config, state, i, highlight);
    }

    private void drawNamesIntern(String[] players, int turn) {
        if (players == null) return;
        gcName.setFill(Color.LIGHTSLATEGRAY);
        gcName.fillRect(0, 0, 980, 50);
        gcName.setFont(Font.font(40));
        gcName.setFill(Color.BLACK);
        for (int i = 0; i < players.length; i++) {
            gcName.drawImage(config.figure[(players.length == 2 ? (i == 0 ? 0 : 2) : i)], 5 + 245 * i, 5, 40, 40);
            String p = players[i];
            gcName.fillText(p, i * 245 + 50, 40, 190);
            if (i == turn) gcName.fillRect(i * 245 + 5, 46, 235, 47);
        }
    }

    private void showGif() {
        gifView.toFront();
        Media media = ansichtImpl.zufaelligesGif();
        MediaPlayer player = new MediaPlayer(media);
        gifView.setMediaPlayer(player);
        player.setAutoPlay(true);
        player.setCycleCount(50);
    }

    private void hideGif() {
        gifCanvas.toFront();
    }

    private void spielVerlassen() {
        if (Meldungen.frageBestaetigung("Willst du dass Spiel wirklich verlassen?", "Ein Wiedereinstieg in das laufende Spiel ist nicht möglich!")) {
            Spielstatistik statistik = ansichtImpl.getClientKomm().spielVerlassen();
            if (statistik == null) {
                Meldungen.kommunikationAbgebrochen();
                System.exit(-1);
            }
            ansichtImpl.dialogSpielstatistikOeffnen(statistik);
            getScene().getWindow().hide();
        }
    }

    private void setOnClose() {
        getScene().getWindow().setOnCloseRequest(e -> {
            spielVerlassen();
            e.consume();
        });
    }

    public static DialogSpiel dialogSpielStart(AnsichtImpl ansichtImpl, SpielfeldKonfigurationIntern config) {
        DialogSpiel root = new DialogSpiel(ansichtImpl, config);
        Scene scene = new Scene(root, 1000, 600);
        Stage stage = new Stage();

        stage.setTitle("Mensch Ärgere dich nicht");
        stage.setScene(scene);
        stage.setResizable(false);
        root.setOnClose();
        return root;
    }

    void anzeigen() {
        ((Stage) getScene().getWindow()).show();
    }

    @Override
    public void spielfeldUpdaten(FeldBesetztStatus[] feld, int[] geandert) {
        brettStatus = feld;
        Platform.runLater(() -> {
            hideGif();
            drawDiceIntern(0);
            if (geandert == null) drawBoardIntern(feld);
            else for (int i : geandert) if (i != -1) drawBoardSingleFieldIntern(feld[i], i, false);
        });
    }

    @Override
    public void spielNamenUpdaten(String[] namen) {
        this.namen = namen;
        Platform.runLater(() -> drawNamesIntern(namen, aktiverSpieler));
    }

    @Override
    public void aktuellenSpielerSetzen(int spieler) {
        aktiverSpieler = spieler;
        Platform.runLater(() -> drawNamesIntern(namen, aktiverSpieler));
    }

    @Override
    public void wuerfelUpdaten(int wert) {
        Platform.runLater(() -> drawDiceIntern(wert));
    }

    @Override
    public void wuerfelnVorbei() {
        Platform.runLater(() -> Meldungen.zeigeInformation("Zeit abgelaufen", "Deine Zeit zum Würfeln ist abgelaufen, der Server hat für dich gewürfelt und gezogen."));
    }

    @Override
    public void ziehenVorbei() {
        Platform.runLater(() -> Meldungen.zeigeInformation("Zeit abgelaufen", "Deine Zeit zum Ziehen ist abgelaufen, der Server hat für dich gezogen."));
    }

    @Override
    public void gifAnzeigen() {
        Platform.runLater(this::showGif);
    }

    @Override
    public void spielVorbei(Spielstatistik statistik) {
        Platform.runLater(() -> {
            ansichtImpl.dialogSpielstatistikOeffnen(statistik);
            getScene().getWindow().hide();
        });
    }

    private class FigurZiehen {
        private boolean highlighted = false;
        private int highlightedField = -1;

        public void onMouseClickedField(double x, double y) {
            int anzahl = config.sechser ? 96 : 72;
            int size = config.sechser ? 15 : 17;
            for (int i = 0; i < anzahl; i++) {
                int[] koords = config.pointCoordinates[i];
                if (Math.hypot(x - koords[0], y - koords[1]) < size - 2) {
                    //System.out.println("Field clicked: " + i);
                    if (!highlighted && brettStatus[i] == FELD_LEER) return;
                    if (!highlighted) {
                        highlighted = true;
                        highlightedField = i;
                        drawBoardSingleFieldIntern(brettStatus[i], i, true);
                    } else {
                        if (highlightedField == i) {
                            highlighted = false;
                            highlightedField = -1;
                            drawBoardSingleFieldIntern(brettStatus[i], i, false);
                        } else {
                            drawBoardSingleFieldIntern(brettStatus[highlightedField], highlightedField, false);
                            ZiehenRueckgabe ret = ansichtImpl.getClientKomm().figurZiehen(highlightedField, i);
                            switch (ret) {
                                case BESTRAFT ->
                                        Meldungen.zeigeInformation("Priorität missachtet", "Spielzugpriorität wurde nicht eingehalten!\nAbrücken oder Schlagen nicht beachtet.\nDeine Figur wird auf die Startposition zurückgesetzt!");
                                case NICHT_DRAN ->
                                        Meldungen.zeigeInformation("Nicht am Zug", "Warte bis du am Zug bist!");
                                case ERFOLGREICH -> {
                                }
                                case ZUG_FEHLERHAFT ->
                                        Meldungen.zeigeInformation("Fehlerhafter Zug", "Der ausgeführte Spielzug entspricht nicht dem Regeln!\nNochmal ziehen");
                                case NICHT_GEWUERFELT ->
                                        Meldungen.zeigeInformation("Nicht gewürfelt", "Du musst zuerst würfeln, bevor du deine Figur bewegen kannst!");
                                case VERBINDUNG_ABGEBROCHEN -> {
                                    Meldungen.kommunikationAbgebrochen();
                                    System.exit(-1);
                                }
                            }
                            highlighted = false;
                            highlightedField = -1;
                        }
                    }
                    return;
                }
            }
        }
    }
}
