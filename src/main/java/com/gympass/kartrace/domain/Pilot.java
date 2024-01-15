package com.gympass.kartrace.domain;

import java.util.Objects;

public record Pilot(String code, String name) {

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pilot pilot = (Pilot) o;
        return Objects.equals(code, pilot.code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code);
    }
}
