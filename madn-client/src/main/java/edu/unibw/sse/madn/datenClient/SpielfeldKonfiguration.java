package edu.unibw.sse.madn.datenClient;

import javafx.scene.image.Image;

public interface SpielfeldKonfiguration {
    int[] koordinatenVonFeld(int i);

    int[] drehungVonFeld(int i);

    Image brettBild();

    Image pfadNormalBild();

    Image wuerfelBild(int wurf);

    Image startfeldBild(int spieler);

    Image spielerPersBild(int spieler);

    Image figurBild(int spieler);

    Image figurMarkiertBild(int spieler);

    int klickRadius();
}
