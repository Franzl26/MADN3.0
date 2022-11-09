package edu.unibw.sse.madn.base;

public record SpielfeldKonfiguration(byte[] position, byte[] board, byte[] pathNormal, byte[][] dice, byte[][] path, byte[][] personal, byte[][] figure, byte[][] figureHigh) {
}
