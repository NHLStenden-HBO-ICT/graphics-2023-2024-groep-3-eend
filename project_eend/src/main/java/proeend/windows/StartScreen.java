package proeend.windows;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import proeend.Main;

public class StartScreen extends VBox {
    private Label infoLabel;
    private Button badeend;
    private Button case1;
    private Button case2;
    private Button case3;
    private Button shutdownButton;

    public StartScreen(Main main) {


        this.setAlignment(javafx.geometry.Pos.CENTER);

        infoLabel = new Label();
        infoLabel.setStyle("-fx-font-size: 20px; -fx-text-fill: black;");
        infoLabel.setPadding(new javafx.geometry.Insets(20));


        badeend = new Button("Badeend scene renderen");
        badeend.setStyle("-fx-background-color: white; -fx-text-fill: black;");
        badeend.setMinSize(300, 50);
        badeend.setOnAction(e -> {
            main.setButtonClicked(1);
            main.renderDuck();
        });

        case1 = new Button("Scene 1 renderen");
        case1.setStyle("-fx-background-color: white; -fx-text-fill: black;");
        case1.setMinSize(300, 50);
        case1.setOnAction(e -> {
            main.setButtonClicked(1);
            main.caseButtonClicked();
        });

        case2 = new Button("Scene 2 renderen");
        case2.setStyle("-fx-background-color: white; -fx-text-fill: black;");
        case2.setMinSize(300, 50);
        case2.setOnAction(e -> {
            main.setButtonClicked(2);
            main.caseButtonClicked();
        });

        case3 = new Button("Scene 3 renderen");
        case3.setStyle("-fx-background-color: white; -fx-text-fill: black;");
        case3.setMinSize(300, 50);
        case3.setOnAction(e -> {
            main.setButtonClicked(3);
            main.caseButtonClicked();
        });

        shutdownButton = new Button("Afsluiten");
        shutdownButton.setStyle("-fx-background-color: white; -fx-text-fill: black;");
        shutdownButton.setMinSize(300, 50);
        shutdownButton.setOnAction(e -> {
            System.exit(0);
        });

        this.getChildren().addAll(
                title(),
                badeend,
                case1,
                case2,
                case3,
                shutdownButton,
                infoLabel
        );

        setMargin(badeend, new Insets(10));
        setMargin(case1, new Insets(10));
        setMargin(case2, new Insets(10));
        setMargin(case3, new Insets(10));
        setMargin(shutdownButton, new Insets(10));
        setMargin(infoLabel, new Insets(10));
    }

    private Label title(){
        Label title = new Label("Project Graphics");
        title.setStyle("-fx-font-size: 50px; -fx-text-fill: black;");
        title.setPadding(new javafx.geometry.Insets(20));
        return title;
    }

    public void setInfoLabel(String info){
        Platform.runLater(() -> infoLabel.setText(info));
    }
};

