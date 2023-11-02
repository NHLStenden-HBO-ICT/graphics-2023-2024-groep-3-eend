package proeend.windows;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import proeend.Main;

public class StartScreen extends VBox {
    private final Label infoLabel = new Label();

    public StartScreen(Main main) {
        setAlignment(javafx.geometry.Pos.CENTER);

        infoLabel.setStyle("-fx-font-size: 20px; -fx-text-fill: blue;");
        infoLabel.setPadding(new Insets(20));

        // Array to store buttons
        Button[] buttons = new Button[7];
        for (int i = 0; i < buttons.length; i++) {
            buttons[i] = new Button();
            buttons[i].setStyle("-fx-background-color: white; -fx-text-fill: blue;");
            buttons[i].setMinSize(300, 50);

            // Set button text and action based on the loop index
            if (i == 0) {
                buttons[i].setText("Badeend scene renderen");
                buttons[i].setOnAction(e -> {
                    Main.setButtonClicked(1);
                    main.renderDuck();
                });
            } else {
                buttons[i].setText("Scene " + i + " renderen");
                final int index = i;
                buttons[i].setOnAction(e -> {
                    Main.setButtonClicked(index);
                    main.caseButtonClicked();
                });
            }
        }

        getChildren().addAll(title(), infoLabel);
        getChildren().addAll(buttons);

        for (Button button : buttons) {
            VBox.setMargin(button, new Insets(10));
        }
    }

    private Label title() {
        Label title = new Label("Project Graphics");
        title.setStyle("-fx-font-size: 50px; -fx-text-fill: blue;");
        title.setPadding(new Insets(20));
        return title;
    }

    public void setInfoLabel(String info) {
        infoLabel.setText(info);
    }
}
