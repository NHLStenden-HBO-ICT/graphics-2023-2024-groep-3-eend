module com.example.project_eend {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;
    requires java.desktop;

    exports proeend;
    opens proeend to javafx.fxml;
}