package com.gympass.kartrace.service;

import com.gympass.kartrace.domain.KartRaceLog;
import com.gympass.kartrace.domain.Lap;
import com.gympass.kartrace.domain.Pilot;
import com.gympass.kartrace.dto.RaceResultsDTO;
import com.gympass.kartrace.repository.RacesRepository;

import java.util.Comparator;
import java.util.List;

import static java.util.stream.Collectors.groupingBy;

public class RaceService {
    private final RacesRepository racesRepository;

    public RaceService(RacesRepository racesRepository) {
        this.racesRepository = racesRepository;
    }

    public List<RaceResultsDTO> getRaceResults(String logPath) {
        var pilotLaps = racesRepository.getRaceFromFile(logPath).stream()
                .collect(groupingBy(KartRaceLog::pilot));

        return pilotLaps.entrySet().stream()
                .map(it -> mapToResultsDTO(it.getKey(), it.getValue()))
                .sorted(Comparator.comparing(RaceResultsDTO::totalRaceTime))
                .toList();
    }

    private RaceResultsDTO mapToResultsDTO(Pilot pilot, List<KartRaceLog> kartRaceLog) {
        var laps = kartRaceLog.stream().map(KartRaceLog::lap).toList();
        double totalTime = laps.stream().mapToDouble(Lap::timeSeconds).sum();

        return new RaceResultsDTO(1, pilot.code(), pilot.name(), kartRaceLog.size(), totalTime);
    }
}
