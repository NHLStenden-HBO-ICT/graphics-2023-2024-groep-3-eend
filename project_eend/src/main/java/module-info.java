module com.example.project_eend {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;
    requires java.desktop;

    exports proeend;
    opens proeend to javafx.fxml;
    exports proeend.misc;
    opens proeend.misc to javafx.fxml;
    exports proeend.records;
    opens proeend.records to javafx.fxml;
    exports proeend.math;
    opens proeend.math to javafx.fxml;
}