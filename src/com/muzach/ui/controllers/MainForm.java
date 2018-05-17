package com.muzach.ui.controllers;

import com.muzach.midi.SequenceBuilder;
import com.muzach.midi.note.NoteDuration;
import com.muzach.midi.note.NoteLocation;
import com.muzach.music.Note;
import com.muzach.music.NotePitch;
import com.muzach.music.TimeSignature;
import com.muzach.music.Track;
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

    public void openEditor() {
        Track track = new Track();
        NoteLocation.setMeasureLength(6);
        for (int i = 0; i < 4; i++) {
            track.addNote(new Note(NotePitch.C1, NoteDuration.Duration.QUARTER, NoteLocation.getNoteLocation(i + 1, 1, NoteDuration.Duration.EIGHTH), 96));
            track.addNote(new Note(NotePitch.Ds1, NoteDuration.Duration.QUARTER, NoteLocation.getNoteLocation(i + 1, 4, NoteDuration.Duration.EIGHTH), 30));
            track.addNote(new Note(NotePitch.G1, NoteDuration.Duration.QUARTER, NoteLocation.getNoteLocation(i + 1, 5, NoteDuration.Duration.EIGHTH), 60));
            track.addNote(new Note(NotePitch.As1, NoteDuration.Duration.QUARTER, NoteLocation.getNoteLocation(i + 1, 7, NoteDuration.Duration.EIGHTH), 100));
            track.addNote(new Note(NotePitch.G1, NoteDuration.Duration.QUARTER, NoteLocation.getNoteLocation(i + 1, 9, NoteDuration.Duration.EIGHTH), 80));
            track.addNote(new Note(NotePitch.Ds1, NoteDuration.Duration.QUARTER, NoteLocation.getNoteLocation(i + 1, 11, NoteDuration.Duration.EIGHTH), 70));
        }
        MidiEditorWindow midiEditor = new MidiEditorWindow(track, new TimeSignature(6, 4));
        track = midiEditor.showModal();
    }

    public void playDemo() {
        try {
            //default, can be safely removed
            NoteLocation.setMeasureLength(6);

            SequenceBuilder sequenceBuilder = new SequenceBuilder(4);
            sequenceBuilder.setTimeSignature((byte) 4, (byte) 4);
            for (int i = 0; i < 4; i++) {
                sequenceBuilder.setNote(new Note(NotePitch.C1, NoteDuration.Duration.QUARTER, NoteLocation.getNoteLocation(i + 1, 1, NoteDuration.Duration.EIGHTH), 96));
                sequenceBuilder.setNote(new Note(NotePitch.Ds1, NoteDuration.Duration.QUARTER, NoteLocation.getNoteLocation(i + 1, 4, NoteDuration.Duration.EIGHTH), 96));
                sequenceBuilder.setNote(new Note(NotePitch.G1, NoteDuration.Duration.QUARTER, NoteLocation.getNoteLocation(i + 1, 5, NoteDuration.Duration.EIGHTH), 96));
                sequenceBuilder.setNote(new Note(NotePitch.As1, NoteDuration.Duration.QUARTER, NoteLocation.getNoteLocation(i + 1, 7, NoteDuration.Duration.EIGHTH), 96));
                sequenceBuilder.setNote(new Note(NotePitch.G1, NoteDuration.Duration.QUARTER, NoteLocation.getNoteLocation(i + 1, 9, NoteDuration.Duration.EIGHTH), 96));
                sequenceBuilder.setNote(new Note(NotePitch.Ds1, NoteDuration.Duration.QUARTER, NoteLocation.getNoteLocation(i + 1, 11, NoteDuration.Duration.EIGHTH), 96));
            }
            playSequence(sequenceBuilder.getSequence());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void playSequence(Sequence sequence) throws Exception {
        Sequencer sequencer = MidiSystem.getSequencer();
        sequencer.setTempoInBPM(200);
        sequencer.open();
        sequencer.setSequence(sequence);
        sequencer.start();
    }
}
