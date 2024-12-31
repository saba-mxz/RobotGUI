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

public class RobotGUI extends Application {
    private RobotArena arena;
    private Canvas canvas;
    private DrawArena drawer;

    @Override
    public void start(Stage stage) {
        arena = new RobotArena();
        drawer = new DrawArena();

        canvas = new Canvas(800, 600);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        SimpleRobot robot1 = new SimpleRobot(100, 100, 20, 0, 2);
        Robot robot2 = new Robot(50, 50, 2, 2); // Example initialization
        WhiskerRobot whiskerRobot = new WhiskerRobot(300, 100, 20, 90, 2);
        arena.addItem(robot1);
        arena.addItem(robot2);
        arena.addItem(whiskerRobot);
        Obstacle obstacle = new Obstacle(400, 300);
        arena.addItem(obstacle);

        // Create menu bar
        MenuBar menuBar = new MenuBar();
        Menu fileMenu = new Menu("File");
        MenuItem loadConfig = new MenuItem("Load Configuration");
        MenuItem saveConfig = new MenuItem("Save Configuration");
        fileMenu.getItems().addAll(loadConfig, saveConfig);
        Menu simulationMenu = new Menu("Simulation");
        MenuItem startSim = new MenuItem("Start");
        MenuItem pauseSim = new MenuItem("Pause");
        simulationMenu.getItems().addAll(startSim, pauseSim);
        Menu helpMenu = new Menu("Help");
        MenuItem instructions = new MenuItem("Instructions");
        helpMenu.getItems().add(instructions);
        menuBar.getMenus().addAll(fileMenu, simulationMenu, helpMenu);

        // Create toolbar
        ToolBar toolBar = new ToolBar();
        Button startButton = new Button("Start");
        Button pauseButton = new Button("Pause");
        Button addRobotButton = new Button("Add Robot");
        toolBar.getItems().addAll(startButton, pauseButton, addRobotButton);

        // Create information panel
        VBox infoPanel = new VBox();
        Label infoLabel = new Label("Arena Information:");
        infoPanel.getChildren().add(infoLabel);

        BorderPane root = new BorderPane();
        root.setTop(menuBar);
        root.setCenter(canvas);
        root.setBottom(toolBar);
        root.setRight(infoPanel);

        Scene scene = new Scene(root, 800, 600);
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
                    }
                }

                // Clear canvas and draw arena items
                gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
                drawer.drawArena(arena, gc);
            }
        };

        startButton.setOnAction(e -> timer.start());
        pauseButton.setOnAction(e -> timer.stop());
        addRobotButton.setOnAction(e -> {
            SimpleRobot newRobot = new SimpleRobot(200, 200, 20, 0, 2);
            arena.addItem(newRobot);
        });

        timer.start();
    }

    public static void main(String[] args) {
        launch(args);
    }
}