import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Input {

    private Main main;  // Reference to the Main class
    private TextField particleField;
    private TextField widthField;
    private TextField heightField;
    private TextField emitterXField;
    private TextField emitterYField;
    private ChoiceBox<String> emitTypeChoice;
    private Button startButton;

    public Input(Main main) {
        this.main = main;  // reference of Main class
    }

    public void inputs() {

        particleField = new TextField(); // input for num of particles
        particleField.setPromptText("Number of Particles");

        widthField = new TextField(); // input for width
        widthField.setPromptText("Window Width");

        heightField = new TextField(); // input for height
        heightField.setPromptText("Window Height");

        emitterXField = new TextField(); // input for x emitter
        emitterXField.setPromptText("Emitter X Position");

        emitterYField = new TextField(); // input for y emitter
        emitterYField.setPromptText("Emitter Y Position");

        emitTypeChoice = new ChoiceBox<>(); // emission type
        emitTypeChoice.getItems().addAll("continuous", "burst");
        emitTypeChoice.setValue("continuous");

        startButton = new Button("Start Simulation");


        startButton.setOnAction(event -> {
            try {
                int nParticles = Integer.parseInt(particleField.getText());
                int width = Integer.parseInt(widthField.getText());
                int height = Integer.parseInt(heightField.getText());
                double emitterX = Double.parseDouble(emitterXField.getText());
                double emitterY = Double.parseDouble(emitterYField.getText());
                String emitType = emitTypeChoice.getValue();
                // start simulation
                main.build(nParticles, width, height, emitterX, emitterY, emitType);
            } catch (NumberFormatException e) {
                // error in case of wrong
                System.out.println("Invalid input, please enter valid numbers");

            }
        });

        // Box layout
        VBox layout = new VBox(10, particleField, widthField, heightField, emitterXField, emitterYField, emitTypeChoice, startButton);


        Scene scene = new Scene(layout, 300, 400);
        Stage stage = new Stage();
        stage.setTitle("Particle System Configurator");
        stage.setScene(scene);
        stage.show();
    }
}
