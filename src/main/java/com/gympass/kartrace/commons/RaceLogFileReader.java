package com.gympass.kartrace.commons;

import com.gympass.kartrace.exception.FileNotFoundException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class RaceLogFileReader {
    public List<String> getLines(String filePath) {
        Path path = Paths.get(filePath);
        try {
            return Files.readAllLines(path);
        } catch (IOException e) {
            throw new FileNotFoundException("File not found.", e);
        }
    }
}
