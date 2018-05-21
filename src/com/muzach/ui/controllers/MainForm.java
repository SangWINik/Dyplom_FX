package com.muzach.ui.controllers;

import com.muzach.generation.Preset;
import com.muzach.midi.Player;
import com.muzach.midi.SequenceBuilder;
import com.muzach.midi.note.NoteDuration;
import com.muzach.midi.note.NoteLocation;
import com.muzach.music.*;
import com.muzach.ui.windows.MidiEditorWindow;
import com.muzach.ui.windows.SavePresetDialog;
import com.muzach.utils.SerializationHelper;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainForm {

    public Button editorButton;
    public Button demoPlayButton;

    @FXML
    private ComboBox timeSignatureComboBox;
    @FXML
    private Button savePresetButton;

    private Composition composition;
    private List<Preset> myPresets;

    public MainForm(){
        Track track = new Track();
        TimeSignature timeSignature = new TimeSignature(6, 4);
        NoteLocation.setMeasureLength(timeSignature.numinator);
        for (int i = 0; i < 4; i++) {
            track.addNote(new Note(NotePitch.C1, NoteDuration.Duration.QUARTER, NoteLocation.getNoteLocation(i + 1, 1, NoteDuration.Duration.EIGHTH), 96));
            track.addNote(new Note(NotePitch.Ds1, NoteDuration.Duration.QUARTER, NoteLocation.getNoteLocation(i + 1, 4, NoteDuration.Duration.EIGHTH), 30));
            track.addNote(new Note(NotePitch.G1, NoteDuration.Duration.QUARTER, NoteLocation.getNoteLocation(i + 1, 5, NoteDuration.Duration.EIGHTH), 60));
            track.addNote(new Note(NotePitch.As1, NoteDuration.Duration.QUARTER, NoteLocation.getNoteLocation(i + 1, 7, NoteDuration.Duration.EIGHTH), 100));
            track.addNote(new Note(NotePitch.G1, NoteDuration.Duration.QUARTER, NoteLocation.getNoteLocation(i + 1, 9, NoteDuration.Duration.EIGHTH), 80));
            track.addNote(new Note(NotePitch.Ds1, NoteDuration.Duration.QUARTER, NoteLocation.getNoteLocation(i + 1, 20, NoteDuration.Duration.SIXTEENTH), 70));
        }
        composition = new Composition();
        composition.setTempoBMP(200);
        composition.setTimeSignature(timeSignature);
        composition.setMelodyTrack(track);
    }

    public void initialize(){
        initAdvancedSettings();
        initMyPresets();
    }

    public void openEditor() {
        MidiEditorWindow midiEditor = new MidiEditorWindow(composition.getMelodyTrack(), composition.getTimeSignature());
        midiEditor.showModal();
    }

    public void playDemo() {
        try {
            SequenceBuilder sequenceBuilder = new SequenceBuilder(composition.getTimeSignature());
            sequenceBuilder.setMelodyTrack(composition.getMelodyTrack());
            Player.playSequence(sequenceBuilder.getSequence(), composition.getTempoBMP());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void savePreset() {
        SavePresetDialog savePresetDialog = new SavePresetDialog(parseAdvancedSettings(), myPresets);
        savePresetDialog.display();
        myPresets = SerializationHelper.deserializeMyPresets();
    }

    private void initMyPresets(){
        myPresets = new ArrayList<>();
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


}
