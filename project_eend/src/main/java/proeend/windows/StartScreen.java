package proeend.windows;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
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
    private final String fontSize = "-fx-fontsize: 24;";
    private final String fontSizeTitle = "-fx-fontsize: 48;";
    private final String backGround = "-fx-background-color: #fff;";
    private final String textFill = "-fx-text-fill: #000;";
    private final String fontFamily = "-fx-font-family: Roboto;";
    /**
     * Initialiseert een StartScreen-object.
     *
     * @param main De instantie van de Main-klasse die wordt gebruikt om knopacties en scène-rendering te beheren.
     */
    public StartScreen(Main main) {

        this.setAlignment(Pos.TOP_CENTER);

        infoLabel = new Label();
        infoLabel.setStyle(fontSize + textFill + fontFamily);
        infoLabel.setPadding(new javafx.geometry.Insets(10));

/*        Button badeend = new Button("Badeend scene renderen");
        badeend.setStyle(fontSize + textFill + fontFamily);
        badeend.setOnAction(e -> {
            Main.setButtonClicked(8);
            main.renderDuck();
        });*/


        Button shutdownButton = new Button("Afsluiten");
        shutdownButton.setStyle(fontSize + textFill + fontFamily);
        shutdownButton.setOnAction(e -> {
            System.exit(0);
        });

        setMargin(shutdownButton, new Insets(10));
        setMargin(infoLabel, new Insets(10));

        this.getChildren().addAll(
                title(),
                infoLabel
        );

        Button[] buttons = new Button[4];

        String[] scenes = {
                "Gekleurde en glazen bal",
                "Spiegelende bol",
                "Wereld bol, zonsondergang",
                "Badeend op ijs",
        };

        for (int i = 0; i < buttons.length; i++){
            buttons[i] = new Button("Render scene: " + scenes[i]);
            buttons[i].setStyle(fontSize + textFill + fontFamily);
            int finalI = i;
            buttons[i].setOnAction(e -> {
                main.caseButtonClicked(finalI);
            });
            setMargin(buttons[i], new Insets(10));

            this.getChildren().add(buttons[i]);
        }

        this.getChildren().add(shutdownButton);
    }

    /**
     * Creëert en retourneert de titellabel voor het StartScreen.
     *
     * @return De titellabel met de gespecificeerde stijl.
     */
    private Label title(){
        Label title = new Label("Project Graphics");
        title.setStyle(fontSizeTitle + textFill + fontFamily);
        title.setPadding(new javafx.geometry.Insets(10));
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

