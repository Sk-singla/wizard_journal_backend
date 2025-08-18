package com.samarth.wizardjournal.wizardjournal.enums;


import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.STRING)
 public enum CustomColors {
    ROYAL_BLUE(0xFF4169E1),
    CORAL(0xFFFF7F50),
    SUNSET_ORANGE(0xFFFF5E13),
    TURQUOISE(0xFF40E0D0),
    MAUVE(0xFFE0B0FF),
    GOLDEN_YELLOW(0xFFFFD700),
    TEAL(0xFF008080),
    LAVENDER(0xFFB57EDC),
    BLUSH_PINK(0xFFFFB6C1),
    EMERALD_GREEN(0xFF50C878);

    private final long colorCode;

    CustomColors(long colorCode) {
        this.colorCode = colorCode;
    }

    public long getColorCode() {
        return colorCode;
    }
}
