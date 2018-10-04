package com.muzach.preset;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static org.testng.Assert.assertEquals;

public class SerializationHelperTest {
    private final static String NORMAL_FILE_PATH = "defaultfile.mzk";
    private final static String CORRUPT_FILE_PATH = "corruptfile.mzk";
    private final static String PRESET_NAME = "preset_name";

    private List<Preset> myPresets;
    private FileInputStream fileInputStream;
    private ObjectInputStream objectInputStream;
    private FileOutputStream fileOutputStream;
    private ObjectOutputStream objectOutputStream;

    @BeforeClass
    public void setup() throws Exception {
        myPresets = new ArrayList<>();
        Preset preset = new Preset();
        preset.setName(PRESET_NAME);
        myPresets.add(preset);

        fileOutputStream = new FileOutputStream(CORRUPT_FILE_PATH);
        objectOutputStream = new ObjectOutputStream(fileOutputStream);
        objectOutputStream.writeObject(preset); // invalid data for this file
    }

    @Test
    public void serializeMyPresetsTest() throws Exception {
        SerializationHelper.setPath(NORMAL_FILE_PATH);
        SerializationHelper.serializeMyPresets(myPresets);

        fileInputStream = new FileInputStream(NORMAL_FILE_PATH);
        objectInputStream = new ObjectInputStream(fileInputStream);
        List<Preset> actual = (List<Preset>) objectInputStream.readObject();

        assertEquals(1, actual.size());
        assertEquals(PRESET_NAME, actual.get(0).getName());
    }

    @Test(dependsOnMethods = {"serializeMyPresetsTest"})
    public void deserializeMyPresetsTest() {
        SerializationHelper.setPath(NORMAL_FILE_PATH);
        List<Preset> presets = SerializationHelper.deserializeMyPresets();

        assertEquals(presets.size(), 1);
        assertEquals(PRESET_NAME, presets.get(0).getName());
    }

    @AfterClass
    public void cleanup() throws Exception {
        if (fileInputStream != null) {
            fileInputStream.close();
        }
        if (fileOutputStream != null) {
            fileOutputStream.close();
        }
        if (objectInputStream != null) {
            objectInputStream.close();
        }
        if (objectOutputStream != null) {
            objectOutputStream.close();
        }
        File normalFile = new File(NORMAL_FILE_PATH);
        normalFile.delete();
        File corruptFile = new File(CORRUPT_FILE_PATH);
        corruptFile.delete();
    }
}
