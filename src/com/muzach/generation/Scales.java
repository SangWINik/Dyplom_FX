package com.muzach.generation;

import com.muzach.music.Note;
import com.muzach.music.NotePitch;
import com.muzach.music.Scale;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Scales {
    private static List<Scale> scales;

    static {
        scales = new ArrayList<>();

        Scale scale = new Scale("Major", Arrays.asList(
                NotePitch.C0, NotePitch.D0, NotePitch.E0, NotePitch.F0, NotePitch.G0, NotePitch.A0, NotePitch.B0,
                NotePitch.C1, NotePitch.D1, NotePitch.E1, NotePitch.F1, NotePitch.G1, NotePitch.A1, NotePitch.B1,
                NotePitch.C2, NotePitch.D2, NotePitch.E2, NotePitch.F2, NotePitch.G2, NotePitch.A2, NotePitch.B2
        ), Arrays.asList(
                NotePitch.C0, NotePitch.E0, NotePitch.F0, NotePitch.G0,
                NotePitch.C1, NotePitch.E1, NotePitch.F2, NotePitch.G1,
                NotePitch.C2, NotePitch.E2, NotePitch.F2, NotePitch.G2
        ));
        scales.add(scale);

        scale = new Scale("Minor", Arrays.asList(
                NotePitch.C0, NotePitch.D0, NotePitch.Ds0, NotePitch.F0, NotePitch.G0, NotePitch.Gs0, NotePitch.As0,
                NotePitch.C1, NotePitch.D1, NotePitch.Ds1, NotePitch.F1, NotePitch.G1, NotePitch.Gs1, NotePitch.As1,
                NotePitch.C2, NotePitch.D2, NotePitch.Ds2, NotePitch.F2, NotePitch.G2, NotePitch.Gs2, NotePitch.As2
        ), Arrays.asList(
                NotePitch.C0, NotePitch.Ds0, NotePitch.F0, NotePitch.G0,
                NotePitch.C1, NotePitch.Ds1, NotePitch.F1, NotePitch.G1,
                NotePitch.C2, NotePitch.Ds2, NotePitch.F2, NotePitch.G2
        ));
        scales.add(scale);
    }

    public static List<Scale> getScales() {
        return scales;
    }
}
