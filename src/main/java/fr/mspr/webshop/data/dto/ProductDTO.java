package fr.mspr.webshop.data.dto;

import fr.mspr.webshop.data.model.ProductDetails;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ProductDTO {

    private long id;
    private LocalDateTime createdAt;
    private String name;
    private ProductDetails details;
    private int stock;


}
