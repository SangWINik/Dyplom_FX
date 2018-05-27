package com.muzach.midi;

import com.muzach.music.Composition;
import com.muzach.music.NotePitch;

import javax.sound.midi.*;

public class Player {
    private static Sequencer sequencer;
    private static PlayerMetaEventListener playerMetaEventListener;
    //used to preview notes in editor
    private static Synthesizer midiSynth;

    static {
        try {
            sequencer = MidiSystem.getSequencer();
            playerMetaEventListener = new PlayerMetaEventListener(false, 120);

            midiSynth = MidiSystem.getSynthesizer();
            midiSynth.loadInstrument(midiSynth.getDefaultSoundbank().getInstruments()[0]);
            midiSynth.open();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Sequencer playComposition(Composition composition, boolean loop) {
        try {
            //sequencer.close();
            if (!sequencer.isOpen()) {
                SequenceBuilder sequenceBuilder = new SequenceBuilder(composition.getTimeSignature(), composition.getMeasureCount(), false);
                sequenceBuilder.setMelodyTrack(composition.getMelodyTrack());
                sequenceBuilder.setHarmonyTrack(composition.getHarmonyTrack());
                sequenceBuilder.setInstrument(0);
                Sequence sequence = sequenceBuilder.getSequence();
                sequencer.setSequence(sequence);
                sequencer.open();
            }
            playerMetaEventListener.setTempo(composition.getTempoBMP());
            playerMetaEventListener.setLoop(loop);
            sequencer.addMetaEventListener(playerMetaEventListener);
            sequencer.setTempoInBPM(composition.getTempoBMP());
            if (composition.getMelodyTrack().isMuted()){
                sequencer.setTrackMute(0, true);
            }
            if (composition.getHarmonyTrack().isMuted()){
                sequencer.setTrackMute(1, true);
            }
            sequencer.start();
            //set on finish event handler
            return sequencer;
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public static void playOneNote(NotePitch pitch) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    midiSynth.getChannels()[0].noteOn(pitch.getMidiNote(), 100);
                    Thread.sleep(500);
                    midiSynth.getChannels()[0].noteOff(pitch.getMidiNote());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }

    public static Sequencer getSequencer() {
        return sequencer;
    }

    public static PlayerMetaEventListener getPlayerMetaEventListener() {
        return playerMetaEventListener;
    }
}
