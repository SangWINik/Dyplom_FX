package com.muzach.ui.controllers;

import com.muzach.midi.Player;
import com.muzach.midi.SequenceBuilder;
import com.muzach.midi.note.NoteDuration;
import com.muzach.midi.note.NoteLocation;
import com.muzach.music.*;
import com.muzach.ui.windows.MidiEditorWindow;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Modality;
import javafx.stage.Stage;

import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import java.io.IOException;

public class MainForm {

    public Button editorButton;
    public Button demoPlayButton;

    private Composition composition;

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
}
