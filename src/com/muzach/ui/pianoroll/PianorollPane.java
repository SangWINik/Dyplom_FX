package com.muzach.ui.pianoroll;

import com.muzach.midi.note.NoteDuration;
import com.muzach.midi.note.NoteLocation;
import com.muzach.music.Note;
import com.muzach.music.NotePitch;
import com.muzach.music.TimeSignature;
import com.muzach.music.Track;
import javafx.beans.NamedArg;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Text;

import java.util.HashMap;
import java.util.Map;

public class PianorollPane extends Pane {

    private Track track;
    private TimeSignature timeSignature;
    private NoteDuration.Duration resolution = NoteDuration.Duration.EIGHTH;

    private int octaveCount;
    private int noteCount;
    private int laneHeight;
    //by ts I mean one 32d note
    private int tsWidth = 10;

    private Map<Note, Rectangle> noteRectangleMap;
    private Note selectedNote = null;

    private int xInitPos = 0;
    private int xOffset = 0;

    public PianorollPane(Track track, TimeSignature timeSignature, @NamedArg(value = "octaveCount", defaultValue = "3") int octaveCount, @NamedArg(value = "laneHeight", defaultValue = "4") int laneHeight){
        this.octaveCount = octaveCount;
        this.laneHeight = laneHeight;
        this.noteCount = octaveCount*12;
        this.track = track;
        this.timeSignature = timeSignature;
        this.noteRectangleMap = new HashMap<>();
        this.setOnMousePressed(onMousePressedEvent);
        this.reDraw();
    }

    public void reDraw(){
        this.getChildren().clear();
        this.drawGrid();
        this.drawNotes();
        //draw notes etc...
    }

    private void drawGrid(){
        int measureCount = track.getMeasures().size();
        int tsNotesInMeasure = timeSignature.numinator*8;
        int tickWidth = NoteDuration.getTsCount(resolution)*tsWidth;
        int ticksPerMeasure = tsNotesInMeasure/NoteDuration.getTsCount(resolution);
        //horizontal lanes
        for (int i = 0; i < octaveCount; i++) {
            Color blackLaneFill = new Color(0, 0, 0, 0.2);
            Color whiteLaneFill = new Color(1, 1, 1, 0);
            Rectangle b = new Rectangle(0, i*12* laneHeight + 0* laneHeight, measureCount*ticksPerMeasure*tickWidth, laneHeight);
            b.setFill(whiteLaneFill);
            Rectangle as = new Rectangle(0, i*12* laneHeight + 1* laneHeight, measureCount*ticksPerMeasure*tickWidth, laneHeight);
            as.setFill(blackLaneFill);
            Rectangle a = new Rectangle(0, i*12* laneHeight + 2* laneHeight, measureCount*ticksPerMeasure*tickWidth, laneHeight);
            a.setFill(whiteLaneFill);
            Rectangle gs = new Rectangle(0, i*12* laneHeight + 3* laneHeight, measureCount*ticksPerMeasure*tickWidth, laneHeight);
            gs.setFill(blackLaneFill);
            Rectangle g = new Rectangle(0, i*12* laneHeight + 4* laneHeight, measureCount*ticksPerMeasure*tickWidth, laneHeight);
            g.setFill(whiteLaneFill);
            Rectangle fs = new Rectangle(0, i*12* laneHeight + 5* laneHeight, measureCount*ticksPerMeasure*tickWidth, laneHeight);
            fs.setFill(blackLaneFill);
            Rectangle f = new Rectangle(0, i*12* laneHeight + 6* laneHeight, measureCount*ticksPerMeasure*tickWidth, laneHeight);
            f.setFill(whiteLaneFill);
            Rectangle e = new Rectangle(0, i*12* laneHeight + 7* laneHeight, measureCount*ticksPerMeasure*tickWidth, laneHeight);
            e.setFill(whiteLaneFill);
            Rectangle ds = new Rectangle(0, i*12* laneHeight + 8* laneHeight, measureCount*ticksPerMeasure*tickWidth, laneHeight);
            ds.setFill(blackLaneFill);
            Rectangle d = new Rectangle(0, i*12* laneHeight + 9* laneHeight, measureCount*ticksPerMeasure*tickWidth, laneHeight);
            d.setFill(whiteLaneFill);
            Rectangle cs = new Rectangle(0, i*12* laneHeight + 10* laneHeight, measureCount*ticksPerMeasure*tickWidth, laneHeight);
            cs.setFill(blackLaneFill);
            Rectangle c = new Rectangle(0, i*12* laneHeight + 11* laneHeight, measureCount*ticksPerMeasure*tickWidth, laneHeight);
            c.setFill(whiteLaneFill);

            this.getChildren().addAll(c, cs, d, ds, e, f, fs, g, gs, a, as, b);
        }
        //vertical lines
        for (int i = 0; i < measureCount; i++){
            for (int j = 0; j < ticksPerMeasure; j++) {
                int x = i*ticksPerMeasure*tickWidth + j*tickWidth;
                Line line = new Line(x, 0, x, laneHeight * noteCount);
                if (j == 0) { //Make the first vert line in measure bolder
                    line.setStrokeWidth(2);
                } else {
                    line.setStrokeWidth(1);
                }
                line.setStroke(new Color(0.2, 0.2, 0.2, 1));
                this.getChildren().add(line);
            }
        }
    }

    private void drawNotes(){
        for (Note note: track.getNotes()){
            int x = note.getLocation().getTSPosition()*tsWidth;
            int y = (noteCount - note.getPitch().ordinal())*laneHeight - laneHeight;
            int width = NoteDuration.getTsCount(note.getDuration())*tsWidth;
            Rectangle rectangle = new Rectangle(x, y, width, laneHeight);
            if (note == selectedNote){
                rectangle.setStroke(new Color(0, 0, 0, 1));
                rectangle.setStrokeWidth(2);
                rectangle.setStrokeType(StrokeType.INSIDE);
            } else {
                rectangle.setStroke(null);
            }
            Color color = new Color(1, 0, 1 - (note.getVelocity() / 127d), 1);
            rectangle.setFill(color);
            this.getChildren().add(rectangle);
            noteRectangleMap.put(note, rectangle);
            addDragDetection(rectangle);
        }
    }

    EventHandler<MouseEvent> onMousePressedEvent = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
            boolean select = false;
            for (Note note: track.getNotes()){
                if (intersects(event.getX(), event.getY(), noteRectangleMap.get(note))){
                    selectedNote = note;
                    select = true;
                    reDraw();
                    break;
                }
            }
            if (!select){
                selectedNote = null;
                reDraw();
            }
        }
    };

    private boolean intersects(double x, double y, Rectangle rectangle){
        if (x < rectangle.getX() || x > rectangle.getX() + rectangle.getWidth())
            return false;
        if (y < rectangle.getY() || y > rectangle.getY() + rectangle.getHeight())
            return false;
        return true;
    }

    private void addDragDetection(Rectangle rectangle) {
        rectangle.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                xInitPos = (int)event.getX();
                event.setDragDetect(true);
            }
        });
        rectangle.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                //xOffset = (int)(event.getX() - xInitPos);
                event.setDragDetect(false);
                recalculateNoteLocation(selectedNote, event.getX(), event.getY());
                reDraw();
                //xOffset = 0;
            }
        });
        rectangle.setOnDragDropped(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                System.out.print("done");
            }
        });
    }

    private void recalculateNoteLocation(Note note, double x, double y){
        //calculating pitch
        int pitchOrdinal = (int)(noteCount - y/laneHeight);
        if  (pitchOrdinal >= 0 && pitchOrdinal < noteCount){
            NotePitch newPitch = NotePitch.values()[pitchOrdinal];
            if (note.getPitch() != newPitch){
                System.out.println(newPitch);
                note.setPitch(newPitch);
            }
        }
        //calculation location
        int tsNotesInMeasure = timeSignature.numinator*8;
        ScrollPane scrollPane = (ScrollPane)getScene().lookup("#pianorollScrollPane");
        double hValue = scrollPane.getHvalue();
        int ticksPerMeasure = tsNotesInMeasure/NoteDuration.getTsCount(resolution);
        int resLocation = (int)(x/(tsWidth*NoteDuration.getTsCount(resolution)));
//        int resLocation = (int)((noteRectangleMap.get(note).getX() + xOffset)/(tsWidth*NoteDuration.getTsCount(resolution)));
        int measureNum = resLocation/ticksPerMeasure + 1;
        int beatInMeasure = resLocation - (measureNum - 1)*ticksPerMeasure;
        note.setLocation(NoteLocation.getNoteLocation(measureNum, beatInMeasure, resolution));
        //note.setLocation();
    }

    public NoteDuration.Duration getResolution() {
        return resolution;
    }

    public void setResolution(NoteDuration.Duration resolution) {
        this.resolution = resolution;
    }
}
