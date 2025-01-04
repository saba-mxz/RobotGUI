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
        // Draw the robot's body (circle)
        gc.setFill(javafx.scene.paint.Color.BLUE);
        double diameter = radius * 2;
        gc.fillOval(x - radius, y - radius, diameter, diameter);  // Draw the robot as a circle

        // Draw small black wheels (circles) as feet
        double wheelRadius = 5; // Radius of the small wheels

        // Calculate the positions for the wheels based on the robot's movement direction
        double angle = Math.atan2(speedY, speedX); // Calculate the angle of movement

        // Adjust the angle by 45 degrees to the left
        double leftAngle = angle - Math.PI / 4;
        double rightAngle = angle + Math.PI / 4;

        // Left wheel position
        double wheelXLeft = x + radius * Math.cos(leftAngle) - wheelRadius;
        double wheelYLeft = y + radius * Math.sin(leftAngle) - wheelRadius;

        // Right wheel position
        double wheelXRight = x + radius * Math.cos(rightAngle) - wheelRadius;
        double wheelYRight = y + radius * Math.sin(rightAngle) - wheelRadius;

        // Draw wheels as small circles
        gc.setFill(javafx.scene.paint.Color.BLACK);
        gc.fillOval(wheelXLeft, wheelYLeft, wheelRadius * 2, wheelRadius * 2);  // Left wheel
        gc.fillOval(wheelXRight, wheelYRight, wheelRadius * 2, wheelRadius * 2);  // Right wheel
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