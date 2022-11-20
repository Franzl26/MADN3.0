package edu.unibw.sse.madn.ansicht.Impl;

import edu.unibw.sse.madn.base.SpielfeldKonfiguration;
import javafx.scene.image.Image;

import java.io.ByteArrayInputStream;

public class SpielfeldKonfigurationIntern {
    final boolean sechser;
    final int[][] pointCoordinates;
    final int[][] orientation;
    final Image board;
    final Image pathNormal;
    final Image[] dice = new Image[7];
    final Image[] path;
    final Image[] personal;
    final Image[] figure;
    final Image[] figureHigh;

    SpielfeldKonfigurationIntern(SpielfeldKonfiguration config) {
        sechser = config.sechser();
        int anzahl = sechser ? 6 : 4;
        path = new Image[anzahl];
        personal = new Image[anzahl];
        figure = new Image[anzahl];
        figureHigh = new Image[anzahl];

        pointCoordinates = config.position().clone();
        orientation = config.rotation().clone();
        board = bildAusArray(config.board());
        pathNormal = bildAusArray(config.pathNormal());
        for (int i = 0; i < anzahl; i++) {
            path[i] = bildAusArray(config.path()[i]);
            personal[i] = bildAusArray(config.personal()[i]);
            figure[i] = bildAusArray(config.figure()[i]);
            figureHigh[i] = bildAusArray(config.figureHigh()[i]);
        }
        for (int i = 0; i < 7; i++) {
            dice[i] = bildAusArray(config.dice()[i]);
        }
    }

    private Image bildAusArray(byte[] array) {
        return new Image(new ByteArrayInputStream(array));
    }
}
