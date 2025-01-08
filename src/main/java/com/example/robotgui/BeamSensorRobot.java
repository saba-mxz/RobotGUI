package com.example.robotgui;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.RadialGradient;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.Stop;
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
        // Draw the yellow light reflection (radial gradient)
        RadialGradient gradient = new RadialGradient(
                0, 0, x, y, radius * 3, false, CycleMethod.NO_CYCLE,
                new Stop(0, Color.YELLOW.deriveColor(1, 1, 1, 0.5)),
                new Stop(1, Color.TRANSPARENT)
        );
        gc.setFill(gradient);
        gc.fillOval(x - radius * 3, y - radius * 3, radius * 6, radius * 6);

        // Draw the robot's body (black circle)
        gc.setFill(Color.BLACK);
        double diameter = radius * 2;
        gc.fillOval(x - radius, y - radius, diameter, diameter);  // Draw the robot as a circle
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