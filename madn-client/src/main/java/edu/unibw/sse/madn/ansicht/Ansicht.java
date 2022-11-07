package edu.unibw.sse.madn.ansicht;

import edu.unibw.sse.madn.clientKomm.ClientKomm;
import edu.unibw.sse.madn.datenClient.DatenClient;

public interface Ansicht {
    void dateizugriffClientSetzen(DatenClient datenClient);

    void clientKommunikationSetzen(ClientKomm clientKomm);
}
