package com.muzach.ui.controls;

import com.muzach.music.Composition;
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

    public TracksPlayerPane(Composition composition, Sequencer sequencer){
        this.composition = composition;
        this.sequencer = sequencer;
        this.reDraw();
    }

    public void reDraw(){
        //this.setBackground(new Background(new BackgroundFill(Color.RED, null, null)));
        this.setPrefWidth(780);

        TrackPane melodyTrackPane = new TrackPane(composition.getMelodyTrack(), composition.getTimeSignature());
        melodyTrackPane.setPrefSize(getPrefWidth() - buttonWidth - horizontalSpacing, trackHeight);
        Button editMelodyButton = new Button("Edit");
        editMelodyButton.setPrefWidth(buttonWidth);
        Button muteMelodyButton = new Button("Mute");
        muteMelodyButton.setPrefWidth(buttonWidth);
        VBox melodyButtonsVBox = new VBox(5, editMelodyButton, muteMelodyButton);
        melodyButtonsVBox.setAlignment(Pos.TOP_LEFT);
        HBox melodyTrackHBox = new HBox(horizontalSpacing, melodyTrackPane, melodyButtonsVBox);
        muteMelodyButton.setOnAction(event -> {
            onMuteClick(melodyTrackPane, muteMelodyButton);
        });
        editMelodyButton.setOnAction(event -> {
            MidiEditorWindow midiEditor = new MidiEditorWindow(composition.getMelodyTrack(), composition.getTimeSignature());
            midiEditor.showModal();
        });

        TrackPane harmonyTrackPane = new TrackPane(composition.getHarmonyTrack(), composition.getTimeSignature());
        harmonyTrackPane.setPrefSize(getPrefWidth() - buttonWidth - horizontalSpacing, trackHeight);
        Button editHarmonyButton = new Button("Edit");
        editHarmonyButton.setPrefWidth(buttonWidth);
        Button muteHarmonyButton = new Button("Mute");
        muteHarmonyButton.setPrefWidth(buttonWidth);
        VBox harmonyButtonsVBox = new VBox(5, editHarmonyButton, muteHarmonyButton);
        harmonyButtonsVBox.setAlignment(Pos.TOP_LEFT);
        HBox harmonyTrackHBox = new HBox(horizontalSpacing, harmonyTrackPane, harmonyButtonsVBox);
        muteHarmonyButton.setOnAction(event -> {
            onMuteClick(harmonyTrackPane, muteHarmonyButton);
        });
        editHarmonyButton.setOnAction(event -> {
            MidiEditorWindow midiEditor = new MidiEditorWindow(composition.getHarmonyTrack(), composition.getTimeSignature());
            midiEditor.showModal();
        });

        VBox tracksVBox = new VBox(verticalSpacing, melodyTrackHBox, harmonyTrackHBox);
        tracksVBox.setPadding(new Insets(padding, 0, 0, 0));

        Line marker = new Line(markerPosition, 0, markerPosition, trackHeight*2 + verticalSpacing + padding);
        this.getChildren().addAll(tracksVBox, marker);
    }

    public int getMarkerPosition() {
        return markerPosition;
    }

    public void onMuteClick(TrackPane trackPane, Button muteButton) {
        trackPane.mute();
        muteButton.setText("Unmute");
        muteButton.setOnAction(event -> {
            onUnmuteClick(trackPane, muteButton);
        });
    }

    public void onUnmuteClick(TrackPane trackPane, Button muteButton) {
        trackPane.unmute();
        muteButton.setText("Mute");
        muteButton.setOnAction(event -> {
            onMuteClick(trackPane, muteButton);
        });
    }
}
