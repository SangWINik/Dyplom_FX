package com.muzach.preset;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class SerializationHelper {
    private static final Logger LOGGER = LogManager.getLogger(SerializationHelper.class);

    private static String path = "./mypresets.mzk";

    private SerializationHelper() {}

    public static void serializeMyPresets(List<Preset> myPresets) {
        try {
            LOGGER.info(String.format("Saving presets. COUNT[%s].", myPresets.size()));
            FileOutputStream fileOut = new FileOutputStream(path);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOut);
            objectOutputStream.writeObject(myPresets);
            objectOutputStream.close();
            fileOut.close();
            LOGGER.info("All presets are successfully serialized. Resources closed.");
        } catch (Exception e) {
            LOGGER.error(String.format("Unexpected exception while saving presets. Exception message: %s", e.getMessage()), e);
        }
    }

    public static List<Preset> deserializeMyPresets() {
        List<Preset> presets;
        try {
            LOGGER.info("Retrieving presets from file " + path);
            FileInputStream fileIn = new FileInputStream(path);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileIn);
            presets = (List<Preset>) objectInputStream.readObject();
            objectInputStream.close();
            fileIn.close();
            LOGGER.info(String.format("Presets retrieved. COUNT[%s]", presets.size()));
        } catch (Exception e) {
            LOGGER.warn("Exception thrown while retrieving presets from file", e);
            return new ArrayList<>();
        }
        return presets;
    }

    protected static void setPath(String path) {
        SerializationHelper.path = path;
    }
}
