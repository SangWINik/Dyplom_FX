package com.muzach.ui.controls;

import com.muzach.music.TimeSignature;
import com.muzach.music.Track;
import com.muzach.ui.windows.MidiEditorWindow;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class TrackPane extends Pane {

    private Track track;
    private TimeSignature timeSignature;

    private boolean muted = false;

    public TrackPane(Track track, TimeSignature timeSignature) {
        this.track = track;
        this.timeSignature = timeSignature;
        this.reDraw();
        this.initEvents();
    }

    public void reDraw(){
        this.getChildren().clear();
        Background background = null;
        if (this.muted) {
            background = new Background(new BackgroundFill(Color.GRAY, new CornerRadii(2), null));
        } else {
            background = new Background(new BackgroundFill(Color.LIGHTGRAY, new CornerRadii(2), null));
        }
        this.setBackground(background);
    }

    public void mute(){
        this.muted = true;
        this.track.mute();
        this.reDraw();
    }

    public void unmute(){
        this.muted = false;
        this.track.unmute();
        this.reDraw();
    }

    private void initEvents(){
        this.setOnMouseClicked(event -> {
            if (event.getButton().equals(MouseButton.PRIMARY)){
                if (event.getClickCount() == 2) {
                    MidiEditorWindow midiEditor = new MidiEditorWindow(track, timeSignature);
                    midiEditor.showModal();
                }
            }
        });
    }
}
