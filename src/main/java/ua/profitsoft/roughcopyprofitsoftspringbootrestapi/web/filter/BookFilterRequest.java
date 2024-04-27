package ua.profitsoft.roughcopyprofitsoftspringbootrestapi.web.filter;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

/**
 * Author: Viacheslav Korbut
 * Date: 27.04.2024
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
