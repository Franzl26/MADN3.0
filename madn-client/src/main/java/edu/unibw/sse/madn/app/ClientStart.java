package edu.unibw.sse.madn.app;

import edu.unibw.sse.madn.ansicht.Ansicht;
import edu.unibw.sse.madn.ansicht.Impl.AnsichtImpl;
import edu.unibw.sse.madn.clientKomm.ClientKommunikation;
import edu.unibw.sse.madn.clientKomm.Impl.ClientKommImpl;
import edu.unibw.sse.madn.datenClient.DateizugriffClient;
import edu.unibw.sse.madn.datenClient.Impl.DateizugriffClientImpl;
import javafx.application.Application;
import javafx.stage.Stage;

public class ClientStart extends Application {
    @Override
    public void start(Stage stage) {
        ClientKommunikation clientKommunikation = new ClientKommImpl();
        Ansicht ansicht = new AnsichtImpl();
        DateizugriffClient dateizugriffClient = new DateizugriffClientImpl();
        ansicht.clientKommunikationSetzen(clientKommunikation.clientKommunikationHolen());
        ansicht.dateizugriffClientSetzen(dateizugriffClient.datenClientHolen());
        ansicht.anwendungStarten();
    }

    public static void main(String[] args) {
        launch();
    }
}
