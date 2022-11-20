package edu.unibw.sse.madn.ansicht.Impl;

import edu.unibw.sse.madn.clientKomm.AllgemeinerReturnWert;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class DialogAnmelden extends AnchorPane {
    private final AnsichtImpl ansichtImpl;
    private final TextField serverTextField;
    private final TextField usernameTextField;
    public DialogAnmelden(AnsichtImpl ansichtImpl) {
        this.ansichtImpl = ansichtImpl;

        serverTextField = new TextField("localhost");
        serverTextField.setPromptText("Server-IP-Adresse");
        serverTextField.setPrefWidth(280);

        usernameTextField = new TextField("Frank");
        usernameTextField.setPromptText("Benutzername");
        usernameTextField.setPrefWidth(280);

        PasswordField passwordField = new PasswordField();
        passwordField.setText("abcdef1!");
        passwordField.setPromptText("Passwort");
        passwordField.setPrefWidth(280);

        Button registrierenButton = new Button("Registrieren");
        registrierenButton.addEventHandler(ActionEvent.ACTION, e -> {
            ansichtImpl.dialogRegistrierenOeffnen(serverTextField.getText(),usernameTextField.getText());
            getScene().getWindow().hide();
        });
        Button abbrechenButton = new Button("Abbrechen");
        abbrechenButton.addEventHandler(ActionEvent.ACTION, e -> System.exit(0));
        Button anmeldenButton = new Button("Anmelden");
        anmeldenButton.addEventHandler(ActionEvent.ACTION, e -> anmelden(passwordField.getText()));

        AnchorPane.setLeftAnchor(serverTextField, 10.0);
        AnchorPane.setTopAnchor(serverTextField, 10.0);
        AnchorPane.setLeftAnchor(usernameTextField, 10.0);
        AnchorPane.setTopAnchor(usernameTextField, 40.0);
        AnchorPane.setLeftAnchor(passwordField, 10.0);
        AnchorPane.setTopAnchor(passwordField, 70.0);
        AnchorPane.setLeftAnchor(registrierenButton, 10.0);
        AnchorPane.setBottomAnchor(registrierenButton, 10.0);
        AnchorPane.setRightAnchor(abbrechenButton, 90.0);
        AnchorPane.setBottomAnchor(abbrechenButton, 10.0);
        AnchorPane.setRightAnchor(anmeldenButton, 10.0);
        AnchorPane.setBottomAnchor(anmeldenButton, 10.0);

        getChildren().addAll(abbrechenButton, serverTextField, usernameTextField, passwordField, registrierenButton, anmeldenButton);

        addEventHandler(KeyEvent.KEY_PRESSED, e -> {
            if (e.getCode() != null) {
                switch (e.getCode()) {
                    case ENTER -> anmelden(passwordField.getText());
                    case ESCAPE -> System.exit(0);
                }
            }
        });
    }

    private void anmelden(String pw) {
        AllgemeinerReturnWert ret = ansichtImpl.getClientKomm().anmelden(serverTextField.getText(), usernameTextField.getText(), pw);
        switch (ret) {
            case ERFOLGREICH -> {
                ansichtImpl.erfolgreichAngemeldet(usernameTextField.getText());
                ansichtImpl.dialogRaumauswahlOeffnen();
                getScene().getWindow().hide();
            }
            case FEHLER -> Meldungen.zeigeInformation("Login-Daten fehlerhaft", "Die Logindaten sind falsch.");
            case VERBINDUNG_ABGEBROCHEN ->
                    Meldungen.zeigeInformation("Server nicht gefunden", "Unter der angegebenen IP-Adresse konnte kein Server gefunden werden.");
        }
    }

    public static DialogAnmelden dialogAnmeldenStart(AnsichtImpl ansichtImpl) {
        DialogAnmelden root = new DialogAnmelden(ansichtImpl);
        Scene scene = new Scene(root, 300, 150);
        Stage stage = new Stage();

        stage.setTitle("Anmelden");
        stage.setScene(scene);
        stage.setResizable(false);
        return root;
    }

    void anzeigen(String ip, String name) {
        if (ip != null) serverTextField.setText(ip);
        if (name != null) usernameTextField.setText(name);
        ((Stage) getScene().getWindow()).show();
    }
}
