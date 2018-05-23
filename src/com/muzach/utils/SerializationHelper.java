package com.muzach.utils;

import com.muzach.Main;
import com.muzach.generation.Preset;

import java.io.*;
import java.util.List;

public class SerializationHelper {
    private static final String path = "./mypresets.mzk";

    public static void serializeMyPresets(List<Preset> myPresets) {
        try {
            FileOutputStream fileOut = new FileOutputStream(path);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOut);
            objectOutputStream.writeObject(myPresets);
            objectOutputStream.close();
            fileOut.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static List<Preset> deserializeMyPresets() {
        List<Preset> presets = null;
        try {
            FileInputStream fileIn = new FileInputStream(path);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileIn);
            presets = (List<Preset>) objectInputStream.readObject();
            objectInputStream.close();
            fileIn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return presets;
    }
}
