package com.muzach.preset;

import java.util.List;

public class PresetManager implements IPresetManager {

    private List<Preset> myPresets;

    //my presets - collection of user's presets
    public PresetManager(List<Preset> myPresets) {
        this.myPresets = myPresets;
    }

    @Override
    public void saveMyPreset(Preset preset) {
        myPresets.add(preset);
        SerializationHelper.serializeMyPresets(myPresets);
    }

    @Override
    public void removePreset(Preset preset) {
        myPresets.remove(preset);
        SerializationHelper.serializeMyPresets(myPresets);
    }

    @Override
    public List<Preset> getMyPresets() {
        return SerializationHelper.deserializeMyPresets();
    }
}
