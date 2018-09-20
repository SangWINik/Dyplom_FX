package com.muzach.ui.windows;

import com.muzach.music.TimeSignature;
import com.muzach.generation.Track;
import com.muzach.ui.controllers.MidiEditor;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MidiEditorWindow extends Stage {
    private Parent root;
    private MidiEditor controller;

    public MidiEditorWindow(Track track, TimeSignature timeSignature, int measureCount, int toneOffset) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("com/muzach/ui/fxforms/midiEditor.fxml"));
            loader.setController(new MidiEditor(track, timeSignature, measureCount, toneOffset));
            root = loader.load();
            setResizable(false);
            setScene(new Scene(root));
            controller = loader.getController();
            initModality(Modality.APPLICATION_MODAL);
            setTitle("Midi Editor");
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public Track showModal(){
        showAndWait();
        return controller.getTrack();
    }
}
