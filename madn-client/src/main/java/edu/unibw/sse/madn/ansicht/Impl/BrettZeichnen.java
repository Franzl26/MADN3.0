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
        int anzahl = config.sechser ? 96 : 72;
        for (int i = 0; i < anzahl; i++) {
            drawBoardSingleFieldAll(gc, config, board[i], i, false);
        }
    }

    static void drawBoardSingleFieldAll(GraphicsContext gc, SpielfeldKonfigurationIntern config, FeldBesetztStatus state, int i, boolean highlight) {
        Image image = getImage(config, state, i, highlight);
        int size = config.sechser ? 15 : 17;

        ImageView iv = new ImageView(image);
        int[] drehung = config.orientation[i];
        if (drehung[1] == 1 && state != FeldBesetztStatus.FELD_LEER) iv.setScaleX(-1);
        SnapshotParameters params = new SnapshotParameters();
        params.setFill(Color.TRANSPARENT);
        image = iv.snapshot(params, null);

        int[] koords = config.pointCoordinates[i];
        Image brett = config.board;
        gc.drawImage(brett, (koords[0] - size) * brett.getWidth() / 500, (koords[1] - size) * brett.getHeight() / 500, 2 * size * brett.getWidth() / 500, 2 * size * brett.getHeight() / 500, koords[0] - size, koords[1] - size, 2 * size, 2 * size);
        gc.save();
        gc.translate(koords[0], koords[1]);
        if (state != FeldBesetztStatus.FELD_LEER) gc.rotate(drehung[0]);
        gc.drawImage(image, -size, -size, 2 * size, 2 * size);
        gc.restore();
    }

    private static Image getImage(SpielfeldKonfigurationIntern config, FeldBesetztStatus state, int i, boolean highlight) {
        Image image;
        if (config.sechser) {
            image = switch (i) {
                case 48 -> config.path[0];
                case 56 -> config.path[1];
                case 64 -> config.path[2];
                case 72 -> config.path[3];
                case 80 -> config.path[4];
                case 88 -> config.path[5];
                default -> config.pathNormal;
            };
            if ((i >= 0 && i <= 3) || (i >= 24 && i <= 27)) image = config.personal[0];
            else if ((i >= 4 && i <= 7) || (i >= 28 && i <= 31)) image = config.personal[1];
            else if ((i >= 8 && i <= 11) || (i >= 32 && i <= 35)) image = config.personal[2];
            else if ((i >= 12 && i <= 15) || (i >= 36 && i <= 39)) image = config.personal[3];
            else if ((i >= 16 && i <= 19) || (i >= 40 && i <= 43)) image = config.personal[4];
            else if ((i >= 20 && i <= 23) || (i >= 44 && i <= 47)) image = config.personal[5];
            if (highlight) {
                switch (state) {
                    case FELD_SPIELER1 -> image = config.figureHigh[0];
                    case FELD_SPIELER2 -> image = config.figureHigh[1];
                    case FELD_SPIELER3 -> image = config.figureHigh[2];
                    case FELD_SPIELER4 -> image = config.figureHigh[3];
                    case FELD_SPIELER5 -> image = config.figureHigh[4];
                    case FELD_SPIELER6 -> image = config.figureHigh[5];
                }
            } else {
                switch (state) {
                    case FELD_SPIELER1 -> image = config.figure[0];
                    case FELD_SPIELER2 -> image = config.figure[1];
                    case FELD_SPIELER3 -> image = config.figure[2];
                    case FELD_SPIELER4 -> image = config.figure[3];
                    case FELD_SPIELER5 -> image = config.figure[4];
                    case FELD_SPIELER6 -> image = config.figure[5];
                }
            }
        } else {
            image = switch (i) {
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
        }
        return image;
    }
}
