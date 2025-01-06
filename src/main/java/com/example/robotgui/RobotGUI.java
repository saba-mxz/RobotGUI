package com.example.robotgui;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.input.MouseEvent;

public class RobotGUI extends Application {
    private RobotArena arena;
    private Canvas canvas;
    private DrawArena drawer;
    private ArenaItem selectedItem;
    private Label infoLabel;

    @Override
    public void start(Stage stage) {
        arena = new RobotArena();
        drawer = new DrawArena();

        canvas = new Canvas(800, 600);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        // Create robots and obstacle
        SimpleRobot robot1 = new SimpleRobot(100, 100, 20, 0, 2);
        Robot robot2 = new Robot(50, 50, 2, 2);
        WhiskerRobot whiskerRobot = new WhiskerRobot(300, 100, 20, 90, 2);
        BeamSensorRobot beamSensorRobot = new BeamSensorRobot(200, 200, 2);
        arena.addItem(robot1);
        arena.addItem(robot2);
        arena.addItem(whiskerRobot);
        arena.addItem(beamSensorRobot);
        Obstacle obstacle = new Obstacle(400, 300);
        Obstacle obstacle2 = new Obstacle(700, 170);
        arena.addItem(obstacle2);
        arena.addItem(obstacle);

        // Create menu bar
        MenuBar menuBar = new MenuBar();
        Menu fileMenu = new Menu("File");
        MenuItem loadConfig = new MenuItem("Load Configuration");
        MenuItem saveConfig = new MenuItem("Save Configuration");
        fileMenu.getItems().addAll(loadConfig, saveConfig);

        // Move start and pause buttons directly into the menu bar
        MenuItem startButton = new MenuItem("Start");
        MenuItem pauseButton = new MenuItem("Pause");
        fileMenu.getItems().addAll(startButton, pauseButton);

        Menu helpMenu = new Menu("Help");
        MenuItem instructions = new MenuItem("Instructions");
        helpMenu.getItems().add(instructions);

        menuBar.getMenus().addAll(fileMenu, helpMenu);

        // Create toolbar
        ToolBar toolBar = new ToolBar();
        Button addRobotButton = new Button("Add Robot");
        Button addDangerRobotButton = new Button("Add Danger Robot");
        Button addWhiskerRobotButton = new Button("Add Whisker Robot");
        Button selectItemButton = new Button("Select Item");
        Button deleteItemButton = new Button("Delete Item");
        toolBar.getItems().addAll(addRobotButton, addDangerRobotButton, addWhiskerRobotButton, selectItemButton, deleteItemButton);

        // Create information panel
        VBox infoPanel = new VBox();
        infoPanel.setStyle("-fx-background-color: lightgray;"); // Set background color for visibility
        infoLabel = new Label("Arena Information:");
        infoPanel.getChildren().add(infoLabel);

        BorderPane root = new BorderPane();
        VBox topContainer = new VBox(menuBar, toolBar); // Combine menu bar and toolbar
        root.setTop(topContainer); // Set the combined container to the top
        root.setCenter(canvas);
        root.setRight(infoPanel); // Set the info panel to the right

        Scene scene = new Scene(root, 1000, 600); // Increase scene width to ensure space for info panel
        stage.setTitle("Robot Simulation");
        stage.setScene(scene);
        stage.show();

        // Animation logic
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                // Move the robots, passing the arena to check for collisions
                for (ArenaItem item : arena.getItems()) {
                    if (item instanceof SimpleRobot) {
                        ((SimpleRobot) item).move(arena);
                    } else if (item instanceof Robot) {
                        ((Robot) item).move(arena);
                    } else if (item instanceof BeamSensorRobot) {
                        ((BeamSensorRobot) item).move(arena);
                    }
                }

                // Clear canvas and draw arena items
                gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
                drawer.drawArena(arena, gc);

                // Update information panel
                updateInfoPanel();
            }
        };

        // Set button actions
        startButton.setOnAction(e -> timer.start());
        pauseButton.setOnAction(e -> timer.stop());
        addRobotButton.setOnAction(e -> {
            SimpleRobot newRobot = new SimpleRobot(200, 400, 20, 0, 2);
            arena.addItem(newRobot);
        });
        addDangerRobotButton.setOnAction(e -> {
            DangerRobot dangerRobot = new DangerRobot(400, 400, 20, 0, 2);
            arena.addItem(dangerRobot);
        });
        addWhiskerRobotButton.setOnAction(e -> {
            WhiskerRobot whiskerRobot2 = new WhiskerRobot(600, 500, 20, 90, 2);
            arena.addItem(whiskerRobot2);
        });
        selectItemButton.setOnAction(e -> {
            canvas.setOnMouseClicked(this::handleSelectItem);
        });
        deleteItemButton.setOnAction(e -> {
            if (selectedItem != null) {
                arena.removeItem(selectedItem);
                selectedItem = null;
            }
        });

        timer.start();
    }

    private void handleSelectItem(MouseEvent event) {
        double clickX = event.getX();
        double clickY = event.getY();
        for (ArenaItem item : arena.getItems()) {
            if (item.contains(clickX, clickY)) {
                selectedItem = item;
                break;
            }
        }
        updateInfoPanel(); // Update the info panel when an item is selected
    }

    private void updateInfoPanel() {
        if (selectedItem != null) {
            String info = String.format("Selected Item:\nType: %s\nX: %.2f\nY: %.2f\nAngle: %.2f\nSpeed: %.2f",
                    selectedItem.getClass().getSimpleName(),
                    selectedItem.getX(),
                    selectedItem.getY(),
                    selectedItem.getAngle(),
                    selectedItem.getSpeed());
            infoLabel.setText(info);
        } else {
            infoLabel.setText("Arena Information:");
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}