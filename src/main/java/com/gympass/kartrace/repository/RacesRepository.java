package com.gympass.kartrace.repository;

import com.gympass.kartrace.domain.KartRaceLog;
import com.gympass.kartrace.domain.Lap;
import com.gympass.kartrace.domain.Pilot;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static com.gympass.kartrace.domain.LogFileColumnsEnum.*;
import static java.lang.Double.parseDouble;
import static java.lang.Integer.parseInt;

public class RacesRepository {
    public List<KartRaceLog> getRaceFromFile(String filePath) {
        return getLines(filePath).stream()
                    //Skip Header
                    .skip(1)
                    .map(line -> line.split("\\s+"))
                    .map(this::mapToObj)
                    .toList();
    }

    private List<String> getLines(String filePath) {
        Path path = Paths.get(filePath);
        try {
          return Files.readAllLines(path);
        } catch (IOException e) {
            throw new RuntimeException("Fail to open file.");
        }
    }

    private KartRaceLog mapToObj(String[] columns) {
        Lap lap = new Lap(parseInt(columns[LAP_NUM.getColumn()]),
                parseToSeconds(columns[LAP_TIME.getColumn()]),
                parseToDouble(columns[LAP_AVERAGE_TIME.getColumn()]));

        Pilot pilot = new Pilot(
                columns[PILOT_NUMBER.getColumn()],
                columns[PILOT_NAME.getColumn()]);

        return new KartRaceLog(pilot, lap);
    }

    private Double parseToSeconds(String column) {
        String[] split = column.split(":");
        Double minutesInSeconds = Double.parseDouble(split[0]) * 60;
        Double seconds = Double.valueOf(split[1]);
        return minutesInSeconds + seconds;
    }

    private Double parseToDouble(String column) {
        return parseDouble(column.replace(",", "."));
    }
}
