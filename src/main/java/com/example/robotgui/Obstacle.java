package com.example.robotgui;

import javafx.scene.canvas.GraphicsContext;

public class Obstacle extends ArenaItem {

    public Obstacle(double x, double y) {
        super(x, y, 25); // Assuming obstacle is a square with radius 25 for collision check
    }

    @Override
    public void showItem(GraphicsContext gc) {
        // Drawing the obstacle (simplified)
        gc.setFill(javafx.scene.paint.Color.RED); // Color of the obstacle
        gc.fillRect(x - radius, y - radius, radius * 2, radius * 2); // Draw as a square obstacle
    }
}