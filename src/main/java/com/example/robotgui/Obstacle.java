package com.example.robotgui;

import javafx.scene.canvas.GraphicsContext;
import java.io.Serializable;

public class Obstacle extends ArenaItem implements Serializable {

    public Obstacle() {
        super();
    }

    public Obstacle(double x, double y) {
        super(x, y, 25); // Assuming obstacle is a square with radius 25 for collision check
    }

    @Override
    public void showItem(GraphicsContext gc) {
        // Drawing the obstacle (simplified)
        gc.setFill(javafx.scene.paint.Color.RED); // Color of the obstacle
        gc.fillRect(x - radius, y - radius, radius * 2, radius * 2); // Draw as a square obstacle
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