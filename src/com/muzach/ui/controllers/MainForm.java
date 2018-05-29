package com.muzach.ui.controllers;

import com.muzach.generation.*;
import com.muzach.midi.IMidiManager;
import com.muzach.midi.MidiManager;
import com.muzach.preset.Preset;
import com.muzach.utils.populators.DefaultPresets;
import com.muzach.utils.populators.Scales;
import com.muzach.playback.Player;
import com.muzach.midi.SequenceBuilder;
import com.muzach.music.NoteDuration;
import com.muzach.music.NoteLocation;
import com.muzach.music.*;
import com.muzach.preset.IPresetManager;
import com.muzach.preset.PresetManager;
import com.muzach.ui.controls.PresetPane;
import com.muzach.ui.controls.TracksPlayerPane;
import com.muzach.ui.windows.SavePresetDialog;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequencer;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainForm {

    private Stage stage;

    @FXML
    private ComboBox timeSignatureComboBox;
    @FXML
    private Slider noteValuesSlider;
    @FXML
    private Slider pauseFrequencySlider;
    @FXML
    private ComboBox scaleComboBox;
    @FXML
    private Slider pitchJumpsSlider;
    @FXML
    private Slider chordChangeFrequencySlider;
    @FXML
    private Slider chordColorSlider;
    @FXML
    private VBox myPresetsVBox;
    @FXML
    private VBox defaultPresetsVBox;
    @FXML
    private HBox playerHBox;
    @FXML
    private Spinner tempoSpinner;
    @FXML
    private CheckBox repeatCheckBox;
    @FXML
    private Spinner measureCountSpinner;

    private TracksPlayerPane tracksPlayerPane;

    private Composition composition;
    private List<Preset> defaultPresets;
    private List<Preset> myPresets;
    private List<PresetPane> presetPanes;
    private Sequencer sequencer;

    public MainForm(){
        TimeSignature timeSignature = new TimeSignature(6, 4);
        NoteLocation.setMeasureLength(timeSignature.numinator); //important

        Track melodyTrack = new Track();
        for (int i = 0; i < 4; i++) {
            melodyTrack.addNote(new Note(NotePitch.C2, NoteDuration.Duration.QUARTER, NoteLocation.getNoteLocation(i + 1, 1, NoteDuration.Duration.EIGHTH), 96));
            melodyTrack.addNote(new Note(NotePitch.Ds1, NoteDuration.Duration.QUARTER, NoteLocation.getNoteLocation(i + 1, 4, NoteDuration.Duration.EIGHTH), 30));
            melodyTrack.addNote(new Note(NotePitch.G1, NoteDuration.Duration.QUARTER, NoteLocation.getNoteLocation(i + 1, 5, NoteDuration.Duration.EIGHTH), 60));
            melodyTrack.addNote(new Note(NotePitch.As1, NoteDuration.Duration.QUARTER, NoteLocation.getNoteLocation(i + 1, 7, NoteDuration.Duration.EIGHTH), 100));
            melodyTrack.addNote(new Note(NotePitch.G1, NoteDuration.Duration.QUARTER, NoteLocation.getNoteLocation(i + 1, 9, NoteDuration.Duration.EIGHTH), 80));
            melodyTrack.addNote(new Note(NotePitch.Ds1, NoteDuration.Duration.QUARTER, NoteLocation.getNoteLocation(i + 1, 20, NoteDuration.Duration.SIXTEENTH), 70));
        }

        Track harmonyTrack = new Track();
        for (int i = 0; i < 3; i++) {
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
        composition.setMeasureCount(4);
        composition.setTempoBMP(200);
        composition.setTimeSignature(timeSignature);
        composition.setMelodyTrack(melodyTrack);
        composition.setHarmonyTrack(harmonyTrack);

        sequencer = Player.getSequencer();
    }

    public void initialize(){
        presetPanes = new ArrayList<>();

        initAdvancedSettings();
        initMyPresets();
        initDefaultPresets();

        repeatCheckBox.selectedProperty().addListener((observable, oldValue, newValue) -> Player.getPlayerMetaEventListener().setLoop(newValue));

        tracksPlayerPane = new TracksPlayerPane(composition, sequencer);
        Runnable thread = () -> {
            while(true) {
                try {
                    if (sequencer.isOpen()){
                        Platform.runLater(() -> {
                            tracksPlayerPane.setMarkerPosition((int)sequencer.getTickPosition());
                        });
                    }
                    Thread.sleep(40);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        Thread threadEx = new Thread(thread);
        threadEx.start();
        playerHBox.getChildren().addAll(tracksPlayerPane);
    }

    private void initDefaultPresets(){
        defaultPresetsVBox.getChildren().clear();
        defaultPresets = DefaultPresets.get();
        for (Preset preset: defaultPresets) {
            PresetPane presetPane = new PresetPane(preset, true);
            presetPanes.add(presetPane);
            defaultPresetsVBox.getChildren().add(presetPane);
        }
        initPresetEvents();
    }

    private void initMyPresets(){
        IPresetManager presetManager = new PresetManager(myPresets);
        myPresetsVBox.getChildren().clear();
        myPresets = presetManager.getMyPresets();
        if (myPresets == null || myPresets.isEmpty()) {
            Label noPresets = new Label("There are no saved presets right now. Go to Advanced Settings to create your own preset or Presets to choose one of predefined.");
            myPresetsVBox.getChildren().add(noPresets);
        }
        for (Preset preset: myPresets){
            PresetPane presetPane = new PresetPane(preset, false);
            presetPanes.add(presetPane);
            myPresetsVBox.getChildren().add(presetPane);
        }
        initPresetEvents();
    }

    private void initPresetEvents() {
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
            presetPane.setOnDeleteAction(event -> deletePreset(presetPane.getPreset()));
        }
    }

    private void initAdvancedSettings() {
        TimeSignature ts1 = new TimeSignature(2, 4);
        TimeSignature ts2 = new TimeSignature(3, 4);
        TimeSignature ts3 = new TimeSignature(4, 4);
        TimeSignature ts4 = new TimeSignature(5, 4);
        TimeSignature ts5 = new TimeSignature(7, 4);
        timeSignatureComboBox.getItems().addAll(ts1, ts2, ts3, ts4, ts5);
        scaleComboBox.getItems().addAll(Scales.getScales());
        Preset defaultPreset = new Preset();
        defaultPreset.setTimeSignature(ts3);
        defaultPreset.setNoteValues(50);
        defaultPreset.setPauseFrequency(50);
        defaultPreset.setScale(Scales.getScales().get(0));
        defaultPreset.setPitchJumps(50);
        defaultPreset.setChordChangeFrequency(50);
        defaultPreset.setChordColor(50);
        setAdvancedSettingsWithPreset(defaultPreset);
    }

    private Preset parseAdvancedSettings(){
        Preset preset = new Preset();
        preset.setTimeSignature((TimeSignature) timeSignatureComboBox.getValue());
        preset.setNoteValues(noteValuesSlider.getValue());
        preset.setPauseFrequency(pauseFrequencySlider.getValue());
        preset.setScale((Scale) scaleComboBox.getValue());
        preset.setPitchJumps(pitchJumpsSlider.getValue());
        preset.setChordChangeFrequency(chordChangeFrequencySlider.getValue());
        preset.setChordColor(chordColorSlider.getValue());
        return preset;
    }

    private void setAdvancedSettingsWithPreset(Preset preset){
        TimeSignature newTimeSignature = (TimeSignature) timeSignatureComboBox.getItems().stream().filter(t -> ((TimeSignature)(t)).toString().equals(preset.getTimeSignature().toString())).findFirst().get();
        Scale newScale = (Scale) scaleComboBox.getItems().stream().filter(s -> ((Scale)(s)).toString().equals(preset.getScale().toString())).findFirst().get();
        timeSignatureComboBox.setValue(newTimeSignature);
        noteValuesSlider.setValue(preset.getNoteValues());
        pauseFrequencySlider.setValue(preset.getPauseFrequency());
        scaleComboBox.setValue(newScale);
        pitchJumpsSlider.setValue(preset.getPitchJumps());
        chordChangeFrequencySlider.setValue(preset.getChordChangeFrequency());
        chordColorSlider.setValue(preset.getChordColor());
    }

    public void generate() {
        composition = new Composition();
        composition.setMeasureCount((int) measureCountSpinner.getValue());
        IMusicGenerator generator = new MusicGenerator(composition, parseAdvancedSettings());
        composition = generator.generate();
        tracksPlayerPane.setComposition(composition);
        tracksPlayerPane.reDraw();
    }

    public void play() {
        try {
            composition.setTempoBMP((int)tempoSpinner.getValue());
            Player.playComposition(composition, repeatCheckBox.isSelected());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void pause() {
        Player.pauseComposition();
    }

    public void stop() {
        Player.stopComposition();
    }

    public void savePreset() {
        SavePresetDialog savePresetDialog = new SavePresetDialog(parseAdvancedSettings(), myPresets);
        savePresetDialog.display();
        initMyPresets();
    }

    private void deletePreset(Preset preset){
        IPresetManager presetManager = new PresetManager(myPresets);
        presetManager.removePreset(preset);
        initMyPresets();
    }

    public void saveMidi(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save MIDI file");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("MIDI file(*.mid)", "*.mid"));
        fileChooser.setInitialFileName("moose");
        File file = fileChooser.showSaveDialog(stage);
        if (file != null) {
            try {
                IMidiManager midiManager = new MidiManager();
                midiManager.saveMidiFile(composition, file);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
