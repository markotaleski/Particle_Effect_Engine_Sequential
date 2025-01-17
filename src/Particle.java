import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Particle {
    private double x, y;
    private double Vx, Vy;
    private double ttl;
    final private double decay = 0.016;
    final private static double gravity = 0.005;
    final private static double lossV = 0.8;
    final private double radius = 5;
    private double width, height;

    public Particle(double x, double y, double Vx, double Vy, double ttl,  double width, double height) {
        this.x = x;
        this.y = y;
        this.Vx = Vx;
        this.Vy = Vy;
        this.ttl = ttl;
        this.width = width;
        this.height = height;
    }

    public void update() {
        Vy += gravity;
        ttl -= decay;
        x += Vx ;
        y += Vy ;


        if (x <= 0) { // left wall collision
            x = 0;
            Vx = -Vx * lossV;
        } else if (x >= width) { // right wall collision
            x = width;
            Vx = -Vx * lossV;
        }

        if (y <= 0) { // top wall collision
            y = 0;
            Vy = -Vy * lossV;
        } else if (y >= height) { // bottom wall collision
            y = height;
            Vy = -Vy * lossV;
        }
    }

    public void render(GraphicsContext g) {

        g.setFill(Color.rgb(0, 0, 255, 1.0));
        g.fillOval(x, y, 5, 5);
    }

    public boolean isAlive() {
        return ttl > 0;
    }
    public void colision(Particle other) {
        double dX = other.x - this.x; // distance in x
        double dY = other.y - this.y; // distance in y
        double distance = Math.sqrt(dX * dX + dY * dY); // euclidean distance

        if (distance < this.radius + other.radius) { // colision detected
            //calculate normal component of velocity
            double normalX = dX / distance;
            double normalY = dY / distance;

            // relative velocity
            double relativeVelX = other.Vx - this.Vx;
            double relativeVelY = other.Vy - this.Vy;

            //velocity along the normal direction
            double velNormal = relativeVelX * normalX + relativeVelY * normalY;

            if (velNormal > 0) {
                return;
            }

            double momentum  = -2 * velNormal / 2;

            //change in velocity
            this.Vx -= momentum  * normalX;
            this.Vy -= momentum  * normalY;

            other.Vx += momentum  * normalX;
            other.Vy += momentum  * normalY;
        }
    }
    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getRadius() {
        return radius;
    }
}