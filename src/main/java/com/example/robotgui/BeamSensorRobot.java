package com.example.robotgui;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import java.io.Serializable;
import java.util.Random;

public class BeamSensorRobot extends ArenaItem implements Serializable {
    private double speed;
    private double angle;
    private Random random;

    public BeamSensorRobot() {
        super();
        this.speed = 0;
        this.angle = 0;
        this.random = new Random();
    }

    public BeamSensorRobot(double x, double y, double speed) {
        super(x, y, 15); // Assuming robot is a circle with radius 15 for collision check
        this.speed = speed;
        this.angle = 0;
        this.random = new Random();
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

        // Calculate beam end points
        double beamLength = radius * 2;
        double beamX = newX + beamLength * Math.cos(Math.toRadians(angle));
        double beamY = newY + beamLength * Math.sin(Math.toRadians(angle));

        // Check if the beam end points are within the arena boundaries and not colliding with obstacles
        if (arena.canMove(beamX, beamY, radius, this) && arena.canMove(newX, newY, radius, this)) {
            // Additional check for bottom boundary
            if (newY + radius <= 700) { // Assuming canvas height is 600
                x = newX;
                y = newY;
            } else {
                // Change direction randomly if hitting the bottom boundary
                angle = random.nextInt(360);
            }
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