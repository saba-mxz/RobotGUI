package com.example.robotgui;

import javafx.scene.canvas.GraphicsContext;

public abstract class ArenaItem {
    protected double x;
    protected double y;
    protected double radius;

    public ArenaItem(double x, double y, double radius) {
        this.x = x;
        this.y = y;
        this.radius = radius;
    }

    // Getter and Setter methods for x, y, and radius
    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    // Abstract method to be implemented by subclasses for drawing
    public abstract void showItem(GraphicsContext gc);

    // Abstract method to be implemented by subclasses for moving
    public abstract void move(RobotArena arena);

    // Abstract method to check if the item contains a point (for selection)
    public abstract boolean contains(double x, double y);

    // Abstract methods for angle and speed
    public abstract double getAngle();
    public abstract double getSpeed();
}