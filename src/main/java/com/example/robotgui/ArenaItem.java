package com.example.robotgui;

import javafx.scene.canvas.GraphicsContext;

public abstract class ArenaItem {
    protected double x, y;  // Position of the item
    protected double radius;  // Size of the item

    public ArenaItem(double x, double y, double radius) {
        this.x = x;
        this.y = y;
        this.radius = radius;
    }

    public abstract void showItem(GraphicsContext gc);  // Abstract method to draw the item

    // Calculate the new X position based on speed and angle
    public double calcX(double speed, double angle) {
        return x + speed * Math.cos(Math.toRadians(angle));
    }

    // Calculate the new Y position based on speed and angle
    public double calcY(double speed, double angle) {
        return y + speed * Math.sin(Math.toRadians(angle));
    }
}
