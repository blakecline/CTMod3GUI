package com.example.guitestjavafx;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

public class MenuAppFX extends Application {
    private TextArea textArea;
    private BorderPane root;
    private Scene scene;

    private Double initialHue = null;

    @Override
    public void start(Stage stage) {
        root = new BorderPane();
        root.setPadding(new Insets(8));

        textArea = new TextArea();
        textArea.setWrapText(true);
        textArea.setStyle(
                "-fx-control-inner-background: transparent; " +
                        "-fx-background-color: transparent;"
        );
        root.setCenter(textArea);

        MenuBar menuBar = new MenuBar();
        Menu menu = new Menu("Options");

        MenuItem item1 = new MenuItem("Show Date/Time");
        MenuItem item2 = new MenuItem("Save to log.txt");
        MenuItem item3 = new MenuItem("Random Green Background");
        MenuItem item4 = new MenuItem("Exit");

        item1.setOnAction(e -> {
            String dateTime = LocalDateTime.now()
                    .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            textArea.appendText(dateTime + System.lineSeparator());
        });

        item2.setOnAction(e -> {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter("log.txt", true))) {
                writer.write(textArea.getText());
                writer.write(System.lineSeparator());
                Alert ok = new Alert(Alert.AlertType.INFORMATION, "Saved to log.txt!");
                ok.showAndWait();
            } catch (IOException ex) {
                Alert err = new Alert(Alert.AlertType.ERROR, "Error writing to file: " + ex.getMessage());
                err.showAndWait();
            }
        });

        item3.setOnAction(e -> {
            Random rand = new Random();
            if (initialHue == null) {
                // hue range for green: ~80° - 160°
                initialHue = 80.0 + rand.nextDouble() * 80.0;
            }
            double saturation = 0.6 + rand.nextDouble() * 0.4; // 0.6 - 1.0
            double brightness = 0.55 + rand.nextDouble() * 0.45; // 0.55 - 1.0

            Color randomGreen = Color.hsb(initialHue, saturation, brightness);

            root.setBackground(new Background(new BackgroundFill(randomGreen, CornerRadii.EMPTY, Insets.EMPTY)));
            scene.setFill(randomGreen);

            item3.setText(String.format("Random Green (Hue: %.0f°)", initialHue));
        });

        item4.setOnAction(e -> stage.close());

        menu.getItems().addAll(item1, item2, item3, item4);
        menuBar.getMenus().add(menu);
        root.setTop(menuBar);

        scene = new Scene(root, 700, 480, Color.WHITE);
        stage.setTitle("JavaFX Menu Application (Fixed Green Background)");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
