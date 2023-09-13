package edu.unibw.sse.madn.ansicht.Impl;

import edu.unibw.sse.madn.clientKomm.AllgemeinerReturnWert;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class DialogChat extends AnchorPane {
    private final AnsichtImpl ansichtImpl;
    private final TextArea verlauf;
    private final TextField inputTextField;

    public DialogChat(AnsichtImpl ansichtImpl) {
        this.ansichtImpl = ansichtImpl;

        verlauf = new TextArea();
        verlauf.setEditable(false);
        verlauf.setPrefWidth(580);
        verlauf.setPrefHeight(340);

        inputTextField = new TextField();
        inputTextField.setPromptText("Hier Nachricht eingeben");
        inputTextField.setPrefHeight(20);
        inputTextField.setPrefWidth(470);

        Button sendButton = new Button("Senden");
        sendButton.setPrefWidth(100);
        sendButton.setPrefHeight(20);


        sendButton.addEventHandler(ActionEvent.ACTION, e -> nachrichtSenden());

        AnchorPane.setLeftAnchor(verlauf, 10.0);
        AnchorPane.setTopAnchor(verlauf, 10.0);
        AnchorPane.setLeftAnchor(inputTextField, 10.0);
        AnchorPane.setBottomAnchor(inputTextField, 10.0);
        AnchorPane.setRightAnchor(sendButton, 10.0);
        AnchorPane.setBottomAnchor(sendButton, 10.0);

        getChildren().addAll(verlauf, inputTextField, sendButton);

        addEventHandler(KeyEvent.KEY_PRESSED, e -> {
            if (e.getCode() != null) {
                if (e.getCode() == KeyCode.ENTER) {
                    nachrichtSenden();
                }
            }
        });

        addEventHandler(Event.ANY, e -> inputTextField.requestFocus());
    }

    private void nachrichtSenden() {
        if (inputTextField.getText().matches(" *")) return;
        AllgemeinerReturnWert ret = ansichtImpl.getClientKomm().nachrichtSenden(inputTextField.getText());
        switch (ret) {
            case ERFOLGREICH -> inputTextField.clear();
            case FEHLER -> Meldungen.zeigeInformation("Nicht gesendet", "Nachricht konnte nicht gesendet werden");
            case VERBINDUNG_ABGEBROCHEN -> {
                Meldungen.kommunikationAbgebrochen();
                System.exit(0);
            }
        }
    }

    public void nachrichtHinzufuegen(String nachricht) {
        verlauf.appendText(nachricht + "\n");
    }

    void chatLoeschen() {
        inputTextField.clear();
        verlauf.clear();
    }

    public static DialogChat dialogChatStart(AnsichtImpl ansichtImpl) {
        DialogChat root = new DialogChat(ansichtImpl);
        Scene scene = new Scene(root, 600, 400);
        Stage stage = new Stage();

        stage.setTitle("Chat");
        stage.setScene(scene);
        stage.setResizable(false);
        root.setOnClose();
        return root;
    }

    public void anzeigen() {
        ((Stage) getScene().getWindow()).show();
    }

    void verstecken() {
        getScene().getWindow().hide();
    }

    private void setOnClose() {
        getScene().getWindow().setOnCloseRequest(e -> getScene().getWindow().hide());
    }
}
