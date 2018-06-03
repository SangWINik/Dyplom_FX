package com.muzach.utils.populators;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GenerationOdds {

    public static List<Integer> getMelodySegmentOdds() {
        return Arrays.asList(100, 50, 20, 2);
    }

    public static List<Integer> getMelodySegmentMargins() {
        return Arrays.asList(20, 40, 60);
    }

    public static List<Integer> getMelodyPitchOdds(double pitchJumps) {
        List<Integer> odds;
        if (pitchJumps < 25){
            odds = Arrays.asList(100, 30, 5, 1);
        } else if (pitchJumps < 50) {
            odds = Arrays.asList(90, 40, 10, 2);
        } else if (pitchJumps < 75) {
            odds = Arrays.asList(80, 50, 15, 5);
        } else {
            odds = Arrays.asList(10, 10, 30, 15);
        }
        return odds;
    }

    public static List<Integer> getMelodyPitchMargins() {
        return Arrays.asList(2, 6, 9);
    }
}
