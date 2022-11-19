package edu.unibw.sse.madn.ansicht.Impl;

import edu.unibw.sse.madn.clientKomm.AllgemeinerReturnWert;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class DialogWarteraum extends AnchorPane {
    private final GraphicsContext gcName;
    private final QuerschnittLogik querschnittLogik;

    public DialogWarteraum(QuerschnittLogik querschnittLogik) {
        this.querschnittLogik = querschnittLogik;

        Canvas nameCanvas = new Canvas(200, 150);
        gcName = nameCanvas.getGraphicsContext2D();

        Button botAddButton = new Button("Bot hinzufügen");
        botAddButton.setPrefWidth(100);
        botAddButton.addEventHandler(ActionEvent.ACTION, e -> {
            AllgemeinerReturnWert ret = querschnittLogik.getClientKomm().botHinzufuegen();
            switch (ret) {
                case ERFOLGREICH -> {
                }
                case FEHLER ->
                        Meldungen.zeigeInformation("Warteraum voll", "Der Warteraum ist bereits voll, du kannst keinen Bot hinzufügen.");
                case VERBINDUNG_ABGEBROCHEN -> {
                    Meldungen.kommunikationAbgebrochen();
                    System.exit(-1);
                }
            }
        });
        Button botRemoveButton = new Button("Bot entfernen");
        botRemoveButton.setPrefWidth(100);
        botRemoveButton.addEventHandler(ActionEvent.ACTION, e -> {
            AllgemeinerReturnWert ret = querschnittLogik.getClientKomm().botEntfernen();
            switch (ret) {
                case ERFOLGREICH -> {
                }
                case FEHLER ->
                        Meldungen.zeigeInformation("Kein Bot im Warteraum", "Es befindet sich kein Bot im Warteraum, der entfernt werden kann.");
                case VERBINDUNG_ABGEBROCHEN -> {
                    Meldungen.kommunikationAbgebrochen();
                    System.exit(-1);
                }
            }
        });
        Button designButton = new Button("Spieldesign auswählen");
        designButton.setPrefWidth(140);
        designButton.addEventHandler(ActionEvent.ACTION, e -> querschnittLogik.dialogDesignauswahlOeffnen());
        Button startButton = new Button("Spiel starten");
        startButton.setPrefWidth(140);
        startButton.addEventHandler(ActionEvent.ACTION, e -> {
            AllgemeinerReturnWert ret = querschnittLogik.getClientKomm().spielStarten();
            switch (ret) {
                case ERFOLGREICH -> {
                    querschnittLogik.dialogSpielOeffnen();
                    getScene().getWindow().hide();
                }
                case FEHLER ->
                        Meldungen.zeigeInformation("Nicht genug Spieler im Warteraum", "Es sind weniger als 2 Spieler im Warteraum, dass Spiel kann nicht gestartet werden.");
                case VERBINDUNG_ABGEBROCHEN -> {
                    Meldungen.kommunikationAbgebrochen();
                    System.exit(-1);
                }
            }
        });
        Button exitButton = new Button("Warteraum verlassen");
        exitButton.setPrefWidth(140);
        exitButton.addEventHandler(ActionEvent.ACTION, e -> verlassen());

        AnchorPane.setLeftAnchor(nameCanvas, 10.0);
        AnchorPane.setTopAnchor(nameCanvas, 10.0);
        AnchorPane.setLeftAnchor(botAddButton, 10.0);
        AnchorPane.setBottomAnchor(botAddButton, 10.0);
        AnchorPane.setLeftAnchor(botRemoveButton, 120.0);
        AnchorPane.setBottomAnchor(botRemoveButton, 10.0);
        AnchorPane.setRightAnchor(exitButton, 10.0);
        AnchorPane.setBottomAnchor(exitButton, 10.0);
        AnchorPane.setRightAnchor(startButton, 10.0);
        AnchorPane.setBottomAnchor(startButton, 40.0);
        AnchorPane.setRightAnchor(designButton, 10.0);
        AnchorPane.setBottomAnchor(designButton, 100.0);

        getChildren().addAll(nameCanvas, botAddButton, botRemoveButton, designButton, startButton, exitButton);
    }

    private void setOnClose() {
        getScene().getWindow().setOnCloseRequest(e -> {
            verlassen();
            e.consume();
        });
    }

    private void verlassen() {
        if (Meldungen.frageBestaetigung("Warteraum verlassen", "Willst du den Warteraum wirklich verlassen und zur Raumauswahl zurückkehren?")) {
            querschnittLogik.getClientKomm().warteraumVerlassen();
            querschnittLogik.setAktuellerWarteraum(-1);
            querschnittLogik.dialogRaumauswahlOeffnen();
            getScene().getWindow().hide();
        }
    }

    void drawNames(String[] names) {
        gcName.clearRect(0, 0, 200, 150);
        gcName.setLineWidth(1.0);
        gcName.setFont(Font.font(20));
        gcName.setFill(Color.BLACK);
        for (int i = 0; i < names.length; i++) {
            gcName.fillText(names[i], 5, i * 30 + 20, 190);
        }
    }

    public static DialogWarteraum dialogWarteraumStart(QuerschnittLogik querschnittLogik) {
        DialogWarteraum root = new DialogWarteraum(querschnittLogik);
        Scene scene = new Scene(root, 400, 200);
        Stage stage = new Stage();

        stage.setTitle("Warteraum");
        stage.setScene(scene);
        stage.setResizable(false);
        root.setOnClose();
        return root;
    }

    void anzeigen() {
        ((Stage) getScene().getWindow()).show();
    }
}
