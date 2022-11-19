package edu.unibw.sse.madn.ansicht.Impl;

import edu.unibw.sse.madn.base.FeldBesetztStatus;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

public class BrettZeichnen {
    static void drawBoardAll(GraphicsContext gc, SpielfeldKonfigurationIntern config, FeldBesetztStatus[] board) {
        //gc.setFill(Color.LIGHTSLATEGRAY);
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, 500, 500);
        gc.drawImage(config.board, 0, 0, 500, 500);
        for (int i = 0; i < 72; i++) {
            drawBoardSingleFieldAll(gc, config, board[i], i, false);
        }
    }

    static void drawBoardSingleFieldAll(GraphicsContext gc, SpielfeldKonfigurationIntern config, FeldBesetztStatus state, int i, boolean highlight) {
        Image image = switch (i) {
            case 32 -> config.path[0];
            case 42 -> config.path[1];
            case 52 -> config.path[2];
            case 62 -> config.path[3];
            default -> config.pathNormal;
        };
        if ((i >= 0 && i <= 3) || (i >= 16 && i <= 19)) image = config.personal[0];
        else if ((i >= 4 && i <= 7) || (i >= 20 && i <= 23)) image = config.personal[1];
        else if ((i >= 8 && i <= 11) || (i >= 24 && i <= 27)) image = config.personal[2];
        else if ((i >= 12 && i <= 15) || (i >= 28 && i <= 31)) image = config.personal[3];
        if (highlight) {
            switch (state) {
                case FELD_SPIELER1 -> image = config.figureHigh[0];
                case FELD_SPIELER2 -> image = config.figureHigh[1];
                case FELD_SPIELER3 -> image = config.figureHigh[2];
                case FELD_SPIELER4 -> image = config.figureHigh[3];
            }
        } else {
            switch (state) {
                case FELD_SPIELER1 -> image = config.figure[0];
                case FELD_SPIELER2 -> image = config.figure[1];
                case FELD_SPIELER3 -> image = config.figure[2];
                case FELD_SPIELER4 -> image = config.figure[3];
            }
        }

        ImageView iv = new ImageView(image);
        int[] drehung = config.orientation[i];
        if (drehung[1] == 1 && state != FeldBesetztStatus.FELD_LEER) iv.setScaleX(-1);
        SnapshotParameters params = new SnapshotParameters();
        params.setFill(Color.TRANSPARENT);
        image = iv.snapshot(params, null);

        int[] koords = config.pointCoordinates[i];
        Image brett = config.board;
        gc.drawImage(brett, (koords[0] - 20) * brett.getWidth() / 500, (koords[1] - 20) * brett.getHeight() / 500, 40 * brett.getWidth() / 500, 40 * brett.getHeight() / 500, koords[0] - 20, koords[1] - 20, 40, 40);
        gc.save();
        gc.translate(koords[0], koords[1]);
        if (state != FeldBesetztStatus.FELD_LEER) gc.rotate(drehung[0]);
        gc.drawImage(image, -17, -17, 34, 34);
        gc.restore();
    }
}
