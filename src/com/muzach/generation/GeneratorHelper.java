package com.muzach.generation;

import com.muzach.music.NoteDuration;
import com.muzach.music.NoteLocation;
import com.muzach.music.Note;
import com.muzach.music.NotePitch;
import com.muzach.music.Scale;
import com.muzach.preset.Preset;
import com.muzach.utils.populators.MelodySegments;

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
        double pitchJumps = preset.getPitchJumps();

        int level1Chances = 1;
        int level2Chances = 1;
        int level3Chances = 1;
        int level4Chances = 1;
        int level1Margin = 2;
        int level2Margin = 6;
        int level3Margin = 9;

        if (pitchJumps < 25){
            level1Chances = 100;
            level2Chances = 30;
            level3Chances = 5;
            level4Chances = 1;
        } else if (pitchJumps < 50) {
            level1Chances = 90;
            level2Chances = 30;
            level3Chances = 10;
            level4Chances = 2;
        } else if (pitchJumps < 75) {
            level1Chances = 80;
            level2Chances = 30;
            level3Chances = 15;
            level4Chances = 5;
        } else {
            level1Chances = 10;
            level2Chances = 10;
            level3Chances = 30;
            level4Chances = 15;
        }

        List<NotePitch> options = preset.getScale().getNotePitches();
        List<NotePitch> optionsCorrected = new ArrayList<>();

        melodyTrack.getNotes().sort(Comparator.comparingInt(n -> n.getLocation().getTSPosition()));

        Note note0 = melodyTrack.getNotes().get(0);
        note0.setPitch(NotePitch.C1);
        for (int i = 1; i < melodyTrack.getNotes().size(); i++){
            int previousNotePitch = note0.getPitch().ordinal();
            List<NotePitch> fitsInLevel1 = options.stream().filter(n -> Math.abs(n.ordinal() - previousNotePitch) <= level1Margin).collect(Collectors.toList());
            List<NotePitch> fitsInLevel2 = options.stream().filter(n -> Math.abs(n.ordinal() - previousNotePitch) > level1Margin && Math.abs(n.ordinal() - previousNotePitch) <= level2Margin).collect(Collectors.toList());
            List<NotePitch> fitsInLevel3 = options.stream().filter(n -> Math.abs(n.ordinal() - previousNotePitch) > level2Margin && Math.abs(n.ordinal() - previousNotePitch) <= level3Margin).collect(Collectors.toList());
            List<NotePitch> fitsInLevel4 = options.stream().filter(n -> Math.abs(n.ordinal() - previousNotePitch) >= level3Margin).collect(Collectors.toList());

            optionsCorrected.clear();

            if (!fitsInLevel1.isEmpty()) {
                for (int j = 0; j < level1Chances; j++) {
                    optionsCorrected.add(fitsInLevel1.get(random.nextInt(fitsInLevel1.size())));
                }
            }
            if (!fitsInLevel2.isEmpty()) {
                for (int j = 0; j < level2Chances; j++) {
                    optionsCorrected.add(fitsInLevel2.get(random.nextInt(fitsInLevel2.size())));
                }
            }
            if (!fitsInLevel3.isEmpty()) {
                for (int j = 0; j < level3Chances; j++) {
                    optionsCorrected.add(fitsInLevel3.get(random.nextInt(fitsInLevel3.size())));
                }
            }
            if (!fitsInLevel4.isEmpty()) {
                for (int j = 0; j < level4Chances; j++) {
                    optionsCorrected.add(fitsInLevel4.get(random.nextInt(fitsInLevel4.size())));
                }
            }

            melodyTrack.getNotes().get(i).setPitch(optionsCorrected.get(random.nextInt(optionsCorrected.size())));

            note0 = melodyTrack.getNotes().get(i);
        }
    }

    @Override
    public void populateHarmonyRythm(Track harmonyTrack, Preset preset, int measureCount) {
        int quarterBeatsPerMeasure = preset.getTimeSignature().numinator;
        boolean halfAllowed;
        for (int i = 0; i < measureCount; i++) {
            int currentBeat = 1;
            while (currentBeat < quarterBeatsPerMeasure + 1) {
                halfAllowed = quarterBeatsPerMeasure - currentBeat > 0;
                if (halfAllowed){
                    harmonyTrack.addNote(new Note(NotePitch.C1, NoteDuration.Duration.HALF, NoteLocation.getNoteLocation(i + 1, currentBeat, NoteDuration.Duration.QUARTER), 50));
                    currentBeat += 2;
                } else {
                    harmonyTrack.addNote(new Note(NotePitch.C1, NoteDuration.Duration.QUARTER, NoteLocation.getNoteLocation(i + 1, currentBeat, NoteDuration.Duration.QUARTER), 50));
                    currentBeat += 1;
                }
            }
        }
    }

    @Override
    public void populateHarmonyChords(Track harmonyTrack, Preset preset) {
        int chordsVelocity = 80;
        int keyNotesChance = 2;

        double colorRate = preset.getChordColor();
        List<NotePitch> keyNotesOptimazedScale = new ArrayList<>();
        keyNotesOptimazedScale.addAll(preset.getScale().getNotePitches());
        for (int i = 0; i < keyNotesChance; i++) {
            keyNotesOptimazedScale.addAll(preset.getScale().getKeyNotes());
        }
        List<NotePitch> options = keyNotesOptimazedScale.stream().filter(p -> p.ordinal() >= NotePitch.C1.ordinal() && p.ordinal() < NotePitch.C2.ordinal()).collect(Collectors.toList());
        List<Note> template = new ArrayList<>();
        template.addAll(harmonyTrack.getNotes());
        List<NotePitch> currentChord = generateChord(preset.getScale(), NotePitch.C1, false);
        for (NotePitch np: currentChord) {
            Note note = new Note(np, template.get(0).getDuration(), template.get(0).getLocation(), chordsVelocity);
            harmonyTrack.addNote(note);
        }
        harmonyTrack.getNotes().remove(template.get(0));
        for (int i = 1; i<template.size(); i++) {
            boolean changeChord;
            if (NoteLocation.isOnFirstBeat(template.get(i).getLocation())) {
                changeChord = true;
            } else {
                boolean chOptions[] = new boolean[100];
                for (int j = 0; j < preset.getChordChangeFrequency(); j++){
                    chOptions[j] = true;
                }
                changeChord = chOptions[random.nextInt(chOptions.length)];
            }
            if (changeChord){
                boolean color;
                boolean cOptions[] = new boolean[100];
                for (int j = 0; j < preset.getChordColor(); j++){
                    cOptions[j] = true;
                }
                color = cOptions[random.nextInt(cOptions.length)];
                currentChord = generateChord(preset.getScale(), options.get(random.nextInt(options.size())), color);
            }
            for (NotePitch np: currentChord) {
                Note note = new Note(np, template.get(i).getDuration(), template.get(i).getLocation(), chordsVelocity);
                harmonyTrack.addNote(note);
            }
            harmonyTrack.getNotes().remove(template.get(i));
        }
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

    private List<NotePitch> generateChord(Scale scale, NotePitch root, boolean addColor) {
        List<NotePitch> chord = new ArrayList<>();
        scale.getNotePitches().sort(Comparator.comparingInt(Enum::ordinal));
        if (root.ordinal() > NotePitch.F1.ordinal()) {
            root = NotePitch.values()[root.ordinal() - 12];
        }
        List<NotePitch> colorOptions = Arrays.asList(scale.getNotePitches().get(scale.getNotePitches().indexOf(root) + 5), scale.getNotePitches().get(scale.getNotePitches().indexOf(root) + 6), scale.getNotePitches().get(scale.getNotePitches().indexOf(root) + 8), scale.getNotePitches().get(scale.getNotePitches().indexOf(root) + 10));
        chord.add(root);
        chord.add(scale.getNotePitches().get(scale.getNotePitches().indexOf(root) + 2));
        chord.add(scale.getNotePitches().get(scale.getNotePitches().indexOf(root) + 4));
        if (addColor) {
            chord.add(colorOptions.get(random.nextInt(colorOptions.size())));
        }
        return chord;
    }
}
