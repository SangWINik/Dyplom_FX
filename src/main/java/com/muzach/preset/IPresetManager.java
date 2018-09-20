package com.muzach.preset;

import java.util.List;

public interface IPresetManager {
    void saveMyPreset(Preset preset);
    void removePreset(Preset preset);
    List<Preset> getMyPresets();
}
