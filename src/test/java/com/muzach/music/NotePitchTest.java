package com.muzach.music;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class NotePitchTest {
    private static final int OFFSET = 50;

    @Test(dataProvider = "notesByOffsetProvider")
    public void notePitchTest(int midiNote, NotePitch pitch) {
        assertEquals(midiNote, pitch.getMidiNote(OFFSET));
    }

    @DataProvider
    public Object[][] notesByOffsetProvider() {
        Object[][] data = new Object[][]{
                {OFFSET, NotePitch.C0},
                {OFFSET + 1, NotePitch.Cs0},
                {OFFSET + 2, NotePitch.D0},
                {OFFSET + 3, NotePitch.Ds0},
                {OFFSET + 4, NotePitch.E0},
                {OFFSET + 5, NotePitch.F0},
                {OFFSET + 6, NotePitch.Fs0},
                {OFFSET + 7, NotePitch.G0},
                {OFFSET + 8, NotePitch.Gs0},
                {OFFSET + 9, NotePitch.A0},
                {OFFSET + 10, NotePitch.As0},
                {OFFSET + 11, NotePitch.B0}
        };
        return data;
    }
}
