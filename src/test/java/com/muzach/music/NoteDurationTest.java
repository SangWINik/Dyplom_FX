package com.muzach.music;

import com.muzach.midi.SequenceBuilder;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class NoteDurationTest {
    @Test
    public void getTickCountTest() {
        int quarterNoteDuration = SequenceBuilder.TICKS_PER_QUARTER_NOTE;

        assertEquals(quarterNoteDuration*4, NoteDuration.getTickCount(NoteDuration.Duration.WHOLE));
        assertEquals(quarterNoteDuration*2, NoteDuration.getTickCount(NoteDuration.Duration.HALF));
        assertEquals(quarterNoteDuration, NoteDuration.getTickCount(NoteDuration.Duration.QUARTER));
        assertEquals(quarterNoteDuration/2, NoteDuration.getTickCount(NoteDuration.Duration.EIGHTH));
        assertEquals(quarterNoteDuration/4, NoteDuration.getTickCount(NoteDuration.Duration.SIXTEENTH));
        assertEquals(quarterNoteDuration/8, NoteDuration.getTickCount(NoteDuration.Duration.THIRTYSECOND));
    }

    @Test
    public void getTsCountTest() {
        assertEquals(1, NoteDuration.getTsCount(NoteDuration.Duration.THIRTYSECOND));
        assertEquals(2, NoteDuration.getTsCount(NoteDuration.Duration.SIXTEENTH));
        assertEquals(4, NoteDuration.getTsCount(NoteDuration.Duration.EIGHTH));
        assertEquals(8, NoteDuration.getTsCount(NoteDuration.Duration.QUARTER));
        assertEquals(16, NoteDuration.getTsCount(NoteDuration.Duration.HALF));
        assertEquals(32, NoteDuration.getTsCount(NoteDuration.Duration.WHOLE));
    }
}
