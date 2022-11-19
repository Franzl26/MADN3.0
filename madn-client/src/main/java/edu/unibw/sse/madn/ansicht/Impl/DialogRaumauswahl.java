package edu.unibw.sse.madn.ansicht.Impl;

import edu.unibw.sse.madn.base.Warteraum;
import edu.unibw.sse.madn.clientKomm.AllgemeinerReturnWert;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class DialogRaumauswahl extends AnchorPane {
    private final ListView<HBox> roomsList;
    private final QuerschnittLogik querschnittLogik;
    private final GraphicsContext gcName;

    public DialogRaumauswahl(QuerschnittLogik querschnittLogik) {
        this.querschnittLogik = querschnittLogik;

        Canvas nameCanvas = new Canvas(300, 40);
        gcName = nameCanvas.getGraphicsContext2D();

        roomsList = new ListView<>();
        roomsList.setPrefWidth(800);

        Button newGameButton = new Button("Neuen Warteraum erstellen");
        newGameButton.addEventHandler(ActionEvent.ACTION, e -> {
            querschnittLogik.setAktuellerWarteraum(-2);
            AllgemeinerReturnWert ret = querschnittLogik.getClientKomm().warteraumErstellen();
            switch (ret) {
                case FEHLER -> {
                    Meldungen.zeigeInformation("Maximale Raumanzahl bereits erreicht", "Die maximale Anzahl an Warteräumen ist erreicht, es kann kein neuer erstellt werden.");
                    querschnittLogik.setAktuellerWarteraum(-1);
                }
                case ERFOLGREICH -> {
                    querschnittLogik.dialogWarteraumOeffnen();
                    getScene().getWindow().hide();
                }
                case VERBINDUNG_ABGEBROCHEN -> {
                    Meldungen.kommunikationAbgebrochen();
                    System.exit(-1);
                }
            }
        });
        Button exitButton = new Button("Beenden");
        exitButton.addEventHandler(ActionEvent.ACTION, e -> beenden());

        AnchorPane.setLeftAnchor(nameCanvas, 10.0);
        AnchorPane.setTopAnchor(nameCanvas, 10.0);
        AnchorPane.setLeftAnchor(roomsList, 10.0);
        AnchorPane.setTopAnchor(roomsList, 60.0);
        AnchorPane.setLeftAnchor(newGameButton, 10.0);
        AnchorPane.setBottomAnchor(newGameButton, 10.0);
        AnchorPane.setRightAnchor(exitButton, 10.0);
        AnchorPane.setBottomAnchor(exitButton, 10.0);

        getChildren().addAll(nameCanvas, roomsList, newGameButton, exitButton);
    }

    void displayRooms(Warteraum[] raume) {
        roomsList.getItems().clear();
        for (Warteraum r : raume) {
            HBox box = new HBox();
            Canvas canvas = new Canvas(700, 30);
            GraphicsContext gc = canvas.getGraphicsContext2D();
            Button button = new Button("Beitreten");
            button.addEventHandler(ActionEvent.ACTION, e -> {
                AllgemeinerReturnWert ret = querschnittLogik.getClientKomm().warteraumBeitreten(r.id());
                switch (ret) {
                    case FEHLER ->
                            Meldungen.zeigeInformation("Warteraum ist voll", "Der Warteraum ist bereits voll, du kannst diesem leider nicht beitreten");
                    case ERFOLGREICH -> {
                        querschnittLogik.setAktuellerWarteraum(r.id());
                        querschnittLogik.dialogWarteraumOeffnen();
                        getScene().getWindow().hide();
                    }
                    case VERBINDUNG_ABGEBROCHEN -> {
                        Meldungen.kommunikationAbgebrochen();
                        System.exit(-1);
                    }
                }
            });

            String[] players = r.namen();
            StringBuilder build = new StringBuilder();
            build.append(players.length).append("/4    ");
            for (String player : players) {
                build.append(player).append("  ");
            }
            gc.setLineWidth(1.0);
            gc.setFont(Font.font(20));
            gc.fillText(build.toString(), 5, 20, 700);
            button.setAlignment(Pos.CENTER_RIGHT);
            box.getChildren().addAll(canvas, button);

            roomsList.getItems().add(box);
        }
    }

    private void setOnClose() {
        getScene().getWindow().setOnCloseRequest(e -> {
            beenden();
            e.consume();
        });

    }

    private void beenden() {
        if (Meldungen.frageBestaetigung("Spiel beenden", "Willst du das Spiel wirklich beenden?")) {
            querschnittLogik.getClientKomm().abmelden();
            System.exit(0);
        }
    }

    public static DialogRaumauswahl dialogRaumauswahlStart(QuerschnittLogik querschnittLogik) {
        DialogRaumauswahl root = new DialogRaumauswahl(querschnittLogik);
        Scene scene = new Scene(root, 820, 500);
        Stage stage = new Stage();

        stage.setTitle("Raumauswahl");
        stage.setScene(scene);
        stage.setResizable(false);
        root.setOnClose();
        return root;
    }

    void anzeigen() {
        ((Stage) getScene().getWindow()).show();
    }

    public void namenSetzen(String benutzername) {
        gcName.setFont(Font.font(30));
        gcName.fillText(benutzername, 5, 30, 300);
    }
}
