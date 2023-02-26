package fr.mspr.webshop.utils.mapper;

import fr.mspr.webshop.data.dto.ProductDTO;
import fr.mspr.webshop.data.model.Product;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Product updateProductFromDTO(ProductDTO productDTO, @MappingTarget Product product);
}
