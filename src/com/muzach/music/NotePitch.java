package com.muzach.music;

public enum NotePitch {
    C0, Cs0, D0, Ds0, E0, F0, Fs0, G0, Gs0, A0, As0, B0,
    C1, Cs1, D1, Ds1, E1, F1, Fs1, G1, Gs1, A1, As1, B1,
    C2, Cs2, D2, Ds2, E2, F2, Fs2, G2, Gs2, A2, As2, B2;

    public int getMidiNote(){
        return getMidiNote(48);
    }

    public int getMidiNote(int offset) {
        return this.ordinal() + offset;
    }
}
