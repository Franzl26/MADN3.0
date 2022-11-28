module madn.client {
    requires transitive madn.common;
    requires javafx.media;
    requires javafx.controls;

    opens edu.unibw.sse.madn.ansicht;

    exports edu.unibw.sse.madn.ansicht;
    exports edu.unibw.sse.madn.clientKomm;
    exports edu.unibw.sse.madn.datenClient;
    exports edu.unibw.sse.madn.app;
    opens edu.unibw.sse.madn.app;
    exports edu.unibw.sse.madn.ansicht.Impl;
    opens edu.unibw.sse.madn.ansicht.Impl;
}