package com.example.robotgui;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import java.io.Serializable;
import java.util.Random;

public class BeamSensorRobot extends ArenaItem implements Serializable {
    private double speed;
    private double angle;
    private Random random;
    private int health;  // Health of the robot

    public BeamSensorRobot() {
        super();
        this.speed = 0;
        this.angle = 0;
        this.random = new Random();
        this.health = 100;  // Initialize health
    }

    public BeamSensorRobot(double x, double y, double speed) {
        super(x, y, 15); // Assuming robot is a circle with radius 15 for collision check
        this.speed = speed;
        this.angle = 0;
        this.random = new Random();
        this.health = 100;  // Initialize health
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
        // Draw the robot's body (black circle)
        gc.setFill(Color.BLACK);
        double diameter = radius * 2;
        gc.fillOval(x - radius, y - radius, diameter, diameter);

        // Draw the sensor beam (green sector)
        gc.setFill(Color.TEAL);
        double beamAngle = 45; // Angle of the beam in degrees
        double beamRadius = radius * 2; // The beam's length can be adjusted
        gc.fillArc(
                x - beamRadius, y - beamRadius, beamRadius * 2, beamRadius * 2,
                -angle - beamAngle / 2, beamAngle, javafx.scene.shape.ArcType.ROUND
        );
    }

    public void move(RobotArena arena) {
        double newX = calcX(speed, angle);
        double newY = calcY(speed, angle);

        // Check if the new position is valid (no collisions, within bounds)
        if (arena.canMove(newX, newY, radius, this)) {
            x = newX;
            y = newY;
        } else {
            // Change direction randomly if collision occurs
            angle = random.nextInt(360);
        }
    }

    // Calculate the new X position based on speed and angle
    public double calcX(double speed, double angle) {
        return x + speed * Math.cos(Math.toRadians(angle));
    }

    // Calculate the new Y position based on speed and angle
    public double calcY(double speed, double angle) {
        return y + speed * Math.sin(Math.toRadians(angle));
    }

    @Override
    public boolean contains(double x, double y) {
        double distance = Math.sqrt(Math.pow(x - this.x, 2) + Math.pow(y - this.y, 2));
        return distance <= radius;
    }

    @Override
    public double getAngle() {
        return angle;
    }

    @Override
    public double getSpeed() {
        return speed;
    }
}