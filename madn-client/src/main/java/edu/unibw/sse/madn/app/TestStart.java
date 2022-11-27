package edu.unibw.sse.madn.app;

import edu.unibw.sse.madn.ansicht.Impl.AnsichtImpl;
import edu.unibw.sse.madn.ansicht.Impl.DialogSpielstatistik;
import edu.unibw.sse.madn.base.Spielstatistik;
import edu.unibw.sse.madn.clientKomm.ClientKommunikation;
import edu.unibw.sse.madn.clientKomm.Impl.ClientKommImpl;
import edu.unibw.sse.madn.datenClient.DateizugriffClient;
import edu.unibw.sse.madn.datenClient.Impl.DateizugriffClientImpl;
import javafx.application.Application;
import javafx.stage.Stage;

public class TestStart extends Application {
    @Override
    public void start(Stage stage) {
        ClientKommunikation clientKommunikation = new ClientKommImpl();
        AnsichtImpl ansicht = new AnsichtImpl();
        DateizugriffClient dateizugriffClient = new DateizugriffClientImpl();
        ansicht.clientKommunikationSetzen(clientKommunikation.clientKommunikationHolen());
        ansicht.dateizugriffClientSetzen(dateizugriffClient.datenClientHolen());

        int[][] zahlenGewuerfelt = new int[][]{{23,34,23,12,89,56},{23,34,23,12,89,56},{23,34,23,12,89,56},{23,34,23,12,89,56},{23,34,23,12,89,56},{23,34,23,12,89,56}};
        int[] andereGeschlagen = new int[]{11,22,33,44,55,66};
        int[] geschlagenWorden = new int[]{1,2,5,2,1,2};
        int[] prioFalsch = new int[]{5,10,5,3,2,2};
        String[] namen = new String[]{"eins","zwei","drei","vier","fuenf","sechs"};
        String[] gewonnen = new String[]{"erster","zweiter","dritter","vierter","fuenfter","sechster"};
        Spielstatistik spielstatistik = new Spielstatistik(zahlenGewuerfelt,andereGeschlagen,geschlagenWorden,prioFalsch,gewonnen,System.currentTimeMillis()-23423,namen);

        DialogSpielstatistik.dialogSpielstatistikStart(ansicht, spielstatistik);
    }

    public static void main(String[] args) {
        launch();
        //System.out.println("  ".matches(" *"));
    }
}
