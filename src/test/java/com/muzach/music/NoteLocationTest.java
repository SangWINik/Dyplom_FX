package com.muzach.music;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class NoteLocationTest {
    @BeforeClass
    public void setup() {
        NoteLocation.setMeasureLength(4);
    }

    @Test
    public void getNoteLocationTest() {
        NoteLocation actualLocation = NoteLocation.getNoteLocation(2, 5, NoteDuration.Duration.QUARTER);

        assertEquals(1, actualLocation.getMeasureNumber());
        assertEquals(64, actualLocation.getTSPosition());
    }

    @Test
    public void getNoteLocationByTsPositionTest() {
        NoteLocation actualLocation = NoteLocation.getNoteLocation(64);

        assertEquals(2, actualLocation.getMeasureNumber());
        assertEquals(64, actualLocation.getTSPosition());
    }

    @Test
    public void getTickTest() {
        NoteLocation location = NoteLocation.getNoteLocation(137);
        int expectedTick = 412;

        int actualTick = location.getTick();

        assertEquals(expectedTick, actualTick);
    }

    @Test
    public void getTsPositionTest() {
        NoteLocation location = NoteLocation.getNoteLocation(2, 5, NoteDuration.Duration.QUARTER);

        assertEquals(64, location.getTSPosition());
    }
}
