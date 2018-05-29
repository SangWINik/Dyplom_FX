package com.muzach.ui.controls;

import com.muzach.playback.Player;
import com.muzach.generation.Composition;
import com.muzach.ui.windows.MidiEditorWindow;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.shape.Line;

import javax.sound.midi.Sequencer;

public class TracksPlayerPane extends Pane {
    private static final int buttonWidth = 75;
    private static final int horizontalSpacing = 5;
    private static final int verticalSpacing = 10;
    private static final int padding = 10;
    private static final int trackHeight = 80;

    private Composition composition;
    private Sequencer sequencer;
    private int markerPosition = 0;

    private Line marker;

    public TracksPlayerPane(Composition composition, Sequencer sequencer){
        this.composition = composition;
        this.sequencer = sequencer;
        this.setPrefWidth(780);
        this.marker = new Line();
        this.reDraw();
    }

    public void reDraw(){
        this.getChildren().clear();

        double trackWidth = getPrefWidth() - buttonWidth - horizontalSpacing;

        TrackPane melodyTrackPane = new TrackPane(composition.getMelodyTrack(), composition.getTimeSignature(), composition.getMeasureCount(), "Melody", trackWidth, trackHeight);
        Button editMelodyButton = new Button("Edit");
        editMelodyButton.setPrefWidth(buttonWidth);
        Button muteMelodyButton = new Button("Mute");
        muteMelodyButton.setPrefWidth(buttonWidth);
        VBox melodyButtonsVBox = new VBox(5, editMelodyButton, muteMelodyButton);
        melodyButtonsVBox.setAlignment(Pos.TOP_LEFT);
        HBox melodyTrackHBox = new HBox(horizontalSpacing, melodyTrackPane, melodyButtonsVBox);
        muteMelodyButton.setOnAction(event -> {
            onMuteClick(melodyTrackPane, muteMelodyButton, true);
        });
        editMelodyButton.setOnAction(event -> {
            MidiEditorWindow midiEditor = new MidiEditorWindow(composition.getMelodyTrack(), composition.getTimeSignature(), composition.getMeasureCount());
            midiEditor.showModal();
            reDraw();
        });

        TrackPane harmonyTrackPane = new TrackPane(composition.getHarmonyTrack(), composition.getTimeSignature(), composition.getMeasureCount(), "Harmony", trackWidth, trackHeight);
        Button editHarmonyButton = new Button("Edit");
        editHarmonyButton.setPrefWidth(buttonWidth);
        Button muteHarmonyButton = new Button("Mute");
        muteHarmonyButton.setPrefWidth(buttonWidth);
        VBox harmonyButtonsVBox = new VBox(5, editHarmonyButton, muteHarmonyButton);
        harmonyButtonsVBox.setAlignment(Pos.TOP_LEFT);
        HBox harmonyTrackHBox = new HBox(horizontalSpacing, harmonyTrackPane, harmonyButtonsVBox);
        muteHarmonyButton.setOnAction(event -> {
            onMuteClick(harmonyTrackPane, muteHarmonyButton, false);
        });
        editHarmonyButton.setOnAction(event -> {
            MidiEditorWindow midiEditor = new MidiEditorWindow(composition.getHarmonyTrack(), composition.getTimeSignature(), composition.getMeasureCount());
            midiEditor.showModal();
            reDraw();
        });

        VBox tracksVBox = new VBox(verticalSpacing, melodyTrackHBox, harmonyTrackHBox);
        tracksVBox.setPadding(new Insets(padding, 0, 0, 0));

        marker = new Line(0, 0, 0, trackHeight*2 + verticalSpacing + padding);
        this.getChildren().addAll(tracksVBox, marker);
    }

    public int getMarkerPosition() {
        return markerPosition;
    }

    public void setComposition(Composition composition) {
        this.composition = composition;
    }

    public void setMarkerPosition(int markerPosition) {
        if (markerPosition != this.markerPosition) {
            this.markerPosition = markerPosition;
            double trackWidth = getPrefWidth() - buttonWidth - horizontalSpacing;
            long tickCount = Player.getSequencer().getTickLength();
            double markerTickRate = tickCount!=0?trackWidth/tickCount:0;
            marker.setStartX(markerPosition*markerTickRate);
            marker.setEndX(markerPosition*markerTickRate);
        }
    }

    private void onMuteClick(TrackPane trackPane, Button muteButton, boolean isMelody) {
        trackPane.mute();
        muteButton.setText("Unmute");
        Player.getSequencer().setTrackMute(isMelody?0:1, true);
        muteButton.setOnAction(event -> {
            onUnmuteClick(trackPane, muteButton, isMelody);
        });
    }

    private void onUnmuteClick(TrackPane trackPane, Button muteButton, boolean isMelody) {
        trackPane.unmute();
        muteButton.setText("Mute");
        Player.getSequencer().setTrackMute(isMelody?0:1, false);
        muteButton.setOnAction(event -> {
            onMuteClick(trackPane, muteButton, isMelody);
        });
    }
}
