package com.muzach.ui.controllers;

import com.muzach.music.NoteDuration;
import com.muzach.music.TimeSignature;
import com.muzach.generation.Track;
import com.muzach.ui.controls.KeyboardPane;
import com.muzach.ui.controls.PianorollPane;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class MidiEditor {
    private static final int octaveCount = 3;
    private static final int laneHeight = 12;

    private int measureCount;
    private int toneOffset;
    private Track track;
    private TimeSignature timeSignature;

    @FXML
    private ScrollPane pianorollScrollPane;
    @FXML
    private HBox pianorollHbox;
    @FXML
    private ComboBox gridComboBox;
    @FXML
    private Button okButton;
    @FXML
    private Button editModeButton;
    @FXML
    private Button addModeButton;
    @FXML
    private Button deleteModeButton;
    @FXML
    private ComboBox valueComboBox;
    @FXML
    private Slider velocitySlider;
    @FXML
    private HBox toolsHBox;

    public MidiEditor(Track track, TimeSignature timeSignature, int measureCount, int toneOffset){
        this.track = track;
        this.timeSignature = timeSignature;
        this.measureCount = measureCount;
        this.toneOffset = toneOffset;
    }

    public void initialize() {
        KeyboardPane keyboardPane = new KeyboardPane(octaveCount, laneHeight);
        PianorollPane pianorollPane = new PianorollPane(track, timeSignature, measureCount, toneOffset, octaveCount, laneHeight, toolsHBox, valueComboBox, velocitySlider);
        pianorollHbox.getChildren().add(0, keyboardPane);
        pianorollScrollPane.setContent(pianorollPane);
        pianorollScrollPane.setPrefHeight(octaveCount*12*laneHeight + 17); //17 - scrollbar size

        gridComboBox.getItems().addAll(NoteDuration.Duration.THIRTYSECOND, NoteDuration.Duration.SIXTEENTH, NoteDuration.Duration.EIGHTH, NoteDuration.Duration.QUARTER, NoteDuration.Duration.HALF);
        gridComboBox.setValue(pianorollPane.getResolution());
        valueComboBox.getItems().addAll(NoteDuration.Duration.THIRTYSECOND, NoteDuration.Duration.SIXTEENTH, NoteDuration.Duration.EIGHTH, NoteDuration.Duration.QUARTER, NoteDuration.Duration.HALF);
        valueComboBox.setValue(pianorollPane.getResolution());

        gridComboBox.valueProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                pianorollPane.setResolution((NoteDuration.Duration) newValue);
                pianorollPane.reDraw();
            }
        });

        okButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Stage stage = (Stage) okButton.getScene().getWindow();
                stage.close();
            }
        });

        editModeButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                pianorollPane.exitAddMode();
            }
        });

        addModeButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                pianorollPane.enterAddMode();
            }
        });

        deleteModeButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                pianorollPane.deleteSelectedNote();
                pianorollPane.reDraw();
            }
        });

        valueComboBox.valueProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                pianorollPane.changeSelectedNoteValue((NoteDuration.Duration) newValue);
                pianorollPane.reDraw();
            }
        });

        velocitySlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                pianorollPane.changeSelectedNoteVelocity(newValue.intValue());
                pianorollPane.reDraw();
            }
        });

    }

    public Track getTrack() {
        return track;
    }

    public void setTrack(Track track) {
        this.track = track;
    }

    public TimeSignature getTimeSignature() {
        return timeSignature;
    }

    public void setTimeSignature(TimeSignature timeSignature) {
        this.timeSignature = timeSignature;
    }
}