package edu.unibw.sse.madn.impl;

import edu.unibw.sse.madn.base.SpielfeldKonfiguration;

import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SpielfeldKonfigurationLadenSpeichern {
    public static SpielfeldKonfiguration loadBoardKonfiguration(String dir, boolean sechser) {
        if (!dir.endsWith("/")) dir = dir.concat("/");
        File f = new File(dir);
        if (!f.isDirectory()) return null;
        Builder builder = new Builder(sechser);
        if (!builder.read(f)) return null;
        return builder.build();
    }

    private static class Builder {
        private final boolean sechser;
        private final int[][] pointCoordinates;
        private final int[][] orientation;
        private byte[] board;
        private byte[] pathNormal;
        private byte[][] dice;
        private byte[][] path;
        private byte[][] personal;
        private byte[][] figure;
        private byte[][] figureHigh;

        private Builder(boolean sechser) {
            this.sechser = sechser;
            dice = new byte[7][];
            int anzahl = sechser ? 6 : 4;
            path = new byte[anzahl][];
            personal = new byte[anzahl][];
            figure = new byte[anzahl][];
            figureHigh = new byte[anzahl][];
            pointCoordinates = new int[sechser ? 96 : 72][2];
            orientation = new int[sechser ? 96 : 72][2];
        }

        private boolean read(File f) {
            try {
                int anzahl = sechser ? 6 : 4;
                // Bilder einlesen
                board = readFile(f.getAbsolutePath() + "/board.png");
                pathNormal = readFile(f.getAbsolutePath() + "/pathNormal.png");
                dice = readFiles(f.getAbsolutePath(), "/dice", 7);
                path = readFiles(f.getAbsolutePath(), "/path", anzahl);
                personal = readFiles(f.getAbsolutePath(), "/personal", anzahl);
                figure = readFiles(f.getAbsolutePath(), "/figure", anzahl);
                figureHigh = readFiles(f.getAbsolutePath(), "/figureHigh", anzahl);

                // Koordinaten einlesen
                File positions = new File(f.getAbsolutePath() + "/positions.txt");
                try (BufferedReader buf = new BufferedReader(new FileReader(positions))) {
                    //Pattern pattern = Pattern.compile("[ \t]*(\\d+)[ \t]+(\\d+)[ \t]*");
                    Pattern pattern = Pattern.compile("[ \t]*(\\d+)[ \t]+(\\d+)[ \t]*([ \t]+(-?\\d+)([ \t]+([01])[ \t]*)?)?");
                    String in = buf.readLine();
                    int line = 0;
                    int count = 0;
                    while (in != null) {
                        line++;
                        if (!in.contains("//")) {
                            Matcher matcher = pattern.matcher(in);
                            if (!matcher.matches()) throw new IOException("position file has error on line:" + line);
                            pointCoordinates[count][0] = Integer.parseInt(matcher.group(1));
                            pointCoordinates[count][1] = Integer.parseInt(matcher.group(2));
                            if (matcher.group(4) != null) {
                                orientation[count][0] = Integer.parseInt(matcher.group(4));
                            }
                            if (matcher.group(6) != null) {
                                orientation[count][1] = Integer.parseInt(matcher.group(6));
                            }
                            count++;
                        }
                        in = buf.readLine();
                    }
                    if (count != (sechser ? 96 : 72)) return false;
                } catch (IOException e) {
                    e.printStackTrace(System.out);
                    return false;
                }
            } catch (Exception e) {
                e.printStackTrace(System.out);
                return false;
            }
            return true;
        }

        private SpielfeldKonfiguration build() {
            return new SpielfeldKonfiguration(sechser, pointCoordinates, orientation, board, pathNormal, dice, path, personal, figure, figureHigh);
        }

        private static byte[][] readFiles(String dir, String name, int count) {
            byte[][] tmp = new byte[count][];
            for (int i = 0; i < count; i++) {
                tmp[i] = readFile(dir + name + i + ".png");
            }
            return tmp;
        }

        private static byte[] readFile(String path) {
            File f = new File(path);
            try (DataInputStream dis = new DataInputStream(new FileInputStream(f))) {
                int length = (int) f.length();
                byte[] file = new byte[length];
                dis.readFully(file);
                return file;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static void saveConfiguration(SpielfeldKonfiguration config, String dir) {
        if (!dir.endsWith("/")) dir = dir.concat("/");
        File f = new File(dir);
        //noinspection ResultOfMethodCallIgnored
        f.mkdir();

        saveFile(dir + "board.png", config.board());
        saveFile(dir + "pathNormal.png", config.pathNormal());
        saveFiles(dir, "dice", config.dice());
        saveFiles(dir, "figure", config.figure());
        saveFiles(dir, "figureHigh", config.figureHigh());
        saveFiles(dir, "path", config.path());
        saveFiles(dir, "personal", config.personal());

        // Position und Drehung speichern
        int[][] pos = config.position();
        int[][] rot = config.rotation();
        int anzahl = pos.length;
        try (BufferedWriter buf = new BufferedWriter(new FileWriter(dir + "positions.txt"))) {
            for (int i = 0; i < anzahl; i++) {
                buf.write(pos[i][0] + " " + pos[i][1] + " " + rot[i][0] + " " + rot[i][1]);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void saveFiles(String dir, String name, byte[][] data) {
        for (int i = 0; i < data.length; i++) {
            saveFile(dir + name + i + ".png", data[i]);
        }
    }

    private static void saveFile(String dir, byte[] data) {
        File f = new File(dir);
        try (DataOutputStream dos = new DataOutputStream(new FileOutputStream(f))) {
            dos.write(data);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
