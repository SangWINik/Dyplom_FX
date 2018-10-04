package com.muzach;

import com.muzach.ui.controllers.MainForm;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main extends Application {
    private static final Logger LOGGER = LogManager.getLogger(Main.class);

    private static Stage window;

    @Override
    public void start(Stage primaryStage) throws Exception{
        LOGGER.trace("Starting application");
        window = primaryStage;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ui/fxforms/mainForm.fxml"));
        Parent root = (Parent)loader.load();
        MainForm controller = (MainForm)loader.getController();
        controller.setStage(primaryStage);
        primaryStage.setTitle("Music Generator");
        primaryStage.setScene(new Scene(root));
        primaryStage.setResizable(false);
        primaryStage.show();
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                LOGGER.trace("Closing application");
                Platform.exit();
                System.exit(0);
            }
        });
    }

    public static Stage getWindow() {
        return window;
    }

    public static void main(String[] args) {
        launch(args);
    }


}
