package edu.unibw.sse.madn.warteraumverwaltung;

/**
 * Domenik
 */
public interface Raumauswahl {
    /**
     * Warteraum erstellen
     * <p>
     * Überprüft, ob bereits 25 aktive Warteräume existieren, ist dies der Fall wird false zurückgegeben,
     * ansonsten wird ein neuer Warteraum erstellt, der Nutzer wird diesem zugeordnet und tritt diesem bei.
     * Daraufhin wird der Warteraum aktualisiert.
     *
     * @param benutzername Benutzername des Clients, der die Aktion ausgelöst hat
     * @return Warteraum erstellt: true, sonst false
     */
    boolean warteraumErstellen(String benutzername);

    /**
     * Warteraum beitreten
     * <p>
     * Zu nächst wird überprüft, ob der angegebene Warteraum existiert, falls nicht, wird false zurückgegeben, anderen, falls
     * wird der angegebene Nutzer diesem Warteraum zugeordnet und tritt diesem bei (true wird zurückgegeben).
     * Sollte der Nutzer jedoch schon einem Warteraum zugeordnet sein, wird auch false zurückgegeben.
     * Daraufhin wird der Warteraum aktualisiert.
     *
     * @param benutzername Benutzername des Clients, der die Aktion ausgelöst hat
     * @param raumId       Raum-ID
     * @return Warteraum beigetreten: true, sonst false
     */
    boolean warteraumBeitreten(String benutzername, long raumId);

    /**
     * Warteraum verlassen
     * <p>
     * zunächst wird überprüft wie viele spieler sich noch im warteraum befinden, sollte der aktuelle benutzer der letzte
     * spieler im Warteraum sein wird er diesem nicht länger zugeordnet und verlässt ihn woraufhin der Warteraum gelöscht wird.
     * Sollte der Nutzer nicht der letzte Spieler in Warteraum sein, wird er diesem nicht länger zugeordnet und verlässt diesen.
     * Daraufhin wird der Warteraum aktualisiert.
     *
     * @param benutzername Benutzername des Clients, der die Aktion ausgelöst hat
     * @param raumId       RaumId des raumes in dem sich der Nutzer aktuell befindet
     */
    void warteraumVerlassen(String benutzername, long raumId);

    /**
     * Bot hinzufügen
     * <p>
     * Zunächst wird überprüft ob der angegebene benutzername dem angegebenen Warteraum zugeordnet ist er dies nicht wird false zurückgegeben.
     * Daraufhin wird überprüft, ob sich bereits insgesamt 4 spieler und bots im Raum befinden, ist dies der Fall wird false zurückgegeben.
     * Anderenfalls wird ein Bot in den Warteraum hinzugefügt, die bot anzahl wird erhöht und es wird true zurückgegeben.
     * Daraufhin wird der Warteraum aktualisiert.
     *
     * @param benutzername Benutzername des Clients, der die Aktion ausgelöst hat
     * @param raumId       RaumId des raumes in dem sich der Nutzer aktuell befindet
     * @return Bot hinzugefügt: true, sonst false
     */
    boolean botHinzufuegen(String benutzername, long raumId);

    /**
     * Bot Entfernen
     * <p>
     * Zunächst wird überprüft ob der angegebene benutzername dem angegebenen Warteraum zugeordnet ist er dies nicht wird false zurückgegeben.
     * Daraufhin wird geprüft, ob mindestens ein bot im Raum ist, ist dies nicht der Fall wird false zurückgegeben, anderenfalls wird ein
     * Bot aus dem Warteraum entfernt, die bot anzahl heruntergesetzt und true zurückgegeben.
     * Daraufhin wird der Warteraum aktualisiert.
     *
     * @param benutzername Benutzername des Clients, der die Aktion ausgelöst hat
     * @param raumId       RaumId des raumes in dem sich der Nutzer aktuell befindet
     * @return Bot entfernt: true, sonst false
     */
    boolean botEntfernen(String benutzername, long raumId);

    /**
     * Spiel starten
     * <p>
     * Zunächst wird überprüft ob der angegebene benutzername dem angegebenen Warteraum zugeordnet ist er dies nicht wird false zurückgegeben.
     * Erstellt zunächst ein neues spiel mit allen dem Warteraum momentan zugeordneten Nutzern und Bots.
     * Teilt darauf den Clients der Nutzer mit, dass das Spiel gestartet wurde.
     * Daraufhin wird der Warteraum aktualisiert.
     *
     * @param benutzername Benutzername des Clients, der die Aktion ausgelöst hat
     * @param raumId       RaumId des raumes in dem sich der Nutzer aktuell befindet
     * @return Spiel gestartet: true, sonst false
     */
    boolean spielStarten(String benutzername, long raumId);

    /**
     * Spieldesign ändern
     * <p>
     * Zunächst wird überprüft ob der angegebene benutzername dem angegebenen Warteraum zugeordnet ist er dies nicht wird nichts unternommen.
     * Daraufhin wird geprüft, ob das angegebene design schon das ausgewählte ist, ist dies nicht der Fall, wird das Design des Warteraums angepasst
     * Anderenfalls wird nichts getan.
     *
     * @param benutzername Benutzername des Clients, der die Aktion ausgelöst hat
     * @param raumId       RaumId des raumes in dem sich der Nutzer aktuell befindet
     * @param design       Design
     */
    void designAnpassen(String benutzername, String design, long raumId);

    void anClientSendenRaumauswahlSetzen(AnClientSendenRaumauswahl anClientSendenRaumauswahl);
}
