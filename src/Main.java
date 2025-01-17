import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Main extends Application {
    private Emitter emitter;
    private Canvas canvas;
    private GraphicsContext g;



    @Override
    public void start(Stage primaryStage) {

        Input input = new Input(this);  //reference of Main class
        input.inputs();  //show GUI


        primaryStage.setTitle("Particle Effect Engine");
    }


    public void build(int nParticles, int width, int height, double emitterX, double emitterY, String emitType) {


        // initialize the emitter
        emitter = new Emitter(emitterX, emitterY, 5, nParticles, emitType, width, height);


        canvas = new Canvas(width, height);
        g = canvas.getGraphicsContext2D();

        // create the scene and add the canvas
        Scene scene = new Scene(new StackPane(canvas), width, height);
        Stage stage = new Stage();
        stage.setTitle("Particle Simulation");
        stage.setScene(scene);
        stage.show();


        animation();
    }

    private void animation() {
        AnimationTimer timer = new AnimationTimer() {
            private long startTime = System.nanoTime();  // Store the start time of the animation
            private boolean isStarted = false;

            @Override
            public void handle(long now) {
                if (!isStarted) {
                    startTime = System.nanoTime();
                    isStarted = true;
                }
                g.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
                emitter.emit();
                emitter.update();
                emitter.render(g);

                if (emitter.finished()) {
                    long endTime = System.nanoTime();
                    double totalTimeInSeconds = (endTime - startTime) / 1_000_000_000.0;
                    System.out.println("Total animation time: " + totalTimeInSeconds + " seconds");


                }
            }
        };
        timer.start();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
