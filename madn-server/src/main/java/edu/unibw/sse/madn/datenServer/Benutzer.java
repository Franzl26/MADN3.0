package edu.unibw.sse.madn.datenServer;

public record Benutzer(String benutzername, byte[] passwortHash, long zuletztEingeloggt) {
}
