package edu.unibw.sse.madn.base;

import java.io.Serializable;

public record SpielfeldKonfiguration(boolean sechser, int[][] position, int[][] rotation, byte[] board, byte[] pathNormal, byte[][] dice, byte[][] path, byte[][] personal, byte[][] figure, byte[][] figureHigh) implements Serializable {
}
