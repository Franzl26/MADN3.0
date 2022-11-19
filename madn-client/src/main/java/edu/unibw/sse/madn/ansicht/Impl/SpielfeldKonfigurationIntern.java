package edu.unibw.sse.madn.ansicht.Impl;

import edu.unibw.sse.madn.base.SpielfeldKonfiguration;
import javafx.scene.image.Image;

import java.io.ByteArrayInputStream;

public class SpielfeldKonfigurationIntern {
    final int[][] pointCoordinates;
    final int[][] orientation;
    final Image board;
    final Image pathNormal;
    final Image[] dice = new Image[7];
    final Image[] path = new Image[4];
    final Image[] personal = new Image[4];
    final Image[] figure = new Image[4];
    final Image[] figureHigh = new Image[4];

    SpielfeldKonfigurationIntern(SpielfeldKonfiguration config) {
        pointCoordinates = config.position();
        orientation = config.rotation();
        board = bildAusArray(config.board());
        pathNormal = bildAusArray(config.pathNormal());
        for (int i = 0; i < 4; i++) {
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
