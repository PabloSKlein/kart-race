package com.gympass.kartrace;

import com.gympass.kartrace.commons.RaceLogFileReader;
import com.gympass.kartrace.repository.RaceRepository;
import com.gympass.kartrace.service.RaceService;

public class Main {
    public static void main(String[] args) {
        RaceLogFileReader logFileReader = new RaceLogFileReader();
        RaceRepository raceRepository = new RaceRepository(logFileReader);
        RaceService raceService = new RaceService(raceRepository);

        raceService.getRaceResults("C:\\projects\\kart-race\\src\\main\\resources\\logfile.txt")
                .forEach(System.out::println);
    }
}
