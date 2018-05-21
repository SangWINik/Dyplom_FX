package com.muzach.ui.windows;

import com.muzach.generation.Preset;
import com.muzach.utils.SerializationHelper;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.List;

public class SavePresetDialog {

    private Stage window;
    private TextField nameTextField;
    private TextArea descriptionTextArea;

    private Preset preset;
    private List<Preset> myPresets;

    public SavePresetDialog(Preset preset, List<Preset> myPresets) {
        window = new Stage();
        this.preset = preset;
        this.myPresets = myPresets;
    }

    public void display() {
        window.setTitle("Save Preset");
        window.setMinWidth(300);
        window.setResizable(false);
        window.initModality(Modality.APPLICATION_MODAL);

        Label nameLabel = new Label("Name");
        nameTextField = new TextField();
        VBox nameVBox = new VBox(3, nameLabel, nameTextField);

        Label descriptionLabel = new Label("Description");
        descriptionTextArea = new TextArea();
        VBox descriptionVBox = new VBox(3, descriptionLabel, descriptionTextArea);

        Button saveButton = new Button("Save");
        saveButton.setPrefWidth(150);
        saveButton.setOnAction(e -> saveButtonHandler());
        Button cancelButton = new Button("Cancel");
        cancelButton.setPrefWidth(150);
        cancelButton.setOnAction(e -> window.close());
        HBox buttonsHBox = new HBox(5, saveButton, cancelButton);
        buttonsHBox.setAlignment(Pos.CENTER_RIGHT);

        VBox layout = new VBox(10, nameVBox, descriptionVBox, buttonsHBox);
        layout.setPadding(new Insets(10, 10, 10, 10));
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();
    }

    private void saveButtonHandler() {
        int depth = 5;
        DropShadow borderGlow= new DropShadow();
        borderGlow.setOffsetY(0f);
        borderGlow.setOffsetX(0f);
        borderGlow.setColor(Color.RED);
        borderGlow.setWidth(depth);
        borderGlow.setHeight(depth);
        boolean validError = false;
        if (nameTextField.getText().isEmpty()) {
            nameTextField.setEffect(borderGlow);
            nameTextField.textProperty().addListener((observable, oldValue, newValue) -> nameTextField.setEffect(null));
            validError = true;
        }
        if (descriptionTextArea.getText().isEmpty()) {
            descriptionTextArea.setEffect(borderGlow);
            descriptionTextArea.textProperty().addListener((observable, oldValue, newValue) -> descriptionTextArea.setEffect(null));
            validError = true;
        }
        if (!validError) {
            preset.setName(nameTextField.getText());
            preset.setDescription(descriptionTextArea.getText());
            myPresets.add(preset);
            SerializationHelper.serializeMyPresets(myPresets);
            window.close();
        }
    }
}
