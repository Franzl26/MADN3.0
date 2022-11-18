package edu.unibw.sse.madn.warteraumverwaltung;

public interface Raumauswahl {
    /**
     * meldet Client für die aktualisierung der Warteräume an
     *
     * @param benutzername
     */
    void fuerUpdatesAnmelden(String benutzername);

    /**
     * Warteraum erstellen(Domenik)
     *
     * Überprüft, ob bereits 25 aktive Warteräume existieren, ist dies der Fall wird false zurückgegeben,
     * ansonsten wird ein neuer Warteraum erstellt, der Nutzer wird diesem zugeordnet und tritt diesem bei.
     * Daraufhin wird er Warteraum aktualisiert
     *
     * @param benutzername
     * @return Warteraum erstellt: true, sonst false
     */
    boolean warteraumErstellen(String benutzername);

    /**
     * Warteraum beitreten(Domenik)
     *
     * Zu nächst wird überprüft, ob der angegebene Warteraum existiert, falls nicht, wird false zurückgegeben, anderen, falls
     * wird der angegebene Nutzer diesem Warteraum zugeordnet und tritt diesem bei (true wird zurückgegeben).
     * Daraufhin wird er Warteraum aktualisiert
     * Sollte der Nutzer jedoch schon einem Warteraum zugeordnet sein wird auch false zurückgegeben.
     *
     *
     * @param benutzername
     * @param raumId  Raum-ID
     * @return Warteraum beigetreten: true, sonst false
     */
    boolean warteraumBeitreten(String benutzername, long raumId);

    /**
     * Warteraum verlassen(Domenik)
     *
     * zunächst wird überprüft wie viele spieler sich noch in einem warteraum befinden, sollte der aktuelle benutzer der letzte
     * spieler im Warteraum sein wird er diesem nicht länger zugeordnet und verlässt ihn woraufhin der Warteraum gelöscht wird.
     * Sollte der Nutzer nicht der letzte Spieler in Warteraum wird er diesem nicht länger zugeordnet und verlässt diesen woraufhin der warteraum aktualisiert wird.
     *
     *
     * @param benutzername
     *
     */
    void warteraumVerlassen(String benutzername);

    /**
     * Bot hinzufügen(Domenik)
     *
     * Zunächst wird überprüft ob der angegebene benutzername einem Warteraum zugeordnet ist er dies nicht wird false zurückgegeben.
     * Daraufhin wird überprüft, ob sich bereits insgesamt 4 spieler und bots im Raum befinden, ist dies der Fall wird false zurückgegeben.
     * Anderenfalls wird ein Bot in den Warteraum hinzugefügt, die bot anzahl wird erhöht und es wird true zurückgegeben.
     * Daraufhin wird er Warteraum aktualisiert.
     *
     * @param benutzername
     * @return Bot hinzugefügt: true, sonst false
     */
    boolean botHinzufuegen(String benutzername);

    /**
     * Bot Entfernen(Domenik)
     *
     * Zunächst wird überprüft, ob der angegebene Nutzer einem Warteraum zugeordnet ist er dies nicht wird false zurückgegeben.
     * Daraufhin wird geprüft, ob mindestens ein bot im Raum ist, ist dies nicht der Fall wird false zurückgegeben, anderenfalls wird ein
     * Bot aus dem Warteraum entfernt, die bot anzahl heruntergesetzt und true zurückgegeben.
     * Daraufhin wird er Warteraum aktualisiert.
     *
     * @param benutzername
     * @return Bot entfernt: true, sonst false
     */
    boolean botEntfernen(String benutzername);

    /**
     * Spiel starten(Domenik)
     *
     * Zunächst wird überprüft ob der angegebene benutzername einem Warteraum zugeordnet ist er dies nicht wird false zurückgegeben.
     * Erstellt zunächst ein neues spiel mit allen dem Warteraum momentan zugeordneten Nutzern und Bots.
     * Teilt darauf den Clients der Nutzer mit, dass das Spiel gestartet wurde
     *
     * @param benutzername
     * @return Spiel gestartet: true, sonst false
     */
    boolean spielStarten(String benutzername);

    /**
     * Spieldesign ändern(Domenik)
     *
     * Zunächst wird überprüft ob der angegebene benutzername einem Warteraum zugeordnet ist er dies nicht wird nichts unternommen.
     * Daraufhin wird geprüft, ob das angegebene design schon das ausgewählte ist, ist dies nicht der Fall, wird das Design dess Warteraums angepasst
     * Anderenfalls wird nichts getan.
     * Daraufhin wird er Warteraum aktualisiert
     *
     * @param benutzername
     * @param design  Design
     */
    void designAnpassen(String benutzername, String design);
    void anClientSendenRaumauswahlSetzen(AnClientSendenRaumauswahl anClientSendenRaumauswahl);
}
