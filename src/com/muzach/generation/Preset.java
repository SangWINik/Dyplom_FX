package com.muzach.generation;

import com.muzach.music.TimeSignature;

import java.io.Serializable;

public class Preset implements Serializable {

    private String name;
    private String description;

    private TimeSignature timeSignature;

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
}
