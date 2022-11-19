package edu.unibw.sse.madn.datenServer;

import java.util.Arrays;
import java.util.Objects;

public record Benutzer(String benutzername, byte[] passwortHash, long zuletztEingeloggt) {
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Benutzer benutzer = (Benutzer) o;

        if (zuletztEingeloggt != benutzer.zuletztEingeloggt) return false;
        if (!Objects.equals(benutzername, benutzer.benutzername))
            return false;
        return Arrays.equals(passwortHash, benutzer.passwortHash);
    }

    @Override
    public int hashCode() {
        int result = benutzername != null ? benutzername.hashCode() : 0;
        result = 31 * result + Arrays.hashCode(passwortHash);
        result = 31 * result + (int) (zuletztEingeloggt ^ (zuletztEingeloggt >>> 32));
        return result;
    }
}
