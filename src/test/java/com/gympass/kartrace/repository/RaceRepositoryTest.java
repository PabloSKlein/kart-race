package com.gympass.kartrace.repository;

import com.gympass.kartrace.commons.RaceLogFileReader;
import com.gympass.kartrace.domain.KartRaceLog;
import com.gympass.kartrace.exception.FileNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class RaceRepositoryTest {
    RaceLogFileReader raceLogFileReader;
    RaceRepository raceRepository;

    @BeforeEach
    void setUp() {
        raceLogFileReader = new RaceLogFileReader();
        raceRepository = new RaceRepository(raceLogFileReader);
    }

    @Test
    void shouldReturnFileMapped() {
        List<KartRaceLog> raceLogFromFile = raceRepository.getRaceLogFromFile("src/test/resources/logfile-ok.txt");

        assertEquals(2, raceLogFromFile.size());
        assertEquals("038", raceLogFromFile.get(0).pilot().code());
        assertEquals("F.MASSA", raceLogFromFile.get(0).pilot().name());
        assertEquals(62.852f, raceLogFromFile.get(0).lap().timeSeconds());
        assertEquals(44.275f, raceLogFromFile.get(0).lap().averageVelocity());
    }

    @Test
    void shouldIgnoreWrongFormatLines() {
        List<KartRaceLog> raceLogFromFile = raceRepository.getRaceLogFromFile("src/test/resources/logfile-wrong.txt");

        assertEquals(1, raceLogFromFile.size());
        assertEquals("033", raceLogFromFile.get(0).pilot().code());
        assertEquals("R.BARRICHELLO", raceLogFromFile.get(0).pilot().name());
        assertEquals(64.352f, raceLogFromFile.get(0).lap().timeSeconds());
        assertEquals(43.243f, raceLogFromFile.get(0).lap().averageVelocity());
    }

    @Test
    void shouldThrowFileNotFound() {
        var fileNotFoundException = assertThrows(FileNotFoundException.class,
                () -> raceRepository.getRaceLogFromFile("src/test/resources/logfile-notexist.txt"));
        assertEquals("File not found.", fileNotFoundException.getMessage());
    }

}