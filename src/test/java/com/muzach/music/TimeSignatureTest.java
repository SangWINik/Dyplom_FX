package com.muzach.music;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TimeSignatureTest {
    @Test
    public void timeSignatureTest() {
        int numinator = 2;
        int denuminator = 4;

        TimeSignature timeSignature = new TimeSignature(2, 4);

        assertEquals(numinator, timeSignature.numinator);
        assertEquals(denuminator, timeSignature.denominator);
    }
}
