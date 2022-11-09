package edu.unibw.sse.madn.spielLogik;

public interface SpielErstellen {
    void spielErstellen(String[] benutzernamen, int bots, int spieler);

    void warteraumCallbackSetzen(WarteraumCallback warteraumCallback);
}
