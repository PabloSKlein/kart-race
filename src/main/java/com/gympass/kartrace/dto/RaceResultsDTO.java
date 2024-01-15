package com.gympass.kartrace.dto;

public record RaceResultsDTO(int classification, String pilotCode, String pilotName, int completedLaps,
                             Double totalRaceTime) {

    @Override
    public String toString() {
        return classification + ";" + pilotCode + ";" + pilotName + ";" + completedLaps + ";" + totalRaceTime;
    }
}
