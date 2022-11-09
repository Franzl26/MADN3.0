package edu.unibw.sse.madn.warteraumverwaltung;

public interface Raumauswahl {
    void fuerUpdatesAnmelden(String benutzername);

    boolean warteraumErstellen(String benutzername);

    boolean warteraumBeitreten(String benutzername, long raumId);

    void warteraumVerlassen(String benutzername);

    boolean botHinzufuegen(String benutzername);

    boolean botEntfernen(String benutzername);

    boolean spielStarten(String benutzername);

    void designAnpassen(String benutzername, String design);

    void anClientSendenRaumauswahlSetzen(AnClientSendenRaumauswahl anClientSendenRaumauswahl);
}
