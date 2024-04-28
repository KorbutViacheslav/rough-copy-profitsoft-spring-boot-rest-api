package ua.profitsoft.roughcopyprofitsoftspringbootrestapi.writer;

import com.opencsv.CSVWriter;
import org.springframework.core.io.ByteArrayResource;
import ua.profitsoft.roughcopyprofitsoftspringbootrestapi.dto.create.BookCreateDTO;
import ua.profitsoft.roughcopyprofitsoftspringbootrestapi.util.exeption.error.CSVGenerationException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * Author: Viacheslav Korbut
 * Date: 28.04.2024
 */
public class CSVReportGenerator {
    public ByteArrayResource generateCSVReport(List<BookCreateDTO> bookCreateDTOs) {
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
}
