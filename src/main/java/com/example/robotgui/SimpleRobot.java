package com.example.robotgui;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import java.io.Serializable;

public class SimpleRobot extends ArenaItem implements Serializable {
    protected double angle;  // Angle the robot is facing
    private double speed;  // Speed of the robot
    private int health;  // Health of the robot

    public SimpleRobot() {
        super(0, 0, 0);
        this.angle = 0;
        this.speed = 0;
        this.health = 100;  // Initialize health
    }

    public SimpleRobot(double x, double y, double radius, double angle, double speed) {
        super(x, y, radius);
        this.angle = angle;
        this.speed = speed;
        this.health = 100;  // Initialize health
    }

    public double getSpeed() {
        return speed;
    }

    public double getAngle() {
        return angle;
    }

    public int getHealth() {
        return health;
    }

    public void reduceHealth(int amount) {
        this.health -= amount;
        if (this.health < 0) {
            this.health = 0;
        }
    }

    @Override
    public void showItem(GraphicsContext gc) {
        // Draw the robot's body (circle)
        gc.setFill(Color.BLUE);
        gc.fillOval(getX() - getRadius(), getY() - getRadius(), getRadius() * 2, getRadius() * 2);  // Draw circle body

        // Draw the wheels (one on top and one on bottom)
        double wheelLength = 20; // Length of the wheel

        // Top wheel (just above the circle, exactly at y - radius)
        double wheelXTop = getX();
        double wheelYTop = getY() - getRadius(); // Positioning the top wheel (touching the circle)

        // Bottom wheel (just below the circle, exactly at y + radius)
        double wheelXBottom = getX();
        double wheelYBottom = getY() + getRadius(); // Positioning the bottom wheel (touching the circle)

        // Draw wheels as thick lines (parallel to each other)
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(5);
        gc.strokeLine(wheelXTop - wheelLength / 2, wheelYTop, wheelXTop + wheelLength / 2, wheelYTop);  // Top wheel
        gc.strokeLine(wheelXBottom - wheelLength / 2, wheelYBottom, wheelXBottom + wheelLength / 2, wheelYBottom);  // Bottom wheel
    }

    @Override
    public void move(RobotArena arena) {
        double newX = calcX(speed, angle);
        double newY = calcY(speed, angle);

        // Use the getter and setter methods to handle movement
        if (arena.canMove(newX, newY, getRadius(), this)) {
            setX(newX);
            setY(newY);
        } else {
            // Reverse direction on collision
            angle = (angle + 180) % 360;
        }
    }

    // Helper methods for calculating X and Y positions based on speed and angle
    protected double calcX(double speed, double angle) {
        return getX() + speed * Math.cos(Math.toRadians(angle));
    }

    protected double calcY(double speed, double angle) {
        return getY() + speed * Math.sin(Math.toRadians(angle));
    }

    // Implement the contains method to check if a point (px, py) is inside the robot's radius
    @Override
    public boolean contains(double px, double py) {
        // Check if the point (px, py) is within the bounds of the robot (circle)
        double distance = Math.sqrt(Math.pow(px - getX(), 2) + Math.pow(py - getY(), 2));
        return distance <= getRadius();  // True if the point is within the radius of the robot
    }
}