module com.example.project_eend {
    requires javafx.controls;
    requires java.desktop;

    exports proeend;
    opens proeend to javafx.fxml;
    exports proeend.material.texture;
    opens proeend.material.texture to javafx.fxml;
    exports proeend.material.pdf;
    opens proeend.material.pdf to javafx.fxml;
    exports proeend.material;
    opens proeend.material to javafx.fxml;
    exports proeend.hittable;
    opens proeend.hittable to javafx.fxml;
    exports proeend.misc;
    opens proeend.misc to javafx.fxml;
    exports proeend.records;
    opens proeend.records to javafx.fxml;
    exports proeend.math;
    opens proeend.math to javafx.fxml;
    exports proeend.windows;
    opens proeend.windows to javafx.fxml;
}