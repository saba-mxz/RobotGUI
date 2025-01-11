package com.example.robotgui;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.RadialGradient;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.Stop;
import java.io.Serializable;

public class Light extends ArenaItem implements Serializable {
    public Light() {
        super();
        this.radius = 10; // Set a smaller radius
    }

    public Light(double x, double y) {
        super(x, y, 10); // Set a smaller radius
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

        // Draw the light
        gc.setFill(Color.YELLOW);
        double diameter = radius * 2;
        gc.fillOval(x - radius, y - radius, diameter, diameter);
    }

    @Override
    public void move(RobotArena arena) {
        // Lights don't move, so this method can be empty
    }

    @Override
    public boolean contains(double x, double y) {
        double distance = Math.sqrt(Math.pow(x - this.x, 2) + Math.pow(y - this.y, 2));
        return distance <= radius;
    }

    @Override
    public double getAngle() {
        return 0; // Lights don't have an angle
    }

    @Override
    public double getSpeed() {
        return 0; // Lights don't move, so their speed is 0
    }
}