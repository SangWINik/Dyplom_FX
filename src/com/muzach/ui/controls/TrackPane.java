package com.muzach.ui.controls;

import com.muzach.midi.note.NoteDuration;
import com.muzach.music.Note;
import com.muzach.music.NotePitch;
import com.muzach.music.TimeSignature;
import com.muzach.music.Track;
import com.muzach.ui.windows.MidiEditorWindow;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;

import java.util.HashMap;
import java.util.Map;

public class TrackPane extends Pane {
    private static final int verticalPadding = 15;

    private int measureCount;
    private Track track;
    private TimeSignature timeSignature;

    private boolean muted = false;
    private String title;

    public TrackPane(Track track, TimeSignature timeSignature, int measureCount, String title, double prefWidth, double prefHeight) {
        this.measureCount = measureCount;
        this.track = track;
        this.timeSignature = timeSignature;
        this.title = title;
        this.setPrefSize(prefWidth, prefHeight);
        this.reDraw();
        this.initEvents();
    }

    public void reDraw() {
        this.getChildren().clear();

        //setting background
        Background background = null;
        if (this.muted) {
            background = new Background(new BackgroundFill(Color.GRAY, new CornerRadii(2), null));
        } else {
            background = new Background(new BackgroundFill(Color.LIGHTGRAY, new CornerRadii(2), null));
        }
        this.setBackground(background);

        //drawing track title
        Text text = new Text(title);
        text.setX(5);
        text.setY(13);

        //drawing note marks
        int x0 = 0;
        int y0 = verticalPadding;
        int xmax = (int) this.getPrefWidth();
        int ymax = (int) (this.getPrefHeight() - verticalPadding);
        //calculating vertical positions
        NotePitch lowestNote = getLowestNote();
        NotePitch highestNote = getHighestNote();
        int notesVariety = highestNote.ordinal() - lowestNote.ordinal() + 1;
        Map<NotePitch, Double> pitchPositionMap = new HashMap<>();
        for (int i = lowestNote.ordinal(); i <= highestNote.ordinal(); i++) {
            NotePitch pitch = NotePitch.values()[i];
            int noteRelativePosition = pitch.ordinal() - lowestNote.ordinal();
            double spaceForOneNote = (ymax - y0) / (double)notesVariety;
            double y = ymax - noteRelativePosition*spaceForOneNote;
            pitchPositionMap.put(pitch, y);
        }
        //drawing and calculating horizontal positions
        int tsMeasureLength = timeSignature.numinator* NoteDuration.getTsCount(NoteDuration.Duration.QUARTER);
        int tsCountInTrack = tsMeasureLength*measureCount;
        double tsWidth = (double)xmax/tsCountInTrack;
        for (Note note: track.getNotes()) {
            int startX = (int)(x0 + note.getLocation().getTSPosition()*tsWidth);
            int width = (int)(NoteDuration.getTsCount(note.getDuration())*tsWidth);
            double y = pitchPositionMap.get(note.getPitch());
            Line noteLine = new Line(startX, y, startX + width, y);
            if (note.getLocation().getMeasureNumber() < measureCount) {
                this.getChildren().add(noteLine);
            }
        }
        this.getChildren().addAll(text);
    }

    public void mute() {
        this.muted = true;
        this.track.mute();
        this.reDraw();
    }

    public void unmute() {
        this.muted = false;
        this.track.unmute();
        this.reDraw();
    }

    private void initEvents() {
        this.setOnMouseClicked(event -> {
            if (event.getButton().equals(MouseButton.PRIMARY)) {
                if (event.getClickCount() == 2) {
                    MidiEditorWindow midiEditor = new MidiEditorWindow(track, timeSignature, measureCount);
                    midiEditor.showModal();
                    reDraw();
                }
            }
        });
    }

    //probably can and should be implemented with lambda
    private NotePitch getLowestNote() {
        if (track.getNotes().isEmpty()) {
            return NotePitch.C0;
        }
        NotePitch min = track.getNotes().get(0).getPitch();
        for (Note note : track.getNotes()) {
            if (note.getPitch().ordinal() < min.ordinal()) {
                min = note.getPitch();
            }
        }
        return min;
    }

    //probably can and should be implemented with lambda
    private NotePitch getHighestNote() {
        if (track.getNotes().isEmpty()) {
            return NotePitch.B2;
        }
        NotePitch max = track.getNotes().get(0).getPitch();
        for (Note note : track.getNotes()) {
            if (note.getPitch().ordinal() > max.ordinal()) {
                max = note.getPitch();
            }
        }
        return max;
    }
}
