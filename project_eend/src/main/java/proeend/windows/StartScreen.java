package proeend.windows;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import proeend.Main;

/**
 * De klasse StartScreen representeert het gebruikersinterface-scherm die bij opstarten wordt getoond.
 * Het biedt knoppen om verschillende scènes te renderen en andere acties uit te voeren.
 */
public class StartScreen extends VBox {
    private final Label infoLabel;
    /**
     * Initialiseert een StartScreen-object.
     *
     * @param main De instantie van de Main-klasse die wordt gebruikt om knopacties en scène-rendering te beheren.
     */
    public StartScreen(Main main) {

        this.setAlignment(javafx.geometry.Pos.CENTER);

        infoLabel = new Label();
        infoLabel.setStyle("-fx-font-size: 20px; -fx-text-fill: black;");
        infoLabel.setPadding(new javafx.geometry.Insets(20));

        Button badeend = new Button("Badeend scene renderen");
        badeend.setStyle("-fx-background-color: white; -fx-text-fill: black;");
        badeend.setMinSize(300, 50);
        badeend.setOnAction(e -> {
            Main.setButtonClicked(1);
            main.renderDuck();
        });

        Button case1 = new Button("Scene 1 renderen");
        case1.setStyle("-fx-background-color: white; -fx-text-fill: black;");
        case1.setMinSize(300, 50);
        case1.setOnAction(e -> {
            Main.setButtonClicked(1);
            main.caseButtonClicked();
        });

        Button case2 = new Button("Scene 2 renderen");
        case2.setStyle("-fx-background-color: white; -fx-text-fill: black;");
        case2.setMinSize(300, 50);
        case2.setOnAction(e -> {
            Main.setButtonClicked(2);
            main.caseButtonClicked();
        });

        Button case3 = new Button("Scene 3 renderen");
        case3.setStyle("-fx-background-color: white; -fx-text-fill: black;");
        case3.setMinSize(300, 50);
        case3.setOnAction(e -> {
            Main.setButtonClicked(3);
            main.caseButtonClicked();
        });

        Button shutdownButton = new Button("Afsluiten");
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

    /**
     * Creëert en retourneert de titellabel voor het StartScreen.
     *
     * @return De titellabel met de gespecificeerde stijl.
     */
    private Label title(){
        Label title = new Label("Project Graphics");
        title.setStyle("-fx-font-size: 50px; -fx-text-fill: black;");
        title.setPadding(new javafx.geometry.Insets(20));
        return title;
    }

    /**
     * Stelt de tekst in van de informatielabel op het StartScreen.
     *
     * @param info De tekst die op de informatielabel moet worden weergegeven.
     */
    public void setInfoLabel(String info){
        Platform.runLater(() -> infoLabel.setText(info));
    }
};

