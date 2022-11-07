package edu.unibw.sse.madn.base;

import java.io.Serializable;

public interface Warteraum extends Serializable {
    long id();

    String[] namen();
}
