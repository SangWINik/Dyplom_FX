package com.muzach.utils.populators;

import com.muzach.music.NotePitch;
import com.muzach.music.Scale;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Scales {
    private static List<Scale> scales;

    static {
        scales = new ArrayList<>();

        Scale scale = new Scale("Major Scale", Arrays.asList(
                NotePitch.C0, NotePitch.D0, NotePitch.E0, NotePitch.F0, NotePitch.G0, NotePitch.A0, NotePitch.B0,
                NotePitch.C1, NotePitch.D1, NotePitch.E1, NotePitch.F1, NotePitch.G1, NotePitch.A1, NotePitch.B1,
                NotePitch.C2, NotePitch.D2, NotePitch.E2, NotePitch.F2, NotePitch.G2, NotePitch.A2, NotePitch.B2
        ), Arrays.asList(
                NotePitch.C0, NotePitch.F0, NotePitch.G0,
                NotePitch.C1, NotePitch.F1, NotePitch.G1,
                NotePitch.C2, NotePitch.F2, NotePitch.G2
        ));
        scales.add(scale);

        scale = new Scale("Minor Scale", Arrays.asList(
                NotePitch.C0, NotePitch.D0, NotePitch.Ds0, NotePitch.F0, NotePitch.G0, NotePitch.Gs0, NotePitch.As0,
                NotePitch.C1, NotePitch.D1, NotePitch.Ds1, NotePitch.F1, NotePitch.G1, NotePitch.Gs1, NotePitch.As1,
                NotePitch.C2, NotePitch.D2, NotePitch.Ds2, NotePitch.F2, NotePitch.G2, NotePitch.Gs2, NotePitch.As2
        ), Arrays.asList(
                NotePitch.C0, NotePitch.F0, NotePitch.G0,
                NotePitch.C1, NotePitch.F1, NotePitch.G1,
                NotePitch.C2, NotePitch.F2, NotePitch.G2
        ));
        scales.add(scale);

        scale = new Scale("Harmonic Minor Scale", Arrays.asList(
                NotePitch.C0, NotePitch.D0, NotePitch.Ds0, NotePitch.F0, NotePitch.G0, NotePitch.Gs0, NotePitch.B0,
                NotePitch.C1, NotePitch.D1, NotePitch.Ds1, NotePitch.F1, NotePitch.G1, NotePitch.Gs1, NotePitch.B1,
                NotePitch.C2, NotePitch.D2, NotePitch.Ds2, NotePitch.F2, NotePitch.G2, NotePitch.Gs2, NotePitch.B2
        ), Arrays.asList(
                NotePitch.C0, NotePitch.F0, NotePitch.G0,
                NotePitch.C1, NotePitch.F1, NotePitch.G1,
                NotePitch.C2, NotePitch.F2, NotePitch.G2
        ));
        scales.add(scale);

        scale = new Scale("Dorian Mode", Arrays.asList(
                NotePitch.C0, NotePitch.D0, NotePitch.Ds0, NotePitch.F0, NotePitch.G0, NotePitch.A0, NotePitch.As0,
                NotePitch.C1, NotePitch.D1, NotePitch.Ds1, NotePitch.F1, NotePitch.G1, NotePitch.A1, NotePitch.As1,
                NotePitch.C2, NotePitch.D2, NotePitch.Ds2, NotePitch.F2, NotePitch.G2, NotePitch.A2, NotePitch.As2
        ), Arrays.asList(
                NotePitch.C0, NotePitch.F0, NotePitch.G0,
                NotePitch.C1, NotePitch.F1, NotePitch.G1,
                NotePitch.C2, NotePitch.F2, NotePitch.G2
        ));
        scales.add(scale);

        scale = new Scale("Phrygian Mode", Arrays.asList(
                NotePitch.C0, NotePitch.Cs0, NotePitch.Ds0, NotePitch.F0, NotePitch.G0, NotePitch.Gs0, NotePitch.As0,
                NotePitch.C1, NotePitch.Cs1, NotePitch.Ds1, NotePitch.F1, NotePitch.G1, NotePitch.Gs1, NotePitch.As1,
                NotePitch.C2, NotePitch.Cs2, NotePitch.Ds2, NotePitch.F2, NotePitch.G2, NotePitch.Gs2, NotePitch.As2
        ), Arrays.asList(
                NotePitch.C0, NotePitch.F0, NotePitch.G0,
                NotePitch.C1, NotePitch.F1, NotePitch.G1,
                NotePitch.C2, NotePitch.F2, NotePitch.G2
        ));
        scales.add(scale);

        scale = new Scale("Lydian Mode", Arrays.asList(
                NotePitch.C0, NotePitch.D0, NotePitch.E0, NotePitch.Fs0, NotePitch.G0, NotePitch.A0, NotePitch.B0,
                NotePitch.C1, NotePitch.D1, NotePitch.E1, NotePitch.Fs1, NotePitch.G1, NotePitch.A1, NotePitch.B1,
                NotePitch.C2, NotePitch.D2, NotePitch.E2, NotePitch.Fs2, NotePitch.G2, NotePitch.A2, NotePitch.B2
        ), Arrays.asList(
                NotePitch.C0, NotePitch.Fs0, NotePitch.G0,
                NotePitch.C1, NotePitch.Fs1, NotePitch.G1,
                NotePitch.C2, NotePitch.Fs2, NotePitch.G2
        ));
        scales.add(scale);

        scale = new Scale("Mixolydian Mode", Arrays.asList(
                NotePitch.C0, NotePitch.D0, NotePitch.E0, NotePitch.F0, NotePitch.G0, NotePitch.A0, NotePitch.As0,
                NotePitch.C1, NotePitch.D1, NotePitch.E1, NotePitch.F1, NotePitch.G1, NotePitch.A1, NotePitch.As1,
                NotePitch.C2, NotePitch.D2, NotePitch.E2, NotePitch.F2, NotePitch.G2, NotePitch.A2, NotePitch.As2
        ), Arrays.asList(
                NotePitch.C0, NotePitch.F0, NotePitch.G0,
                NotePitch.C1, NotePitch.F1, NotePitch.G1,
                NotePitch.C2, NotePitch.F2, NotePitch.G2
        ));
        scales.add(scale);

        scale = new Scale("Locrian Mode", Arrays.asList(
                NotePitch.C0, NotePitch.Cs0, NotePitch.Ds0, NotePitch.F0, NotePitch.Fs0, NotePitch.Gs0, NotePitch.As0,
                NotePitch.C1, NotePitch.Cs1, NotePitch.Ds1, NotePitch.F1, NotePitch.Fs1, NotePitch.Gs1, NotePitch.As1,
                NotePitch.C2, NotePitch.Cs2, NotePitch.Ds2, NotePitch.F2, NotePitch.Fs2, NotePitch.Gs2, NotePitch.As2
        ), Arrays.asList(
                NotePitch.C0, NotePitch.F0, NotePitch.Fs0,
                NotePitch.C1, NotePitch.F1, NotePitch.Fs1,
                NotePitch.C2, NotePitch.F2, NotePitch.Fs2
        ));
        scales.add(scale);
    }

    public static List<Scale> getScales() {
        return scales;
    }

    public static Scale getByName(String name) {
        return scales.stream().filter(s -> s.getName().equals(name)).findFirst().get();
    }
}
