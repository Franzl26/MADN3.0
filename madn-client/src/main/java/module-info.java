module madn.client {
    requires transitive madn.common;
    requires javafx.media;

    opens edu.unibw.sse.madn.ansicht;

    exports edu.unibw.sse.madn.ansicht;
}