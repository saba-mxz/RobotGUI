package com.example.robotgui;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.Stop;
import java.io.Serializable;

public class Obstacle extends ArenaItem implements Serializable {

    public Obstacle() {
        super();
    }

    public Obstacle(double x, double y) {
        super(x, y, 15); // Assuming obstacle is a circle with radius 15 for collision check
    }

    @Override
    public void showItem(GraphicsContext gc) {
        // Drawing the fire (using gradient and shapes)
        LinearGradient gradient = new LinearGradient(
                0, 0, 1, 1, true, CycleMethod.NO_CYCLE,
                new Stop(0, Color.RED),
                new Stop(1, Color.ORANGE)
        );
        gc.setFill(gradient);
        gc.fillOval(x - radius, y - radius, radius * 2, radius * 2); // Draw as a circle

        // Add some flame shapes
        gc.setFill(Color.DARKORANGE);
        gc.fillPolygon(
                new double[]{x, x - radius / 2, x + radius / 2},
                new double[]{y - radius, y + radius / 2, y + radius / 2},
                3
        );
        gc.fillPolygon(
                new double[]{x, x - radius / 3, x + radius / 3},
                new double[]{y - radius / 2, y + radius / 3, y + radius / 3},
                3
        );
    }

    @Override
    public boolean contains(double x, double y) {
        double distance = Math.sqrt(Math.pow(x - this.x, 2) + Math.pow(y - this.y, 2));
        return distance <= radius;
    }

    @Override
    public void move(RobotArena arena) {
        // Obstacles don't move, so this method can be empty
    }

    @Override
    public double getAngle() {
        return 0; // Obstacles don't have an angle
    }

    @Override
    public double getSpeed() {
        return 0; // Obstacles don't move, so their speed is 0
    }
}