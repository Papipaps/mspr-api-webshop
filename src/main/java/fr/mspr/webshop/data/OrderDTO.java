package fr.mspr.webshop.data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO extends BaseDTO {

    private Long id;
    private LocalDateTime createdAt;

    private Long customerId;


}

