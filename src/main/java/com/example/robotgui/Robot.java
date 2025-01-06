package com.example.robotgui;

import javafx.scene.canvas.GraphicsContext;

public class Robot extends ArenaItem {
    private double speedX, speedY;

    public Robot(double x, double y, double speedX, double speedY) {
        super(x, y, 15); // Assuming robot is a circle with radius 15 for collision check
        this.speedX = speedX;
        this.speedY = speedY;
    }

    // Getter for speedX
    public double getSpeedX() {
        return speedX;
    }

    // Setter for speedX
    public void setSpeedX(double speedX) {
        this.speedX = speedX;
    }

    // Getter for speedY
    public double getSpeedY() {
        return speedY;
    }

    // Setter for speedY
    public void setSpeedY(double speedY) {
        this.speedY = speedY;
    }

    @Override
    public void showItem(GraphicsContext gc) {
        // Draw the robot's body (circle)
        gc.setFill(javafx.scene.paint.Color.BLUE);
        double diameter = getRadius() * 2;  // Use the getter method for radius
        gc.fillOval(getX() - getRadius(), getY() - getRadius(), diameter, diameter);  // Draw the robot as a circle

        // Draw small black wheels (circles) as feet
        double wheelRadius = 5; // Radius of the small wheels

        // Calculate the positions for the wheels based on the robot's movement direction
        double angle = Math.atan2(speedY, speedX); // Calculate the angle of movement

        // Adjust the angle by 45 degrees to the left
        double leftAngle = angle - Math.PI / 4;
        double rightAngle = angle + Math.PI / 4;

        // Left wheel position
        double wheelXLeft = getX() + getRadius() * Math.cos(leftAngle) - wheelRadius;
        double wheelYLeft = getY() + getRadius() * Math.sin(leftAngle) - wheelRadius;

        // Right wheel position
        double wheelXRight = getX() + getRadius() * Math.cos(rightAngle) - wheelRadius;
        double wheelYRight = getY() + getRadius() * Math.sin(rightAngle) - wheelRadius;

        // Draw wheels as small circles
        gc.setFill(javafx.scene.paint.Color.BLACK);
        gc.fillOval(wheelXLeft, wheelYLeft, wheelRadius * 2, wheelRadius * 2);  // Left wheel
        gc.fillOval(wheelXRight, wheelYRight, wheelRadius * 2, wheelRadius * 2);  // Right wheel
    }

    @Override
    public boolean contains(double x, double y) {
        // Check if the point (x, y) is inside the robot's circular area
        double dx = x - getX();  // x-distance from the robot center
        double dy = y - getY();  // y-distance from the robot center
        double distanceSquared = dx * dx + dy * dy;  // Distance squared from center

        // If the distance squared is less than or equal to the square of the radius, the point is inside
        return distanceSquared <= getRadius() * getRadius();
    }

    @Override
    public void move(RobotArena arena) {
        double newX = getX() + speedX; // Use getter for x
        double newY = getY() + speedY; // Use getter for y

        // Check if the new position is valid (no collisions, within bounds)
        if (arena.canMove(newX, newY, getRadius(), this)) { // Pass 'this' as the fourth parameter
            setX(newX); // Use setter for x
            setY(newY); // Use setter for y
        } else {
            // Reverse direction if collision occurs
            speedX = -speedX;
            speedY = -speedY;
        }
    }

    @Override
    public double getAngle() {
        return Math.atan2(speedY, speedX); // Calculate the angle of movement
    }

    @Override
    public double getSpeed() {
        return Math.sqrt(speedX * speedX + speedY * speedY); // Calculate the speed
    }
}
