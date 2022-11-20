package edu.unibw.sse.madn.ansicht.Impl;

import edu.unibw.sse.madn.base.FeldBesetztStatus;
import edu.unibw.sse.madn.base.SpielfeldKonfiguration;
import edu.unibw.sse.madn.datenClient.Impl.DateizugriffClientImpl;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.Arrays;

import static edu.unibw.sse.madn.base.FeldBesetztStatus.*;

public class DesignTest extends Application {
    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) {
        DesignPane.DesignPaneStart();
    }


    private static class DesignPane extends AnchorPane {
        /**
         * Hier anpassen - Feldpositionen nach TDM!
         */
        private static final int[] spieler1 = new int[]{0,1,2,3}; // gelb
        private static final int[] spieler2 = new int[]{4,5,6,7}; // grün
        private static final int[] spieler3 = new int[]{8,9,10,11}; // rot
        private static final int[] spieler4 = new int[]{12,13,14,15}; // blau
        private static final int[] spieler5 = new int[]{16,17,18,19}; // türkis
        private static final int[] spieler6 = new int[]{20,21,22,23}; // orange

        public DesignPane() {

            setBackground(Background.fill(Color.LIGHTSLATEGRAY));
            Canvas boardCanvas = new Canvas(500, 500);
            GraphicsContext gcBoard = boardCanvas.getGraphicsContext2D();

            gcBoard.setFill(Color.LIGHTSLATEGRAY);
            gcBoard.fillRect(0, 0, 500, 500);

            FeldBesetztStatus[] feld = new FeldBesetztStatus[96];
            Arrays.fill(feld, FELD_LEER);
            for (int i = 0; i < 4; i++) {
                if (spieler1.length > i) feld[spieler1[i]] = FELD_SPIELER1;
                if (spieler2.length > i) feld[spieler2[i]] = FELD_SPIELER2;
                if (spieler3.length > i) feld[spieler3[i]] = FELD_SPIELER3;
                if (spieler4.length > i) feld[spieler4[i]] = FELD_SPIELER4;
                if (spieler5.length > i) feld[spieler5[i]] = FELD_SPIELER5;
                if (spieler6.length > i) feld[spieler6[i]] = FELD_SPIELER6;
            }

            drawBoardAllPrivate(gcBoard, feld);

            AnchorPane.setLeftAnchor(boardCanvas, 10.0);
            AnchorPane.setTopAnchor(boardCanvas, 10.0);

            getChildren().addAll(boardCanvas);
        }

        void drawBoardAllPrivate(GraphicsContext gc, FeldBesetztStatus[] feld) {
            DateizugriffClientImpl dateizugriffClient = new DateizugriffClientImpl();
            SpielfeldKonfiguration spielfeldKonfiguration = dateizugriffClient.konfigurationLaden("StarWars",true);
            //SpielfeldKonfiguration spielfeldKonfiguration = dateizugriffClient.konfigurationLaden("Standard",true);
            if (spielfeldKonfiguration == null) return;
            SpielfeldKonfigurationIntern spielfeldKonfigurationIntern = new SpielfeldKonfigurationIntern(spielfeldKonfiguration);
            BrettZeichnen.drawBoardAll(gc,spielfeldKonfigurationIntern,feld);
        }

        private static void DesignPaneStart() {
            DesignPane root = new DesignPane();
            Scene scene = new Scene(root, 520, 520);
            Stage stage = new Stage();

            stage.setTitle("Design Auswählen");
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
        }
    }
}

