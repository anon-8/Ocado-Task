package com.ocado.basket;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
public class JsonLoader {
    public static @NotNull JsonNode getObjectFromJson(String absolutePathToJsonObjectFile) throws IOException {

        validateJsonFile(absolutePathToJsonObjectFile);

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(new File(absolutePathToJsonObjectFile));

        validateObjectFormat(rootNode);

        return rootNode;
    }
    private static void validateObjectFormat(JsonNode rootNode) throws IOException {
        if (!rootNode.isObject()) {
            throw new IOException("File does not contain a valid JSON object.");
        }
    }
    private static void validateJsonFile(String absolutePathToConfigFile) throws IOException {

        validatePathNotNullOrEmpty(absolutePathToConfigFile);
        validateFileNotNullOrEmpty(absolutePathToConfigFile);
    }

    private static void validateFileNotNullOrEmpty(String absolutePathToConfigFile) throws IOException {
        File configFile = new File(absolutePathToConfigFile);
        if (!configFile.exists() || configFile.length() == 0) {
            throw new IOException("File is empty or does not exist.");
        }
    }
    private static void validatePathNotNullOrEmpty(String absolutePathToConfigFile) throws IOException {
        if (absolutePathToConfigFile == null || absolutePathToConfigFile.isEmpty()) {
            throw new IOException("File path is null or empty.");
        }
    }
}
