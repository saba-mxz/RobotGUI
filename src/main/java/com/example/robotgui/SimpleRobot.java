package com.example.robotgui;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class SimpleRobot extends ArenaItem {
    protected double angle;  // Angle the robot is facing (changed to protected)
    private double speed;  // Speed of the robot

    public SimpleRobot(double x, double y, double radius, double angle, double speed) {
        super(x, y, radius);
        this.angle = angle;
        this.speed = speed;
    }

    public double getSpeed() {
        return speed;
    }

    @Override
    public void showItem(GraphicsContext gc) {
        // Draw the robot's body (circle)
        gc.setFill(Color.BLUE);
        gc.fillOval(x - radius, y - radius, radius * 2, radius * 2);  // Draw circle body

        // Draw the wheels (one on top and one on bottom)
        double wheelLength = 20; // Length of the wheel

        // Top wheel (just above the circle, exactly at y - radius)
        double wheelXTop = x;
        double wheelYTop = y - radius; // Positioning the top wheel (touching the circle)

        // Bottom wheel (just below the circle, exactly at y + radius)
        double wheelXBottom = x;
        double wheelYBottom = y + radius; // Positioning the bottom wheel (touching the circle)

        // Draw wheels as thick lines (parallel to each other)
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(5);
        gc.strokeLine(wheelXTop - wheelLength / 2, wheelYTop, wheelXTop + wheelLength / 2, wheelYTop);  // Top wheel
        gc.strokeLine(wheelXBottom - wheelLength / 2, wheelYBottom, wheelXBottom + wheelLength / 2, wheelYBottom);  // Bottom wheel
    }


    public void move(RobotArena arena) {
        double newX = calcX(speed, angle);
        double newY = calcY(speed, angle);

        if (arena.canMove(newX, newY, radius, this)) {
            x = newX;
            y = newY;
        } else {
            // Reverse direction on collision
            angle = (angle + 180) % 360;
        }
    }
}