package com.example.robotgui;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class WhiskerRobot extends SimpleRobot {

    public WhiskerRobot(double x, double y, double radius, double angle, double speed) {
        super(x, y, radius, angle, speed);
    }

    @Override
    public void showItem(GraphicsContext gc) {
        super.showItem(gc);  // Draw the body and wheels from SimpleRobot

        // Draw whiskers (lines at ±22.5 degrees from the robot's facing angle)
        double whiskerLength = radius * 2;
        double whiskerOffset = 22.5; // Whiskers at ±22.5 degrees

        double whiskerX1 = x + whiskerLength * Math.cos(Math.toRadians(angle + whiskerOffset));
        double whiskerY1 = y + whiskerLength * Math.sin(Math.toRadians(angle + whiskerOffset));

        double whiskerX2 = x + whiskerLength * Math.cos(Math.toRadians(angle - whiskerOffset));
        double whiskerY2 = y + whiskerLength * Math.sin(Math.toRadians(angle - whiskerOffset));

        // Draw whiskers as thin lines
        gc.setStroke(Color.RED);
        gc.setLineWidth(2);
        gc.strokeLine(x, y, whiskerX1, whiskerY1);
        gc.strokeLine(x, y, whiskerX2, whiskerY2);
    }

    @Override
    public void move(RobotArena arena) {
        double newX = calcX(getSpeed(), angle); // Use getter method if speed is private
        double newY = calcY(getSpeed(), angle);

        // Calculate whisker positions
        double whiskerLength = radius * 2;
        double whiskerX1 = newX + whiskerLength * Math.cos(Math.toRadians(angle + 22.5));
        double whiskerY1 = newY + whiskerLength * Math.sin(Math.toRadians(angle + 22.5));
        double whiskerX2 = newX + whiskerLength * Math.cos(Math.toRadians(angle - 22.5));
        double whiskerY2 = newY + whiskerLength * Math.sin(Math.toRadians(angle - 22.5));

        // Check if the whiskers are within the arena boundaries
        if (arena.canMove(whiskerX1, whiskerY1, radius, this) && arena.canMove(whiskerX2, whiskerY2, radius, this)) {
            x = newX;
            y = newY;
        } else {
            // Reverse direction on collision
            angle = (angle + 180) % 360;
        }
    }
}