package com.gympass.kartrace.service;

import com.gympass.kartrace.domain.KartRaceLog;
import com.gympass.kartrace.domain.Lap;
import com.gympass.kartrace.domain.Pilot;
import com.gympass.kartrace.dto.RaceResultsDTO;
import com.gympass.kartrace.repository.RaceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class RaceServiceTest {

    public static final String BRUCE_CODE = "123";
    public static final String BARRY_CODE = "456";
    RaceService raceService;

    RaceRepository raceRepository;

    List<KartRaceLog> repositoryResponse;

    @BeforeEach
    void setUp() {
        raceRepository = mock(RaceRepository.class);
        raceService = new RaceService(raceRepository);

        repositoryResponse = getRepositoryResponse();

        when(raceRepository.getRaceLogFromFile(eq("tst")))
                .thenReturn(repositoryResponse);
    }

    @Test
    @DisplayName("Should return ordered the podium.")
    void shouldReturnRaceResults() {
        List<RaceResultsDTO> raceResults = raceService.getRaceResults("tst");
        assertPodiumOrder(raceResults);
    }

    @Test
    @DisplayName("Should return ordered the podium one pilot has his name miss written.")
    void shouldReturnRaceResultsPilotWithWrongName() {
        Lap bruceLap2 = new Lap(2, 62.1f, 20f);
        Pilot bruceWrong = new Pilot(BRUCE_CODE, "Bruce.Wayn");
        repositoryResponse.set(1, new KartRaceLog(bruceWrong, bruceLap2));

        when(raceRepository.getRaceLogFromFile(eq("tst")))
                .thenReturn(repositoryResponse);

        List<RaceResultsDTO> raceResults = raceService.getRaceResults("tst");

        assertPodiumOrder(raceResults);
    }


    @Test
    @DisplayName("Should return in first place the pilot who finished the race.")
    void shouldReturnRaceResultsOnePilotHasLessLaps() {
        repositoryResponse.remove(3);

        when(raceRepository.getRaceLogFromFile(eq("tst")))
                .thenReturn(repositoryResponse);

        List<RaceResultsDTO> raceResults = raceService.getRaceResults("tst");

        assertPodiumWithPilotWithLessLaps(raceResults);
    }

    private static void assertPodiumWithPilotWithLessLaps(List<RaceResultsDTO> raceResults) {
        assertEquals(2, raceResults.size());

        assertEquals(1, raceResults.get(0).classification());
        assertEquals(127.4f, raceResults.get(0).totalRaceTime());
        assertEquals(2, raceResults.get(0).completedLaps());
        assertEquals(BRUCE_CODE, raceResults.get(0).pilotCode());
        assertEquals("Bruce Wayne", raceResults.get(0).pilotName());

        assertEquals(2, raceResults.get(1).classification());
        assertEquals(61.1f, raceResults.get(1).totalRaceTime());
        assertEquals(1, raceResults.get(1).completedLaps());
        assertEquals(BARRY_CODE, raceResults.get(1).pilotCode());
        assertEquals("Barry Alen", raceResults.get(1).pilotName());
    }

    private static void assertPodiumOrder(List<RaceResultsDTO> raceResults) {
        assertEquals(2, raceResults.size());

        assertEquals(1, raceResults.get(0).classification());
        assertEquals(125.2f, raceResults.get(0).totalRaceTime());
        assertEquals(2, raceResults.get(0).completedLaps());
        assertEquals(BARRY_CODE, raceResults.get(0).pilotCode());
        assertEquals("Barry Alen", raceResults.get(0).pilotName());

        assertEquals(2, raceResults.get(1).classification());
        assertEquals(127.4f, raceResults.get(1).totalRaceTime());
        assertEquals(2, raceResults.get(1).completedLaps());
        assertEquals(BRUCE_CODE, raceResults.get(1).pilotCode());
        assertEquals("Bruce Wayne", raceResults.get(1).pilotName());
    }

    private static ArrayList<KartRaceLog> getRepositoryResponse() {
        ArrayList<KartRaceLog> list = new ArrayList<>();
        Pilot bruce = new Pilot(BRUCE_CODE, "Bruce Wayne");
        Lap bruceLap1 = new Lap(1, 65.3f, 20f);
        Lap bruceLap2 = new Lap(2, 62.1f, 20f);
        list.add(new KartRaceLog(bruce, bruceLap1));
        list.add(new KartRaceLog(bruce, bruceLap2));

        Pilot barry = new Pilot(BARRY_CODE, "Barry Alen");
        Lap barryLap1 = new Lap(1, 61.1f, 20f);
        Lap barryLap2 = new Lap(2, 64.1f, 20f);
        list.add(new KartRaceLog(barry, barryLap1));
        list.add(new KartRaceLog(barry, barryLap2));

        return list;
    }

}