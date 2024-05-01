package ua.profitsoft.roughcopyprofitsoftspringbootrestapi.writer;

import com.opencsv.CSVWriter;
import org.springframework.core.io.ByteArrayResource;
import ua.profitsoft.roughcopyprofitsoftspringbootrestapi.dto.create.BookCreateDTO;
import ua.profitsoft.roughcopyprofitsoftspringbootrestapi.util.exeption.error.CSVGenerationException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Author: Viacheslav Korbut
 * Date: 28.04.2024
 */

/**
 * This utility class provides methods for generating CSV reports based on a list of book DTOs.
 * The generateCSVReport method accepts a list of BookCreateDTO objects and generates a CSV report
 * containing information about the books. The generateFileName method generates a unique filename
 * for the CSV report based on the current date and time.
 */
public class CSVReportGenerator {

    private CSVReportGenerator() {

    }

    /**
     * Generates a CSV report based on the provided list of book DTOs.
     *
     * @param bookCreateDTOs The list of book DTOs to include in the report.
     * @return A {@link ByteArrayResource} containing the generated CSV report.
     * @throws CSVGenerationException If an error occurs during CSV generation.
     */
    public static ByteArrayResource generateCSVReport(List<BookCreateDTO> bookCreateDTOs) {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
             OutputStreamWriter writer = new OutputStreamWriter(baos, StandardCharsets.UTF_8)) {

            CSVWriter csvWriter = new CSVWriter(writer);

            // Write header
            String[] header = {"Title", "Author", "Year Published", "Genres"};
            csvWriter.writeNext(header);

            // Write book data
            for (BookCreateDTO book : bookCreateDTOs) {
                String authorFullName = book.getAuthor().getFirstName() + " " + book.getAuthor().getLastName();
                String[] data = {
                        book.getTitle(),
                        authorFullName,
                        book.getYearPublished().toString(),
                        String.join(", ", book.getGenres())
                };
                csvWriter.writeNext(data);
            }

            csvWriter.flush();
            return new ByteArrayResource(baos.toByteArray());

        } catch (IOException e) {
            throw new CSVGenerationException();
        }
    }

    /**
     * Generates a unique filename for the CSV report based on the current date and time.
     *
     * @return A string representing the generated filename.
     */
    public static String generateFileName() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");
        String formattedDateTime = now.format(formatter);
        return "books_report_" + formattedDateTime + ".csv";
    }
}
