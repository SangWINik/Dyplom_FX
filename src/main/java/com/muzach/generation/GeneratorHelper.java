package com.muzach.generation;

import com.muzach.music.NoteDuration;
import com.muzach.music.NoteLocation;
import com.muzach.music.Note;
import com.muzach.music.NotePitch;
import com.muzach.music.Scale;
import com.muzach.preset.Preset;
import com.muzach.utils.populators.GenerationOdds;
import com.muzach.utils.populators.MelodySegments;

import java.util.*;
import java.util.stream.Collectors;

public class GeneratorHelper implements IGeneratorHelper {
    private Random random;

    public GeneratorHelper() {
        random = new Random();
    }

    @Override
    public void populateMeta(Composition composition, Preset preset) {
        if (preset.isDefault()) {
            composition.setTempoBMP(random.nextInt(preset.getMaxTempo() - preset.getMinTempo()) + preset.getMinTempo());
        } else {
            composition.setTempoBMP(80);
        }
        composition.setToneOffset(random.nextInt(53 - 42) + 42);
    }

    @Override
    public void populateMelodyRhythm(Track melodyTrack, Preset preset, int measureCount) {
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

        List<Integer> odds = GenerationOdds.getMelodyPitchOdds(pitchJumps);
        List<Integer> margins = GenerationOdds.getMelodyPitchMargins();

        List<NotePitch> options = preset.getScale().getNotePitches();
        List<NotePitch> optionsCorrected = new ArrayList<>();

        melodyTrack.getNotes().sort(Comparator.comparingInt(n -> n.getLocation().getTSPosition()));

        Note note0 = melodyTrack.getNotes().get(0);
        note0.setPitch(NotePitch.C1);
        for (int i = 1; i < melodyTrack.getNotes().size(); i++){
            int previousNotePitch = note0.getPitch().ordinal();
            List<NotePitch> fitsInLevel1 = options.stream().filter(n -> Math.abs(n.ordinal() - previousNotePitch) <= margins.get(0)).collect(Collectors.toList());
            List<NotePitch> fitsInLevel2 = options.stream().filter(n -> Math.abs(n.ordinal() - previousNotePitch) > margins.get(0) && Math.abs(n.ordinal() - previousNotePitch) <= margins.get(1)).collect(Collectors.toList());
            List<NotePitch> fitsInLevel3 = options.stream().filter(n -> Math.abs(n.ordinal() - previousNotePitch) > margins.get(1) && Math.abs(n.ordinal() - previousNotePitch) <= margins.get(2)).collect(Collectors.toList());
            List<NotePitch> fitsInLevel4 = options.stream().filter(n -> Math.abs(n.ordinal() - previousNotePitch) >= margins.get(2)).collect(Collectors.toList());

            optionsCorrected.clear();

            addWithOdds(optionsCorrected, fitsInLevel1, odds.get(0));
            addWithOdds(optionsCorrected, fitsInLevel2, odds.get(1));
            addWithOdds(optionsCorrected, fitsInLevel3, odds.get(2));
            addWithOdds(optionsCorrected, fitsInLevel4, odds.get(3));

            melodyTrack.getNotes().get(i).setPitch(optionsCorrected.get(random.nextInt(optionsCorrected.size())));

            note0 = melodyTrack.getNotes().get(i);
        }
    }

    @Override
    public void populateHarmonyRhythm(Track harmonyTrack, Preset preset, int measureCount) {
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
                boolean[] chOptions = new boolean[100];
                for (int j = 0; j < preset.getChordChangeFrequency(); j++){
                    chOptions[j] = true;
                }
                changeChord = chOptions[random.nextInt(chOptions.length)];
            }
            if (changeChord){
                boolean color;
                boolean[] cOptions = new boolean[100];
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
        List<Integer> odds = GenerationOdds.getMelodySegmentOdds();
        List<Integer> margins = GenerationOdds.getMelodySegmentMargins();

        List<MelodySegment> options = MelodySegments.get(isOnFirstBeat, halfAllowed);
        List<MelodySegment> normalizedOptions = new ArrayList<>();

        List<MelodySegment> fitsInLevel = options.stream().filter(s -> Math.abs(s.getValueRate() - preset.getNoteValues()) <= margins.get(0)).collect(Collectors.toList());
        List<MelodySegment> fitsInLeve2 = options.stream().filter(s -> Math.abs(s.getValueRate() - preset.getNoteValues()) > margins.get(0) && Math.abs(s.getValueRate() - preset.getNoteValues()) <= margins.get(1)).collect(Collectors.toList());
        List<MelodySegment> fitsInLeve3 = options.stream().filter(s -> Math.abs(s.getValueRate() - preset.getNoteValues()) > margins.get(1) && Math.abs(s.getValueRate() - preset.getNoteValues()) <= margins.get(2)).collect(Collectors.toList());
        List<MelodySegment> fitsInLeve4 = options.stream().filter(s -> Math.abs(s.getValueRate() - preset.getNoteValues()) > margins.get(2)).collect(Collectors.toList());

        addWithOdds(normalizedOptions, fitsInLevel, odds.get(0));
        addWithOdds(normalizedOptions, fitsInLeve2, odds.get(1));
        addWithOdds(normalizedOptions, fitsInLeve3, odds.get(2));
        addWithOdds(normalizedOptions, fitsInLeve4, odds.get(3));

        fitsInLevel = normalizedOptions.stream().filter(s -> Math.abs(s.getPauseRate() - preset.getPauseFrequency()) <= margins.get(0)).collect(Collectors.toList());
        fitsInLeve2 = normalizedOptions.stream().filter(s -> Math.abs(s.getPauseRate() - preset.getPauseFrequency()) > margins.get(0) && Math.abs(s.getPauseRate() - preset.getPauseFrequency()) <= margins.get(1)).collect(Collectors.toList());
        fitsInLeve3 = normalizedOptions.stream().filter(s -> Math.abs(s.getPauseRate() - preset.getPauseFrequency()) > margins.get(1) && Math.abs(s.getPauseRate() - preset.getPauseFrequency()) <= margins.get(2)).collect(Collectors.toList());
        fitsInLeve4 = normalizedOptions.stream().filter(s -> Math.abs(s.getPauseRate() - preset.getPauseFrequency()) > margins.get(2)).collect(Collectors.toList());
        normalizedOptions.clear();

        addWithOdds(normalizedOptions, fitsInLevel, odds.get(0));
        addWithOdds(normalizedOptions, fitsInLeve2, odds.get(1));
        addWithOdds(normalizedOptions, fitsInLeve3, odds.get(2));
        addWithOdds(normalizedOptions, fitsInLeve4, odds.get(3));

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

    private <T> void addWithOdds(List<T> destination, List<T> source, int odds) {
        if (!source.isEmpty()) {
            for (int i = 0; i < odds; i++) {
                destination.add(source.get(random.nextInt(source.size())));
            }
        }
    }
}
