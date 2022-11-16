package edu.unibw.sse.madn.clientKomm;

import edu.unibw.sse.madn.base.Warteraum;

public class RaumverwaltungUpdatenFuerTest implements RaumverwaltungUpdaten{
    Warteraum[] warteraeume;

    @Override
    public synchronized void raeumeUpdaten(Warteraum[] warteraeume) {
        this.warteraeume = warteraeume;
        notifyAll();
    }

    public synchronized void waitForWarteraume(long time) {
        if (warteraeume == null) {
            try {
                wait(time);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
