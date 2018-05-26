package com.muzach.generation;

import com.muzach.midi.note.NoteDuration;
import com.muzach.midi.note.NoteLocation;
import com.muzach.music.Note;
import com.muzach.music.NotePitch;
import com.muzach.music.Track;

import java.util.*;
import java.util.stream.Collectors;

public class GeneratorHelper implements IGeneratorHelper {
    private Random random;

    public GeneratorHelper() {
        random = new Random();
    }

    @Override
    public void populateMelodyRythm(Track melodyTrack, Preset preset, int measureCount) {
        int quarterBeatsPerMeasure = preset.getTimeSignature().numinator;
        boolean halfAllowed;
        for (int i = 0; i < measureCount; i++) {
            int currentBeat = 1;
            while (currentBeat < quarterBeatsPerMeasure + 1) {
                halfAllowed = quarterBeatsPerMeasure - currentBeat > 0;
                MelodySegment option = getRandomMelodySegment(preset, currentBeat == 1 ? true : false, halfAllowed);
                List<Note> segmentNotes = option.getNotes(NoteLocation.getNoteLocation(i + 1, currentBeat, NoteDuration.Duration.QUARTER));
                melodyTrack.getNotes().addAll(segmentNotes);
                if (option.getDuration() == NoteDuration.Duration.HALF) {
                    currentBeat += 2;
                } else {
                    currentBeat++;
                }
            }

        }
    }

    @Override
    public void populateMelodyPitch(Track melodyTrack, Preset preset) {
        int keyNoteChances = 2;

        List<NotePitch> options = preset.getScale().getNotePitches();
        List<NotePitch> optionsCorrected = new ArrayList<>();
        optionsCorrected.addAll(options);
        for (int i = 0; i < keyNoteChances; i++){
            optionsCorrected.addAll(preset.getScale().getKeyNotes());
        }

        melodyTrack.getNotes().sort(Comparator.comparingInt(n -> n.getLocation().getTSPosition()));

        Note note0 = melodyTrack.getNotes().get(0);
        note0.setPitch(NotePitch.C1);
        for (int i = 1; i < melodyTrack.getNotes().size(); i++){
            melodyTrack.getNotes().get(i).setPitch(optionsCorrected.get(random.nextInt(optionsCorrected.size())));
        }
    }

    @Override
    public void populateHarmonyRythm(Track harmonyTrack, Preset preset, int measureCount) {

    }

    @Override
    public void populateHarmonyChords(Track harmonyTrack, Preset preset) {

    }

    private MelodySegment getRandomMelodySegment(Preset preset, boolean isOnFirstBeat, boolean halfAllowed) {
        int level1Chances = 100;
        int level2Chances = 50;
        int level3Chances = 20;
        int level4Chances = 5;
        int level1Margin = 20;
        int level2Margin = 40;
        int level3Margin = 60;

        List<MelodySegment> options = MelodySegments.get(isOnFirstBeat, halfAllowed);
        List<MelodySegment> normalizedOptions = new ArrayList<>();

        List<MelodySegment> fitsInLevel = options.stream().filter(s -> Math.abs(s.getValueRate() - preset.getNoteValues()) <= level1Margin).collect(Collectors.toList());
        List<MelodySegment> fitsInLeve2 = options.stream().filter(s -> Math.abs(s.getValueRate() - preset.getNoteValues()) > level1Margin && Math.abs(s.getValueRate() - preset.getNoteValues()) <= level2Margin).collect(Collectors.toList());
        List<MelodySegment> fitsInLeve3 = options.stream().filter(s -> Math.abs(s.getValueRate() - preset.getNoteValues()) > level2Margin && Math.abs(s.getValueRate() - preset.getNoteValues()) <= level3Margin).collect(Collectors.toList());
        List<MelodySegment> fitsInLeve4 = options.stream().filter(s -> Math.abs(s.getValueRate() - preset.getNoteValues()) > level3Margin).collect(Collectors.toList());

        if (!fitsInLevel.isEmpty()) {
            for (int i = 0; i < level1Chances; i++) {
                normalizedOptions.add(fitsInLevel.get(random.nextInt(fitsInLevel.size())));
            }
        }
        if (!fitsInLeve2.isEmpty()) {
            for (int i = 0; i < level2Chances; i++) {
                normalizedOptions.add(fitsInLeve2.get(random.nextInt(fitsInLeve2.size())));
            }
        }
        if (!fitsInLeve3.isEmpty()) {
            for (int i = 0; i < level3Chances; i++) {
                normalizedOptions.add(fitsInLeve3.get(random.nextInt(fitsInLeve3.size())));
            }
        }
        if (!fitsInLeve4.isEmpty()) {
            for (int i = 0; i < level4Chances; i++) {
                normalizedOptions.add(fitsInLeve4.get(random.nextInt(fitsInLeve4.size())));
            }
        }

        fitsInLevel = normalizedOptions.stream().filter(s -> Math.abs(s.getPauseRate() - preset.getPauseFrequency()) <= level1Margin).collect(Collectors.toList());
        fitsInLeve2 = normalizedOptions.stream().filter(s -> Math.abs(s.getPauseRate() - preset.getPauseFrequency()) > level1Margin && Math.abs(s.getPauseRate() - preset.getPauseFrequency()) <= level2Margin).collect(Collectors.toList());
        fitsInLeve3 = normalizedOptions.stream().filter(s -> Math.abs(s.getPauseRate() - preset.getPauseFrequency()) > level2Margin && Math.abs(s.getPauseRate() - preset.getPauseFrequency()) <= level3Margin).collect(Collectors.toList());
        fitsInLeve4 = normalizedOptions.stream().filter(s -> Math.abs(s.getPauseRate() - preset.getPauseFrequency()) > level3Margin).collect(Collectors.toList());
        normalizedOptions.clear();

        if (!fitsInLevel.isEmpty()) {
            for (int i = 0; i < level1Chances; i++) {
                normalizedOptions.add(fitsInLevel.get(random.nextInt(fitsInLevel.size())));
            }
        }
        if (!fitsInLeve2.isEmpty()) {
            for (int i = 0; i < level2Chances; i++) {
                normalizedOptions.add(fitsInLeve2.get(random.nextInt(fitsInLeve2.size())));
            }
        }
        if (!fitsInLeve3.isEmpty()) {
            for (int i = 0; i < level3Chances; i++) {
                normalizedOptions.add(fitsInLeve3.get(random.nextInt(fitsInLeve3.size())));
            }
        }
        if (!fitsInLeve4.isEmpty()) {
            for (int i = 0; i < level4Chances; i++) {
                normalizedOptions.add(fitsInLeve4.get(random.nextInt(fitsInLeve4.size())));
            }
        }

        return normalizedOptions.get(random.nextInt(normalizedOptions.size()));
    }
}
