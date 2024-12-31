package com.example.robotgui;

import javafx.scene.canvas.GraphicsContext;

public class Robot extends ArenaItem {
    private double speedX, speedY;

    public Robot(double x, double y, double speedX, double speedY) {
        super(x, y, 15); // Assuming robot is a circle with radius 15 for collision check
        this.speedX = speedX;
        this.speedY = speedY;
    }

    @Override
    public void showItem(GraphicsContext gc) {
        gc.setFill(javafx.scene.paint.Color.BLUE);
        double diameter = radius * 2;
        gc.fillOval(x - radius, y - radius, diameter, diameter);  // Draw the robot as a circle
    }

    public void move(RobotArena arena) {
        double newX = x + speedX;
        double newY = y + speedY;

        // Check if the new position is valid (no collisions, within bounds)
        if (arena.canMove(newX, newY, radius, this)) { // Pass 'this' as the fourth parameter
            x = newX;
            y = newY;
        } else {
            // Reverse direction if collision occurs
            speedX = -speedX;
            speedY = -speedY;
        }
    }
}