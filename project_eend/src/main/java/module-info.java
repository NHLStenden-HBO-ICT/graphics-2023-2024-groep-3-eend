module com.example.project_eend {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;
    requires java.desktop;

    opens com.example.project_eend to javafx.fxml;
    exports com.example.project_eend;
}