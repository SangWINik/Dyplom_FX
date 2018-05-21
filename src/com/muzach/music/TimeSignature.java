package com.muzach.music;

import java.io.Serializable;

public class TimeSignature implements Serializable {
    public int numinator;
    public int denominator;

    public TimeSignature(int num, int den) {
        numinator = num;
        denominator = den;
    }

    @Override
    public String toString() {
        return numinator + "/" + denominator;
    }
}
