package edu.unibw.sse.madn.ansicht.Impl;

import edu.unibw.sse.madn.base.RegistrierenRueckgabe;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import static edu.unibw.sse.madn.base.RegistrierenRueckgabe.*;

public class DialogRegistrieren extends AnchorPane {
    private final AnsichtImpl ansichtImpl;
    private final TextField serverTextField;
    private final TextField usernameTextField;

    public DialogRegistrieren(AnsichtImpl ansichtImpl) {
        this.ansichtImpl = ansichtImpl;

        serverTextField = new TextField();
        serverTextField.setPromptText("Server-IP-Adresse");
        serverTextField.setPrefWidth(280);

        usernameTextField = new TextField();
        usernameTextField.setPromptText("Benutzername");
        usernameTextField.setPrefWidth(280);

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Passwort");
        passwordField.setPrefWidth(280);

        PasswordField passwordField2 = new PasswordField();
        passwordField2.setPromptText("Passwort wiederholen");
        passwordField2.setPrefWidth(280);

        Button registrierenButton = new Button("Registrieren");
        registrierenButton.setPrefWidth(90);
        registrierenButton.addEventHandler(ActionEvent.ACTION, e -> registrieren(passwordField.getText(), passwordField2.getText()));
        Button abbrechenButton = new Button("Abbrechen");
        abbrechenButton.addEventHandler(ActionEvent.ACTION, e -> schliessen());

        AnchorPane.setLeftAnchor(serverTextField, 10.0);
        AnchorPane.setTopAnchor(serverTextField, 10.0);
        AnchorPane.setLeftAnchor(usernameTextField, 10.0);
        AnchorPane.setTopAnchor(usernameTextField, 40.0);
        AnchorPane.setLeftAnchor(passwordField, 10.0);
        AnchorPane.setTopAnchor(passwordField, 70.0);
        AnchorPane.setLeftAnchor(passwordField2, 10.0);
        AnchorPane.setTopAnchor(passwordField2, 100.0);
        AnchorPane.setRightAnchor(registrierenButton, 10.0);
        AnchorPane.setBottomAnchor(registrierenButton, 10.0);
        AnchorPane.setRightAnchor(abbrechenButton, 110.0);
        AnchorPane.setBottomAnchor(abbrechenButton, 10.0);

        getChildren().addAll(abbrechenButton, serverTextField, usernameTextField, passwordField, passwordField2, registrierenButton);

        addEventHandler(KeyEvent.KEY_PRESSED, e -> {
            if (e.getCode() != null) {
                switch (e.getCode()) {
                    case ENTER -> registrieren(passwordField.getText(), passwordField2.getText());
                    case ESCAPE -> System.exit(0);
                }
            }
        });
    }

    private void registrieren(String pw1, String pw2) {
        if (!pw1.equals(pw2)) {
            Meldungen.zeigeInformation("Passwörter stimmen nicht überein", "Die beiden eingegebenen Passwörter stimmen nicht überein.");
            return;
        }
        RegistrierenRueckgabe ret = ansichtImpl.getClientKomm().registrieren(serverTextField.getText(), usernameTextField.getText(), pw1);
        if (ret == PASSWORT_NICHT_GUIDELINES) {
            Meldungen.zeigeInformation("Passwort entspricht nicht den Richtlinien", "Das Passwort entspricht nicht den Richtlinien:\n- 8-15 Zeichen\n- mindestens ein Buchstabe\n- mindestens eine Zahl\n- mindestens eins der Sonderzeichen: !§$%&/()=?#");
        } else if (ret == NAME_NICHT_GUIDELINES) {
            Meldungen.zeigeInformation("Benutzername entspricht nicht den Richtlinien", "Der Benutzername entspricht nicht den Richtlinien:\n- 3-8 Zeichen\n- nur Buchstaben");
        } else if (ret == ERFOLGREICH) {
            Meldungen.zeigeInformation("Registrierung erfolgreich!", "Die Registrierung war erfolgreich, du kannst dich jetzt anmelden");
            schliessen();
        } else if (ret == NAME_BEREITS_VERGEBEN) {
            Meldungen.zeigeInformation("Benutzername bereits vergeben", "Dieser Benutzername ist bereits vergeben, versuche es mit einem anderen nochmal");
        } else {
            Meldungen.zeigeInformation("Server nicht gefunden", "Unter der angegebenen IP-Adresse konnte kein Server gefunden werden.");
        }
    }

    private void schliessen() {
        ansichtImpl.dialogAnmeldenOeffnen(serverTextField.getText(),usernameTextField.getText());
        getScene().getWindow().hide();
    }

    private void setOnClose() {
        getScene().getWindow().setOnCloseRequest(e -> schliessen());
    }

    public static DialogRegistrieren dialogRegistrierenStart(AnsichtImpl ansichtImpl) {
        DialogRegistrieren root = new DialogRegistrieren(ansichtImpl);
        Scene scene = new Scene(root, 300, 180);
        Stage stage = new Stage();

        stage.setTitle("Registrieren");
        stage.setScene(scene);
        stage.setResizable(false);
        root.setOnClose();
        return root;
    }

    void anzeigen(String ip, String name) {
        if (ip != null) serverTextField.setText(ip);
        if (name != null) usernameTextField.setText(name);
        ((Stage) getScene().getWindow()).show();
    }
}
