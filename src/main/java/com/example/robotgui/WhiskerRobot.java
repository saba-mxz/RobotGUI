package com.example.robotgui;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class WhiskerRobot extends SimpleRobot {

    public WhiskerRobot(double x, double y, double radius, double angle, double speed) {
        super(x, y, radius, angle, speed);
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