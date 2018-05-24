package com.muzach.ui.controllers;

import com.muzach.generation.Preset;
import com.muzach.midi.Player;
import com.muzach.midi.SequenceBuilder;
import com.muzach.midi.note.NoteDuration;
import com.muzach.midi.note.NoteLocation;
import com.muzach.music.*;
import com.muzach.ui.controls.PresetPane;
import com.muzach.ui.controls.TracksPlayerPane;
import com.muzach.ui.windows.MidiEditorWindow;
import com.muzach.ui.windows.SavePresetDialog;
import com.muzach.utils.SerializationHelper;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainForm {

    public Button editorButton;
    public Button demoPlayButton;

    private Stage stage;
    @FXML
    private ComboBox timeSignatureComboBox;
    @FXML
    private Button savePresetButton;
    @FXML
    private VBox myPresetsVBox;
    @FXML
    private HBox playerHBox;

    private Composition composition;
    private List<Preset> myPresets;
    private List<PresetPane> presetPanes;
    private Sequencer sequencer;

    public MainForm(){
        TimeSignature timeSignature = new TimeSignature(6, 4);
        NoteLocation.setMeasureLength(timeSignature.numinator); //important

        Track melodyTrack = new Track();
        for (int i = 0; i < 4; i++) {
            melodyTrack.addNote(new Note(NotePitch.C1, NoteDuration.Duration.QUARTER, NoteLocation.getNoteLocation(i + 1, 1, NoteDuration.Duration.EIGHTH), 96));
            melodyTrack.addNote(new Note(NotePitch.Ds1, NoteDuration.Duration.QUARTER, NoteLocation.getNoteLocation(i + 1, 4, NoteDuration.Duration.EIGHTH), 30));
            melodyTrack.addNote(new Note(NotePitch.G1, NoteDuration.Duration.QUARTER, NoteLocation.getNoteLocation(i + 1, 5, NoteDuration.Duration.EIGHTH), 60));
            melodyTrack.addNote(new Note(NotePitch.As1, NoteDuration.Duration.QUARTER, NoteLocation.getNoteLocation(i + 1, 7, NoteDuration.Duration.EIGHTH), 100));
            melodyTrack.addNote(new Note(NotePitch.G1, NoteDuration.Duration.QUARTER, NoteLocation.getNoteLocation(i + 1, 9, NoteDuration.Duration.EIGHTH), 80));
            melodyTrack.addNote(new Note(NotePitch.Ds1, NoteDuration.Duration.QUARTER, NoteLocation.getNoteLocation(i + 1, 20, NoteDuration.Duration.SIXTEENTH), 70));
        }

        Track harmonyTrack = new Track();
        for (int i = 0; i < 4; i++) {
            harmonyTrack.addNote(new Note(NotePitch.C1, NoteDuration.Duration.HALF, NoteLocation.getNoteLocation(i + 1, 1, NoteDuration.Duration.HALF), 50));
            harmonyTrack.addNote(new Note(NotePitch.Ds1, NoteDuration.Duration.HALF, NoteLocation.getNoteLocation(i + 1, 1, NoteDuration.Duration.HALF), 50));
            harmonyTrack.addNote(new Note(NotePitch.G1, NoteDuration.Duration.HALF, NoteLocation.getNoteLocation(i + 1, 1, NoteDuration.Duration.HALF), 50));
            harmonyTrack.addNote(new Note(NotePitch.As1, NoteDuration.Duration.HALF, NoteLocation.getNoteLocation(i + 1, 1, NoteDuration.Duration.HALF), 50));

            harmonyTrack.addNote(new Note(NotePitch.C1, NoteDuration.Duration.HALF, NoteLocation.getNoteLocation(i + 1, 2, NoteDuration.Duration.HALF), 50));
            harmonyTrack.addNote(new Note(NotePitch.Ds1, NoteDuration.Duration.HALF, NoteLocation.getNoteLocation(i + 1, 2, NoteDuration.Duration.HALF), 50));
            harmonyTrack.addNote(new Note(NotePitch.G1, NoteDuration.Duration.HALF, NoteLocation.getNoteLocation(i + 1, 2, NoteDuration.Duration.HALF), 50));
            harmonyTrack.addNote(new Note(NotePitch.As1, NoteDuration.Duration.HALF, NoteLocation.getNoteLocation(i + 1, 2, NoteDuration.Duration.HALF), 50));

            harmonyTrack.addNote(new Note(NotePitch.C1, NoteDuration.Duration.HALF, NoteLocation.getNoteLocation(i + 1, 3, NoteDuration.Duration.HALF), 50));
            harmonyTrack.addNote(new Note(NotePitch.Ds1, NoteDuration.Duration.HALF, NoteLocation.getNoteLocation(i + 1, 3, NoteDuration.Duration.HALF), 50));
            harmonyTrack.addNote(new Note(NotePitch.G1, NoteDuration.Duration.HALF, NoteLocation.getNoteLocation(i + 1, 3, NoteDuration.Duration.HALF), 50));
            harmonyTrack.addNote(new Note(NotePitch.As1, NoteDuration.Duration.HALF, NoteLocation.getNoteLocation(i + 1, 3, NoteDuration.Duration.HALF), 50));
        }
        composition = new Composition();
        composition.setTempoBMP(200);
        composition.setTimeSignature(timeSignature);
        composition.setMelodyTrack(melodyTrack);
        composition.setHarmonyTrack(harmonyTrack);
    }

    public void initialize(){
        initAdvancedSettings();
        initMyPresets();

        TracksPlayerPane tracksPlayerPane = new TracksPlayerPane(composition, sequencer);
        playerHBox.getChildren().addAll(tracksPlayerPane);
    }

    public void openEditor() {
        MidiEditorWindow midiEditor = new MidiEditorWindow(composition.getMelodyTrack(), composition.getTimeSignature());
        midiEditor.showModal();
    }

    public void playDemo() {
        try {
            sequencer = Player.playComposition(composition);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void savePreset() {
        SavePresetDialog savePresetDialog = new SavePresetDialog(parseAdvancedSettings(), myPresets);
        savePresetDialog.display();
        initMyPresets();
    }

    private void deletePreset(Preset preset){
        myPresets.remove(preset);
        SerializationHelper.serializeMyPresets(myPresets);
        initMyPresets();
    }

    private void initMyPresets(){
        myPresetsVBox.getChildren().clear();
        myPresets = SerializationHelper.deserializeMyPresets();
        presetPanes = new ArrayList<>();
        for (Preset preset: myPresets){
            PresetPane presetPane = new PresetPane(preset, false);
            presetPanes.add(presetPane);
            myPresetsVBox.getChildren().add(presetPane);
        }
        for (PresetPane presetPane: presetPanes) {
            presetPane.setOnMousePressed(event -> {
                presetPane.select();
                setAdvancedSettingsWithPreset(presetPane.getPreset());
                for (PresetPane pp: presetPanes) {
                    if (pp != presetPane) {
                        pp.deselect();
                    }
                }
            });
            presetPane.setOnDeleteAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    deletePreset(presetPane.getPreset());
                }
            });
        }
    }

    private void initAdvancedSettings(){
        TimeSignature ts1 = new TimeSignature(2, 4);
        TimeSignature ts2 = new TimeSignature(3, 4);
        TimeSignature ts3 = new TimeSignature(4, 4);
        TimeSignature ts4 = new TimeSignature(5, 4);
        TimeSignature ts5 = new TimeSignature(7, 4);
        timeSignatureComboBox.getItems().addAll(ts1, ts2, ts3, ts4, ts5);
        timeSignatureComboBox.setValue(ts3);
    }

    private Preset parseAdvancedSettings(){
        Preset preset = new Preset();
        preset.setTimeSignature((TimeSignature) timeSignatureComboBox.getValue());
        //set other settings
        return preset;
    }

    private void setAdvancedSettingsWithPreset(Preset preset){
        TimeSignature newValue = (TimeSignature) timeSignatureComboBox.getItems().stream().filter(t -> ((TimeSignature)(t)).toString().equals(preset.getTimeSignature().toString())).findFirst().get();
        timeSignatureComboBox.setValue(newValue);
        //todo other fields
    }

    public void saveMidi(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save MIDI file");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("MIDI file(*.mid)", "*.mid"));
        fileChooser.setInitialFileName("moose");
        File file = fileChooser.showSaveDialog(stage);
        if (file != null) {
            try {
                SequenceBuilder sequenceBuilder = new SequenceBuilder(composition.getTimeSignature());
                sequenceBuilder.setMelodyTrack(composition.getMelodyTrack());
                sequenceBuilder.setHarmonyTrack(composition.getHarmonyTrack());
                MidiSystem.write(sequenceBuilder.getSequence(), 1, file);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
