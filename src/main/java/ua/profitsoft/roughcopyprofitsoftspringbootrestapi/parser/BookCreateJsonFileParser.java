package ua.profitsoft.roughcopyprofitsoftspringbootrestapi.parser;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.multipart.MultipartFile;
import ua.profitsoft.roughcopyprofitsoftspringbootrestapi.dto.create.BookCreateDTO;

import java.io.IOException;
import java.util.List;

/**
 * Author: Viacheslav Korbut
 * Date: 29.04.2024
 */

/**
 * Utility class for parsing JSON files containing data for creating books.
 * This class provides methods for parsing JSON data from a {@link org.springframework.web.multipart.MultipartFile}.
 */
public class BookCreateJsonFileParser {

    /**
     * Private constructor to prevent instantiation of the class.
     */
    private BookCreateJsonFileParser() {
    }

    /**
     * Parses JSON data from the provided {@link org.springframework.web.multipart.MultipartFile}.
     *
     * @param file The multipart file containing JSON data.
     * @return A list of {@link BookCreateDTO} objects parsed from the JSON data.
     * @throws IllegalArgumentException If the file is null, empty, has an invalid extension,
     *                                  or if there is an error parsing the JSON data.
     */
    public static List<BookCreateDTO> parseJsonFile(MultipartFile file) {
        validateFile(file);

        try (var inputStream = file.getInputStream()) {
            ObjectMapper objectMapper = new ObjectMapper();
            TypeReference<List<BookCreateDTO>> typeRef = new TypeReference<>() {
            };
            return objectMapper.readValue(inputStream, typeRef);
        } catch (JsonParseException e) {
            throw new IllegalArgumentException("Invalid JSON format in file " + file.getOriginalFilename(), e);
        } catch (IOException e) {
            throw new IllegalArgumentException("Error reading file " + file.getOriginalFilename(), e);
        }
    }

    /**
     * Validates the provided {@link org.springframework.web.multipart.MultipartFile}.
     *
     * @param file The multipart file to be validated.
     * @throws IllegalArgumentException If the file is null, empty, or has an invalid extension.
     */
    private static void validateFile(MultipartFile file) {
        if (file == null) {
            throw new IllegalArgumentException("The file is null.");
        }
        if (file.isEmpty()) {
            throw new IllegalArgumentException("The file is empty.");
        }
        String fileName = file.getOriginalFilename();
        if (fileName == null || !fileName.endsWith(".json")) {
            throw new IllegalArgumentException("The file has an invalid extension.");
        }
    }
}
