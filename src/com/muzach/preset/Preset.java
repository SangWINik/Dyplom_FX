package com.muzach.preset;

import com.muzach.music.Scale;
import com.muzach.music.TimeSignature;

import java.io.Serializable;

public class Preset implements Serializable {

    private String name;
    private String description;

    //rhythm
    private TimeSignature timeSignature;
    private double noteValues;
    private double pauseFrequency;
    //melody
    private Scale scale;
    private double pitchJumps;
    //harmony
    private double chordChangeFrequency;
    private double chordColor;

    public Preset(){}

    public Preset(String name, String description, TimeSignature timeSignature, double noteValues, double pauseFrequency, Scale scale, double pitchJumps, double chordChangeFrequency, double chordColor) {
        this.name = name;
        this.description = description;
        this.timeSignature = timeSignature;
        this.noteValues = noteValues;
        this.pauseFrequency = pauseFrequency;
        this.scale = scale;
        this.pitchJumps = pitchJumps;
        this.chordChangeFrequency = chordChangeFrequency;
        this.chordColor = chordColor;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TimeSignature getTimeSignature() {
        return timeSignature;
    }

    public void setTimeSignature(TimeSignature timeSignature) {
        this.timeSignature = timeSignature;
    }

    public double getNoteValues() {
        return noteValues;
    }

    public void setNoteValues(double noteValues) {
        this.noteValues = noteValues;
    }

    public double getPauseFrequency() {
        return pauseFrequency;
    }

    public void setPauseFrequency(double pauseFrequency) {
        this.pauseFrequency = pauseFrequency;
    }

    public Scale getScale() {
        return scale;
    }

    public void setScale(Scale scale) {
        this.scale = scale;
    }

    public double getPitchJumps() {
        return pitchJumps;
    }

    public void setPitchJumps(double pitchJumps) {
        this.pitchJumps = pitchJumps;
    }

    public double getChordChangeFrequency() {
        return chordChangeFrequency;
    }

    public void setChordChangeFrequency(double chordChangeFrequency) {
        this.chordChangeFrequency = chordChangeFrequency;
    }

    public double getChordColor() {
        return chordColor;
    }

    public void setChordColor(double chordColor) {
        this.chordColor = chordColor;
    }
}
