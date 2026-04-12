package com.recruitassist.model;

import java.util.Arrays;
import java.util.Optional;

public enum ApplicationStatus {
    SUBMITTED,
    ACCEPTED,
    REJECTED;

    public static Optional<ApplicationStatus> from(String rawValue) {
        if (rawValue == null || rawValue.isBlank()) {
            return Optional.empty();
        }
        return Arrays.stream(values())
                .filter(value -> value.name().equalsIgnoreCase(rawValue.trim()))
                .findFirst();
    }

    public String getLabel() {
        return switch (this) {
            case SUBMITTED -> "Pending";
            case ACCEPTED -> "Accepted";
            case REJECTED -> "Rejected";
        };
    }

    public String getCssClass() {
        return name().toLowerCase();
    }

    public String getCode() {
        return name();
    }
}
