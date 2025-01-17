import javafx.scene.canvas.GraphicsContext;
import java.util.ArrayList;
import java.util.List;

public class Emitter {
    private double x, y;
    private List<Particle> particles;
    private double emitRate;
    private int maxPart;
    private String emitType;
    private double width, height;



    public Emitter(double x, double y, double emitRate, int maxPart, String emitType, double width, double height) {
        this.x = x;
        this.y = y;
        this.emitRate = emitRate;
        this.particles = new ArrayList<>();
        this.maxPart = maxPart;
        this.emitType = emitType;
        this.width = width;
        this.height = height;
    }

    public void emit() {
        if("continuous".equals(emitType)) { // Continuous emission
            if (particles.size() < maxPart) {
                for (int i = 0; i < emitRate; i++) {
                    particles.add(new Particle(x, y, Math.random() * 2 - 1, Math.random() * 2 - 1, 5.0, width, height));
                }
            }
        }
        else if ("burst".equals(emitType)) { // Burst emision
            if (particles.size() == 0) {
                for (int i = 0; i < maxPart; i++) {
                    particles.add(new Particle(x, y, Math.random() * 2 - 1, Math.random() * 2 - 1, 5.0, width, height));
                }
            }
        }
    }

    // update particles (apply physics and check colisions)
    public void update() {
        for (int i = 0; i < particles.size(); i++) {
            Particle p1 = particles.get(i);
            p1.update();

            //colisions
            for (int j = i + 1; j < particles.size(); j++) {
                Particle p2 = particles.get(j);
                if (Math.abs(p1.getX() - p2.getX()) < p1.getRadius() * 2 &&
                        Math.abs(p1.getY() - p2.getY()) < p1.getRadius() * 2) {
                    p1.colision(p2);
                }
            }
        }

        //remove
        for (int i = 0; i < particles.size(); i++) {
            if (!particles.get(i).isAlive()) {
                particles.remove(i);
            }
        }

    }

    public void render(GraphicsContext g) {
        for (Particle particle : particles) {
            particle.render(g);
        }
    }

    public boolean finished() {
        return particles.isEmpty();
    }
}
