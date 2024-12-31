package com.example.robotgui;

import javafx.scene.canvas.GraphicsContext;

public class DrawArena {

    // This method is responsible for drawing the entire arena
    public void drawArena(RobotArena arena, GraphicsContext gc) {
        // Loop through all items in the arena and draw them
        for (ArenaItem item : arena.getItems()) {
            item.showItem(gc);  // Draw each item using its showItem method
        }
    }
}