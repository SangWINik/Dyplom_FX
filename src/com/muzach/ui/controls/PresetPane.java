package com.muzach.ui.controls;

import com.muzach.generation.Preset;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class PresetPane extends GridPane {
    private Preset preset;

    private Button deleteButton;

    private boolean selected = false;
    private boolean isIntegrated;

    public PresetPane(Preset preset, boolean isIntegrated) {
        this.preset = preset;
        this.isIntegrated = isIntegrated;
        this.setPadding(new Insets(5, 10, 5, 10));
        this.setHgap(20);

        this.draw();
        this.style();
    }

    public void select() {
        this.selected = true;
        this.style();
    }

    public void deselect() {
        this.selected = false;
        this.style();
    }

    private void draw(){
        Label nameLabel = new Label(preset.getName());
        nameLabel.setFont(Font.font("Segoe UI", 14));
        nameLabel.setWrapText(true);
        Label descriptionLabel = new Label(preset.getDescription());
        descriptionLabel.setWrapText(true);
        descriptionLabel.setMaxHeight(100);
        ImageView imageView = new ImageView("com/muzach/ui/resources/delete.png");
        imageView.setFitWidth(17);
        imageView.setFitHeight(17);
        deleteButton = new Button("", imageView);

        this.add(nameLabel, 0, 0);
        this.add(descriptionLabel, 1, 0);
        if (!isIntegrated) {
            this.add(deleteButton, 2, 0);
        }
        this.getColumnConstraints().addAll(new ColumnConstraints(200), new ColumnConstraints(400), new ColumnConstraints(50));
    }

    private void style(){
        BorderStroke borderStroke;
        if (selected){
            borderStroke = new BorderStroke(Color.DARKBLUE, BorderStrokeStyle.SOLID, new CornerRadii(3), BorderWidths.DEFAULT);
        } else {
            borderStroke = new BorderStroke(Color.LIGHTGRAY, BorderStrokeStyle.SOLID, new CornerRadii(3), BorderWidths.DEFAULT);
        }
        this.setBorder(new Border(borderStroke));
    }

    public Preset getPreset() {
        return preset;
    }

    public void setOnDeleteAction(EventHandler<ActionEvent> eventHandler){
        deleteButton.setOnAction(eventHandler);
    }
}
