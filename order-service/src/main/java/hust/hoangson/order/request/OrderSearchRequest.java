package hust.hoangson.order.request;

import jakarta.validation.constraints.Min;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class OrderSearchRequest {

    private Integer status;
    private LocalDateTime createdFrom;
    private LocalDateTime createdTo;

    @Min(value = 0)
    private int page = 0;
    @Min(value = 1)
    private int limit = 10;
    private String sortBy;
    private String sortDirection;
}
