package com.hissain.jscipy.signal;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.stream.Stream;

public class ResampleGenerator {

    private static double[] readData(String fileName) throws IOException {
        Path path = Paths.get("datasets/" + fileName);
        try (Stream<String> lines = Files.lines(path)) {
            return lines.mapToDouble(Double::parseDouble).toArray();
        }
    }

    public static void main(String[] args) throws IOException {
        // Test case 1
        double[] input1 = readData("resample_1_input.txt");
        Resample resample1 = new Resample();
        double[] output1 = resample1.resample(input1, 50);
        writeData("resample_1_output_java.txt", output1);

        // Test case 2
        double[] input2 = readData("resample_2_input.txt");
        Resample resample2 = new Resample();
        double[] output2 = resample2.resample(input2, 30);
        writeData("resample_2_output_java.txt", output2);
    }

    private static void writeData(String fileName, double[] data) throws IOException {
        Path path = Paths.get("datasets/" + fileName);
        StringBuilder sb = new StringBuilder();
        for (double d : data) {
            sb.append(d).append(System.lineSeparator());
        }
        Files.write(path, sb.toString().getBytes());
    }
}
