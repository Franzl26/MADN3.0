package edu.unibw.sse.madn.app;

import edu.unibw.sse.madn.ansicht.Impl.DialogChat;
import javafx.application.Application;
import javafx.stage.Stage;

public class ChatBild extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        DialogChat dialogChat = DialogChat.dialogChatStart(null);
        dialogChat.nachrichtHinzufuegen("PersonA: Ein schönes Spiel allen");
        dialogChat.nachrichtHinzufuegen("PersonC: Viel Spaß");
        dialogChat.nachrichtHinzufuegen("PersonE: ICH MACH EUCH ALLE PLATT");
        dialogChat.nachrichtHinzufuegen("PersonD: ...");
        dialogChat.nachrichtHinzufuegen("PersonA: Jetzt hast du Blödbommel mir doch tatsächlich die Figur aus dem Feld geschmissen");
        dialogChat.nachrichtHinzufuegen("PersonB: Hättest du halt mal die andere Figur genommen");
        dialogChat.nachrichtHinzufuegen("PersonC: Seit 5 verdammten Runden keine 6");
        dialogChat.nachrichtHinzufuegen("PersonE: Ja echt mal, das ist doch kein Zufall mehr");
        dialogChat.nachrichtHinzufuegen("PersonD: Ich bin raus...");
        dialogChat.anzeigen();
    }

    public static void main(String[] args) {
        launch();
    }
}
