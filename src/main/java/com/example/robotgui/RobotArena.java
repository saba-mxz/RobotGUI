package com.example.robotgui;

import java.util.ArrayList;
import java.io.*;
import java.util.List;

public class RobotArena implements Serializable {
    private ArrayList<ArenaItem> items;

    public RobotArena() {
        items = new ArrayList<>();
    }

    public void addItem(ArenaItem item) {
        items.add(item);
    }

    public void removeItem(ArenaItem item) {
        items.remove(item);
    }

    public ArrayList<ArenaItem> getItems() {
        return items;
    }

    public boolean canMove(double newX, double newY, double radius, ArenaItem movingItem) {
        double whiskerLength = radius * 2; // Assuming whisker length is twice the radius

        // Check boundaries considering whisker length
        if (newX - whiskerLength < 0 || newX + whiskerLength > 800 || newY - whiskerLength < 0 || newY + whiskerLength > 600) {
            return false;
        }

        // Check collisions with other items
        for (ArenaItem item : items) {
            if (item != movingItem) { // Skip self-collision
                double distance = Math.sqrt(Math.pow(newX - item.x, 2) + Math.pow(newY - item.y, 2));
                if (distance < radius + item.radius) {
                    return false;
                }
            }
        }

        return true;
    }

    // Save the state of the arena to a file
    public void saveToFile(String filename) throws IOException {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filename))) {
            out.writeObject(this);
        }
    }

    // Load the state of the arena from a file
    public static RobotArena loadFromFile(String filename) throws IOException, ClassNotFoundException {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(filename))) {
            return (RobotArena) in.readObject();
        }
    }
}