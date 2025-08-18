package com.samarth.wizardjournal.wizardjournal.enums;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.STRING)
public enum BackgroundType {
    SOLID_COLOR,
    LINEAR_GRADIENT,
    RADIAL_GRADIENT,
}
