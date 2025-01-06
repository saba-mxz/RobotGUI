package com.example.robotgui;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import java.util.Random;

public class DangerRobot extends SimpleRobot {
    private Random random;

    public DangerRobot(double x, double y, double radius, double angle, double speed) {
        super(x, y, radius, angle, speed * 2); // Double the speed
        this.random = new Random();
    }

    @Override
    public void showItem(GraphicsContext gc) {
        // Draw the robot's body (red circle)
        gc.setFill(Color.RED);
        gc.fillOval(x - radius, y - radius, radius * 2, radius * 2);

        // Draw the wheels, adjusted for direction
        drawWheels(gc);

        // Draw the exclamation mark inside the robot's body
        drawExclamationMark(gc);
    }

    private void drawWheels(GraphicsContext gc) {
        double wheelLength = 20; // Length of the wheel
        double wheelOffset = radius; // Offset to place wheels on the edge of the robot
        double angleRad = Math.toRadians(angle); // Convert angle to radians

        // Calculate the wheel end points based on the angle, rotated by 90 degrees
        double dx = (wheelLength / 2) * Math.cos(angleRad);
        double dy = (wheelLength / 2) * Math.sin(angleRad);

        // Top wheel center position (on the edge of the circle)
        double topWheelCenterX = x + wheelOffset * Math.sin(angleRad);
        double topWheelCenterY = y - wheelOffset * Math.cos(angleRad);

        // Bottom wheel center position (on the edge of the circle, opposite side)
        double bottomWheelCenterX = x - wheelOffset * Math.sin(angleRad);
        double bottomWheelCenterY = y + wheelOffset * Math.cos(angleRad);

        // Draw top wheel
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(5);
        gc.strokeLine(topWheelCenterX - dx, topWheelCenterY - dy, topWheelCenterX + dx, topWheelCenterY + dy);

        // Draw bottom wheel
        gc.strokeLine(bottomWheelCenterX - dx, bottomWheelCenterY - dy, bottomWheelCenterX + dx, bottomWheelCenterY + dy);
    }

    private void drawExclamationMark(GraphicsContext gc) {
        double markHeight = 22; // Height of the exclamation mark
        double markWidth = 6; // Width of the exclamation mark

        // Calculate the position of the exclamation mark inside the robot's body
        double markX = x;
        double markY = y - radius / 4;

        // Draw the exclamation mark
        gc.setFill(Color.BLACK);
        gc.fillRect(markX - markWidth / 2, markY - markHeight / 2, markWidth, markHeight - 7); // Main line
        gc.fillOval(markX - markWidth / 2, markY + markHeight / 2 - 1, markWidth, markWidth); // Dot
    }

    @Override
    public void move(RobotArena arena) {
        double angleRad = Math.toRadians(angle); // Convert angle to radians
        double speedX = getSpeed() * Math.cos(angleRad);
        double speedY = getSpeed() * Math.sin(angleRad);

        double newX = x + speedX;
        double newY = y + speedY;

        if (arena.canMove(newX, newY, radius, this)) {
            x = newX;
            y = newY;
        } else {
            // Change direction randomly if collision occurs
            angle = random.nextInt(360);
        }
    }
}