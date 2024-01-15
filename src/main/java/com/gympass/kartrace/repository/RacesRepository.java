package com.gympass.kartrace.repository;

import com.gympass.kartrace.commons.LogFileReader;
import com.gympass.kartrace.domain.KartRaceLog;
import com.gympass.kartrace.domain.Lap;
import com.gympass.kartrace.domain.Pilot;

import java.util.List;

import static com.gympass.kartrace.commons.LogFileColumnsEnum.*;
import static java.lang.Double.parseDouble;
import static java.lang.Integer.parseInt;

public class RacesRepository {
    public static final int SECONDS_IN_A_MINUTE = 60;
    private final LogFileReader logFileReader;

    public RacesRepository(LogFileReader logFileReader) {
        this.logFileReader = logFileReader;
    }

    public List<KartRaceLog> getRaceFromFile(String filePath) {
        return logFileReader.getLines(filePath).stream()
                    //Skip Header
                    .skip(1)
                    .map(line -> line.split("\\s+"))
                    .map(this::mapToObj)
                    .toList();
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
        Double minutesInSeconds = Double.parseDouble(split[0]) * SECONDS_IN_A_MINUTE;
        Double seconds = Double.valueOf(split[1]);
        return minutesInSeconds + seconds;
    }

    private Double parseToDouble(String column) {
        return parseDouble(column.replace(",", "."));
    }
}
