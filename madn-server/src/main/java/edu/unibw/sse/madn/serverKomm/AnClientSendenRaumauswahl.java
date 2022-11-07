package edu.unibw.sse.madn.serverKomm;

import edu.unibw.sse.madn.base.Warteraeume;
import edu.unibw.sse.madn.komm.Sitzung;

public interface AnClientSendenRaumauswahl {
    boolean raeumeUpdaten(Sitzung sitzung, Warteraeume warteraeume);

    boolean warteraumNamenUpdaten(Sitzung sitzung, String[] namen);

    boolean spielStartet(Sitzung sitzung, String design);
}
