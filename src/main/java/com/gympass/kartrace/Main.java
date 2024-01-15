package com.gympass.kartrace;

import com.gympass.kartrace.commons.LogFileReader;
import com.gympass.kartrace.repository.RacesRepository;
import com.gympass.kartrace.service.RaceService;

public class Main {
    public static void main(String[] args) {
        LogFileReader logFileReader = new LogFileReader();
        RacesRepository raceRepository = new RacesRepository(logFileReader);
        RaceService raceService = new RaceService(raceRepository);

        raceService.getRaceResults("C:\\projects\\kart-race\\src\\main\\resources\\logfile.txt").forEach(System.out::println);
    }
}
