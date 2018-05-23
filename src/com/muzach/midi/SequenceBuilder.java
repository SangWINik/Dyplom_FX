package com.muzach.midi;

import com.muzach.midi.note.NoteDuration;
import com.muzach.midi.note.NoteLocation;
import com.muzach.music.Note;
import com.muzach.music.TimeSignature;

import javax.sound.midi.*;

public class SequenceBuilder {
    //todo make a property file for these
    public static final int TICKS_PER_QUARTER_NOTE = 24;

    private Sequence sequence;
    private Track melodyTrack;
    private Track harmonyTrack;

    public SequenceBuilder(TimeSignature timeSignature) throws InvalidMidiDataException {
        sequence = new Sequence(Sequence.PPQ, TICKS_PER_QUARTER_NOTE);
        melodyTrack = sequence.createTrack();
        harmonyTrack = sequence.createTrack();
        setTimeSignature(timeSignature);
    }

    public Sequence getSequence() {
        return this.sequence;
    }

    public void setTimeSignature(TimeSignature timeSignature) throws InvalidMidiDataException {
        MetaMessage mm = new MetaMessage();
        byte[] tsBytes = new byte[]{(byte)timeSignature.numinator, (byte)timeSignature.denominator, 24, 8};
        mm.setMessage(0x58, tsBytes, 4);
        MidiEvent me = new MidiEvent(mm, 0);
        melodyTrack.add(me);
        harmonyTrack.add(me);
    }

    public void setMelodyTrack(com.muzach.music.Track track) {
        for (Note note: track.getNotes()){
            setNote(melodyTrack, note);
        }
    }

    public void setHarmonyTrack(com.muzach.music.Track track) {
        for (Note note: track.getNotes()){
            setNote(harmonyTrack, note);
        }
    }

    private void setNote(Track track, Note note) {
        ShortMessage sm;
        MidiEvent me;

        try {
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
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void setInstrument(int instrument) throws Exception{
        ShortMessage sm = new ShortMessage();
        sm.setMessage(0xC0, instrument, 0x00);
        MidiEvent me = new MidiEvent(sm, 0);
        melodyTrack.add(me);
        harmonyTrack.add(me);
    }
}

