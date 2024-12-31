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

        // Draw the wheels (one on left and one on right)
        double wheelLength = 20; // Length of the wheel

        // Left wheel (just to the left of the circle, exactly at x - radius)
        double wheelXLeft = x - radius; // Positioning the left wheel (touching the circle)
        double wheelYLeft = y;

        // Right wheel (just to the right of the circle, exactly at x + radius)
        double wheelXRight = x + radius; // Positioning the right wheel (touching the circle)
        double wheelYRight = y;

        // Draw wheels as thick lines (parallel to each other)
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(5);
        gc.strokeLine(wheelXLeft, wheelYLeft - wheelLength / 2, wheelXLeft, wheelYLeft + wheelLength / 2);  // Left wheel
        gc.strokeLine(wheelXRight, wheelYRight - wheelLength / 2, wheelXRight, wheelYRight + wheelLength / 2);  // Right wheel
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