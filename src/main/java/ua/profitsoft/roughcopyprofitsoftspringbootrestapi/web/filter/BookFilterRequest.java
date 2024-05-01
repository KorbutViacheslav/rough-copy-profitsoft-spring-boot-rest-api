package ua.profitsoft.roughcopyprofitsoftspringbootrestapi.web.filter;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

/**
 * Author: Viacheslav Korbut
 * Date: 27.04.2024
 */

/**
 * This class represents a request used to filter books based on criteria such as title,
 * year published, author's first name, author's last name, page number, and page size.
 * Each attribute is annotated with the @Schema annotation to provide a description and
 * example value for documentation purposes.
 */
@Builder
@Data
public class BookFilterRequest {
    @Schema(description = "Title", example = "It")
    public String title;

    @Schema(description = "Year published", example = "1986")
    public Integer yearPublish;

    @Schema(description = "Author firstname", example = "Stephen")
    private String authorFirstName;

    @Schema(description = "Author lastname", example = "King")
    private String authorLastName;

    @Schema(description = "Page", example = "0")
    public Integer page;

    @Schema(description = "Size", example = "2")
    public Integer size;
}
