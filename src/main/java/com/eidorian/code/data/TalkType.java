package com.eidorian.code.data;

/**
 * Different talk types and their length in <code>minutes</code>.
 */
public enum TalkType {
    WORKSHOP(60),
    CHALK(60),
    SESSION(30),
    LIGHTNING(10),
    KEYNOTE(30),
    CLOSING(30),
    LUNCH(60),
    TEA(15);

    private int minutes;

    TalkType(int minutes) {
        this.minutes = minutes;
    }

    public int getMinutes() {
        return minutes;
    }
}
