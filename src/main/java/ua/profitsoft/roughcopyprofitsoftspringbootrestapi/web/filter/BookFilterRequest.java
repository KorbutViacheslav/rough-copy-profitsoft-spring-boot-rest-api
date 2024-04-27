package ua.profitsoft.roughcopyprofitsoftspringbootrestapi.web.filter;

import lombok.Builder;
import lombok.Data;

/**
 * Author: Viacheslav Korbut
 * Date: 27.04.2024
 */
@Builder
@Data
public class BookFilterRequest {
    public String title;
    public Integer yearPublish;
    public Integer page;
    public Integer size;
}
