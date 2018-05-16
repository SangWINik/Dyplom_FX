package com.muzach.midi;

import com.muzach.midi.note.NoteDuration;
import com.muzach.midi.note.NoteLocation;
import com.muzach.music.Note;

import javax.sound.midi.*;
import java.nio.ByteBuffer;
import java.util.Arrays;

public class SequenceBuilder {
    //todo make a property file for these
    public static final int TICKS_PER_QUARTER_NOTE = 24;

    private Sequence sequence;
    private Track track;
    //int eighth notes
    private int measureLength;

    public SequenceBuilder(int measureLength) throws InvalidMidiDataException {
        this.measureLength = measureLength;
        sequence = new Sequence(Sequence.PPQ, TICKS_PER_QUARTER_NOTE);
        track = sequence.createTrack();
    }

    public Sequence getSequence() {
        return this.sequence;
    }

    public void setTimeSignature(byte numinator, byte denominator) throws InvalidMidiDataException {
        MetaMessage mm = new MetaMessage();
        byte[] tsBytes = new byte[]{numinator, denominator, 24, 8};
        mm.setMessage(0x58, tsBytes, 4);
        MidiEvent me = new MidiEvent(mm, 0);
        track.add(me);
    }

    public void setNote(Note note) throws Exception{
        ShortMessage sm;
        MidiEvent me;

        //note on
        sm = new ShortMessage();
        sm.setMessage(0x90, note.getPitch().getMidiNote(), note.getVelocity());
        me = new MidiEvent(sm, note.getLocation().getTick());
        track.add(me);

        //note off
        sm = new ShortMessage();
        sm.setMessage(0x80, note.getPitch().getMidiNote(), note.getVelocity());
        me = new MidiEvent(sm, note.getLocation().getTick() + NoteDuration.getTickCount(note.getDuration()));
        track.add(me);
    }

    public void setNote(NoteLocation noteLocation, int pitch, NoteDuration.Duration duration, int velocity) throws InvalidMidiDataException {
        ShortMessage sm;
        MidiEvent me;

        //note on
        sm = new ShortMessage();
        sm.setMessage(0x90, pitch, velocity);
        me = new MidiEvent(sm, noteLocation.getTick());
        track.add(me);

        //note off
        sm = new ShortMessage();
        sm.setMessage(0x80, pitch, velocity);
        me = new MidiEvent(sm, noteLocation.getTick() + NoteDuration.getTickCount(duration));
        track.add(me);
    }
}

