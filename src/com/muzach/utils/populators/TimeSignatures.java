package com.muzach.utils.populators;

import com.muzach.music.TimeSignature;

import java.util.ArrayList;
import java.util.List;

public class TimeSignatures {
    private static List<TimeSignature> timeSignatures;

    static {
        timeSignatures = new ArrayList<>();
        timeSignatures.add(new TimeSignature(2,4));
        timeSignatures.add(new TimeSignature(3,4));
        timeSignatures.add(new TimeSignature(4,4));
        timeSignatures.add(new TimeSignature(5,4));
        timeSignatures.add(new TimeSignature(7,4));
    }

    public List<TimeSignature> get() {
        return timeSignatures;
    }
}
