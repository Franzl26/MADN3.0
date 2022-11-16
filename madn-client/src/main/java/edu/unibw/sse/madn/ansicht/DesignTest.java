package edu.unibw.sse.madn.ansicht;

import edu.unibw.sse.madn.base.FeldBesetztStatus;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.File;
import java.nio.file.Paths;
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
         * Hier anpassen
         */
        private static final int[] spieler1 = new int[]{0, 1, 2, 3};
        private static final int[] spieler2 = new int[]{4, 5, 6, 7};
        private static final int[] spieler3 = new int[]{8, 9, 10, 11};
        private static final int[] spieler4 = new int[]{12, 13, 14, 15};

        public DesignPane() {

            setBackground(Background.fill(Color.LIGHTSLATEGRAY));
            Canvas boardCanvas = new Canvas(500, 500);
            GraphicsContext gcBoard = boardCanvas.getGraphicsContext2D();

            gcBoard.setFill(Color.LIGHTSLATEGRAY);
            gcBoard.fillRect(0, 0, 500, 500);

            FeldBesetztStatus[] feld = new FeldBesetztStatus[72];
            Arrays.fill(feld, FELD_LEER);
            for (int i = 0; i < 4; i++) {
                feld[spieler1[i]] = FELD_SPIELER1;
                feld[spieler2[i]] = FELD_SPIELER2;
                feld[spieler3[i]] = FELD_SPIELER3;
                feld[spieler4[i]] = FELD_SPIELER4;
            }
            String array = Arrays.toString(feld).replaceAll("\\[", "{").replaceAll("]", "}");
            System.out.println(array);
            System.out.println("oder");
            System.out.println("int[] spieler1 = new int[]"+Arrays.toString(spieler1).replaceAll("\\[", "{").replaceAll("]", "}") +";\n"+
                    "int[] spieler2 = new int[]"+Arrays.toString(spieler2).replaceAll("\\[", "{").replaceAll("]", "}") +";\n"+
                    "int[] spieler3 = new int[]"+Arrays.toString(spieler3).replaceAll("\\[", "{").replaceAll("]", "}") +";\n"+
                    "int[] spieler4 = new int[]"+Arrays.toString(spieler4).replaceAll("\\[", "{").replaceAll("]", "}") +";\n"+
                                """
                                Arrays.fill(feld, FELD_LEER);
                                for (int i = 0; i<4;i++) {
                                    feld[spieler1[i]] = FELD_SPIELER1;
                                    feld[spieler2[i]] = FELD_SPIELER2;
                                    feld[spieler3[i]] = FELD_SPIELER3;
                                    feld[spieler4[i]] = FELD_SPIELER4;
                                }""");

            drawBoardAllPrivate(gcBoard, feld);

            AnchorPane.setLeftAnchor(boardCanvas, 10.0);
            AnchorPane.setTopAnchor(boardCanvas, 10.0);

            getChildren().addAll(boardCanvas);
        }

        @SuppressWarnings("ConstantConditions")
        void drawBoardAllPrivate(GraphicsContext gc, FeldBesetztStatus[] feld) {
            int[][] pos = new int[][]{{50, 50}, {90, 50}, {50, 90}, {90, 90}, {410, 50}, {450, 50}, {410, 90}, {450, 90}, {410, 410}, {450, 410}, {410, 450}, {450, 450}, {50, 410}, {90, 410}, {50, 450}, {90, 450}, {90, 250}, {130, 250}, {170, 250}, {210, 250}, {250, 90}, {250, 130}, {250, 170}, {250, 210}, {410, 250}, {370, 250}, {330, 250}, {290, 250}, {250, 410}, {250, 370}, {250, 330}, {250, 290}, {50, 210}, {90, 210}, {130, 210}, {170, 210}, {210, 210}, {210, 170}, {210, 130}, {210, 90}, {210, 50}, {250, 50}, {290, 50}, {290, 90}, {290, 130}, {290, 170}, {290, 210}, {330, 210}, {370, 210}, {410, 210}, {450, 210}, {450, 250}, {450, 290}, {410, 290}, {370, 290}, {330, 290}, {290, 290}, {290, 330}, {290, 370}, {290, 410}, {290, 450}, {250, 450}, {210, 450}, {210, 410}, {210, 370}, {210, 330}, {210, 290}, {170, 290}, {130, 290}, {90, 290}, {50, 290}, {50, 250}};
            File f = new File("C:\\Users\\f-luc\\Desktop\\JavaProjects\\ourproject-src\\madn-client\\src\\main\\resources\\designs\\Standard");
            Image board = new Image(Paths.get(f.getAbsolutePath() + "/board.png").toUri().toString());
            Image pathNormal = new Image(Paths.get(f.getAbsolutePath() + "/pathNormal.png").toUri().toString());
            Image[] path = new Image[4];
            Image[] personal = new Image[4];
            Image[] figure = new Image[4];
            for (int i = 0; i < 4; i++) {
                path[i] = new Image(Paths.get(f.getAbsolutePath() + "/path" + i + ".png").toUri().toString());
                personal[i] = new Image(Paths.get(f.getAbsolutePath() + "/personal" + i + ".png").toUri().toString());
                figure[i] = new Image(Paths.get(f.getAbsolutePath() + "/figure" + i + ".png").toUri().toString());
            }

            gc.setFill(Color.BLACK);
            gc.fillRect(0, 0, 500, 500);
            gc.drawImage(board, 0, 0, 500, 500);
            for (int i = 0; i < 72; i++) {
                Image image = switch (i) {
                    case 32 -> path[0];
                    case 42 -> path[1];
                    case 52 -> path[2];
                    case 62 -> path[3];
                    default -> pathNormal;
                };
                if ((i >= 0 && i <= 3) || (i >= 16 && i <= 19)) image = personal[0];
                else if ((i >= 4 && i <= 7) || (i >= 20 && i <= 23)) image = personal[1];
                else if ((i >= 8 && i <= 11) || (i >= 24 && i <= 27)) image = personal[2];
                else if ((i >= 12 && i <= 15) || (i >= 28 && i <= 31)) image = personal[3];
                switch (feld[i]) {
                    case FELD_SPIELER1 -> image = figure[0];
                    case FELD_SPIELER2 -> image = figure[1];
                    case FELD_SPIELER3 -> image = figure[2];
                    case FELD_SPIELER4 -> image = figure[3];
                }
                gc.drawImage(image, pos[i][0] - 17, pos[i][1] - 17, 34, 34);
            }
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

