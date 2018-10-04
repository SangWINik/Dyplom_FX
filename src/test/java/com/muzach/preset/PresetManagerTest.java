package com.muzach.preset;

import org.testng.annotations.Test;

public class PresetManagerTest {

    @Test(expectedExceptions = {NullPointerException.class})
    public void saveMyPresetNullExceptionTest() {
        PresetManager presetManager = new PresetManager(null);
        Preset preset = new Preset();
        preset.setName("preset");

        presetManager.saveMyPreset(preset);
    }
}
