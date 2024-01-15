package com.gympass.kartrace.service;

import com.gympass.kartrace.domain.KartRaceLog;
import com.gympass.kartrace.domain.Lap;
import com.gympass.kartrace.domain.Pilot;
import com.gympass.kartrace.dto.RaceResultsDTO;
import com.gympass.kartrace.repository.RaceRepository;

import java.util.Comparator;
import java.util.List;
import java.util.stream.IntStream;

import static java.util.Collections.reverseOrder;
import static java.util.stream.Collectors.groupingBy;

public class RaceService {
    private final RaceRepository raceRepository;

    public RaceService(RaceRepository raceRepository) {
        this.raceRepository = raceRepository;
    }

    public List<RaceResultsDTO> getRaceResults(String logPath) {
        var pilotLaps = raceRepository.getRaceLogFromFile(logPath).stream()
                .collect(groupingBy(KartRaceLog::pilot));

        var raceResultsOrdered = pilotLaps.entrySet().stream()
                .map(it -> mapToResultsDTO(it.getKey(), it.getValue()))
                .sorted(Comparator.comparing(RaceResultsDTO::completedLaps).reversed()
                        .thenComparing(RaceResultsDTO::totalRaceTime))
                .toList();

        return IntStream.range(0, raceResultsOrdered.size())
                .mapToObj(i -> raceResultsOrdered.get(i).withClassification(i + 1))
                .toList();
    }

    private RaceResultsDTO mapToResultsDTO(Pilot pilot, List<KartRaceLog> kartRaceLog) {
        var laps = kartRaceLog.stream().map(KartRaceLog::lap).toList();
        Float totalTime = laps.stream().map(Lap::timeSeconds).reduce(0f, Float::sum);;

        return new RaceResultsDTO(pilot.code(), pilot.name(), kartRaceLog.size(), totalTime);
    }
}
