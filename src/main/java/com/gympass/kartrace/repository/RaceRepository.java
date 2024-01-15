package com.gympass.kartrace.repository;

import com.gympass.kartrace.commons.RaceLogFileReader;
import com.gympass.kartrace.domain.KartRaceLog;
import com.gympass.kartrace.domain.Lap;
import com.gympass.kartrace.domain.Pilot;

import java.util.List;

import static com.gympass.kartrace.commons.FileFormatConstants.*;
import static com.gympass.kartrace.commons.LogFileColumnsEnum.*;
import static java.lang.Float.parseFloat;
import static java.lang.Integer.parseInt;

public class RaceRepository {
    public static final int SECONDS_IN_A_MINUTE = 60;
    private final RaceLogFileReader logFileReader;

    public RaceRepository(RaceLogFileReader logFileReader) {
        this.logFileReader = logFileReader;
    }

    public List<KartRaceLog> getRaceLogFromFile(String filePath) {
        return logFileReader.getLines(filePath).stream()
                .skip(HEADER_LINE)
                .map(line -> line.split(REGEX_SPLIT_BY_SPACES))
                .filter(it -> it.length == COLUMNS_BY_LINE)
                .map(this::mapToObj)
                .toList();
    }

    private KartRaceLog mapToObj(String[] columns) {
        Lap lap = new Lap(parseInt(columns[LAP_NUM.getColumn()]),
                parseToSeconds(columns[LAP_TIME.getColumn()]),
                parseToFloat(columns[LAP_AVERAGE_TIME.getColumn()]));

        Pilot pilot = new Pilot(
                columns[PILOT_NUMBER.getColumn()],
                columns[PILOT_NAME.getColumn()]);

        return new KartRaceLog(pilot, lap);
    }

    private Float parseToSeconds(String column) {
        String[] split = column.split(":");
        Float minutesInSeconds = parseFloat(split[0]) * SECONDS_IN_A_MINUTE;
        Float seconds = Float.valueOf(split[HEADER_LINE]);
        return minutesInSeconds + seconds;
    }

    private Float parseToFloat(String column) {
        return parseFloat(column.replace(",", "."));
    }
}
