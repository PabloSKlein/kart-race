package com.gympass.kartrace.dto;

public record RaceResultsDTO(int classification, String pilotCode, String pilotName, int completedLaps,
                             Float totalRaceTime) {
    public RaceResultsDTO(String pilotCode, String pilotName, int completedLaps, Float totalRaceTime) {
        this(0, pilotCode, pilotName, completedLaps, totalRaceTime);
    }

    public RaceResultsDTO withClassification(int classification) {
        return new RaceResultsDTO(classification, pilotCode, pilotName, completedLaps, totalRaceTime);
    }

    @Override
    public String toString() {
        return classification + ";" + pilotCode + ";" + pilotName + ";" + completedLaps + ";" + totalRaceTime;
    }
}
