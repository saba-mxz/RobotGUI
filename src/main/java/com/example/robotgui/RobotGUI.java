package com.example.robotgui;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import java.io.File;
import java.io.IOException;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

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
        // Create lights
        Light light1 = new Light(400, 250); // Stationary light
        Light light2 = new Light(650, 400); // Stationary light
        arena.addItem(light1);
        arena.addItem(light2);
        arena.addItem(robot1);
        arena.addItem(robot2);
        arena.addItem(whiskerRobot);
        arena.addItem(beamSensorRobot);
        Obstacle obstacle = new Obstacle(100, 400);
        Obstacle obstacle2 = new Obstacle(700, 150);
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

        //Instruction button into menu bar
        Menu helpMenu = new Menu("Help");
        MenuItem instructions = new MenuItem("Instructions");
        helpMenu.getItems().add(instructions);


        // About menu
        Menu aboutMenu = new Menu("About");
        MenuItem aboutItem = new MenuItem("About");
        aboutMenu.getItems().add(aboutItem);

        menuBar.getMenus().addAll(fileMenu, helpMenu, aboutMenu);

        // Create toolbar
        ToolBar toolBar = new ToolBar();
        Button addRobotButton = new Button("Add Robot");
        Button addDangerRobotButton = new Button("Add Danger Robot");
        Button addWhiskerRobotButton = new Button("Add Whisker Robot");
        Button addBeamSensorRobotButton = new Button("Add Beam Sensor Robot");
        Button selectItemButton = new Button("Select Item");
        Button deleteItemButton = new Button("Delete Item");
        toolBar.getItems().addAll(addRobotButton, addDangerRobotButton, addWhiskerRobotButton, addBeamSensorRobotButton, selectItemButton, deleteItemButton);

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
                    item.move(arena);
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
        addBeamSensorRobotButton.setOnAction(e -> {
            BeamSensorRobot beamSensorRobot2 = new BeamSensorRobot(550, 300, 2);
            arena.addItem(beamSensorRobot2);
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

        // Save configuration action
        saveConfig.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save Configuration");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Arena Files", "*.arena"));
            File file = fileChooser.showSaveDialog(stage);
            if (file != null) {
                try {
                    arena.saveToFile(file.getAbsolutePath());
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });

        // Load configuration action
        loadConfig.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Load Configuration");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Arena Files", "*.arena"));
            File file = fileChooser.showOpenDialog(stage);
            if (file != null) {
                try {
                    arena = RobotArena.loadFromFile(file.getAbsolutePath());
                    updateInfoPanel(); // Update the info panel after loading
                } catch (IOException | ClassNotFoundException ex) {
                    ex.printStackTrace();
                }
            }
        });

        // Instructions action
        instructions.setOnAction(e -> {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Instructions");
            alert.setHeaderText("How to Use the Robot Simulation GUI");
            alert.setContentText("1. Use the toolbar to add different types of robots to the arena.\n"
                    + "2. Select an item by clicking 'Select Item' and then clicking on the item in the arena.\n"
                    + "3. Delete a selected item using the 'Delete Item' button.\n"
                    + "4. Start and pause the simulation using the 'Start' and 'Pause' buttons under the 'File' menu option.\n"
                    + "5. Save and load arena configurations using the 'File' menu options.\n"
                    + "6. View information about selected items in the info panel on the right, under 'Arena Information'.");
            alert.showAndWait();
        });

        aboutMenu.setOnAction(e -> {
            Alert alert2 = new Alert(AlertType.INFORMATION);
            alert2.setTitle("About");
            alert2.setHeaderText("Student Number: 32009516");
            alert2.setContentText("This RobotGUI features various robots moving in an arena. SimpleRobots move left and right or diagonally, " +
                    " with ‘bump’ sensors that change direction upon contact with the canvas edge, another robot, or obstacle. The WhiskerRobot has a" +
                    " larger sensor that changes direction before colliding with another robot. The BeamSensorRobot moves randomly and changes direction" +
                    " when its beam senses another robot or obstacle. The DangerRobot moves twice as fast and reduces the health of any robot or fire it" +
                    " encounters by 5. You can add or delete robots using the toolbar buttons. Selecting a robot reveals its health, direction, or coordinates." +
                    " Pausing helps select a robot or obstacle for information. Have fun!");
            alert2.showAndWait();
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
            int health = 0;
            if (selectedItem instanceof SimpleRobot) {
                health = ((SimpleRobot) selectedItem).getHealth();
            } else if (selectedItem instanceof WhiskerRobot) {
                health = ((WhiskerRobot) selectedItem).getHealth();
            } else if (selectedItem instanceof BeamSensorRobot) {
                health = ((BeamSensorRobot) selectedItem).getHealth();
            } else if (selectedItem instanceof Robot) {
                health = ((Robot) selectedItem).getHealth();
            }

            String info = String.format("Selected Item:\nType: %s\nX: %.2f\nY: %.2f\nAngle: %.2f\nSpeed: %.2f\nHealth: %d",
                    selectedItem.getClass().getSimpleName(),
                    selectedItem.getX(),
                    selectedItem.getY(),
                    selectedItem.getAngle(),
                    selectedItem.getSpeed(),
                    health);
            infoLabel.setText(info);
        } else {
            infoLabel.setText("Arena Information:");
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}