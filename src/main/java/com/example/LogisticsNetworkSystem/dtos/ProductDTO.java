package com.example.LogisticsNetworkSystem.dtos;

import com.example.LogisticsNetworkSystem.entities.Product;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/** Carries product catalog data between the API layer and application services. */
@Data
@NoArgsConstructor
public class ProductDTO {

    /** Unique identifier of the product. */
    private Long id;

    @NotBlank(message = "name cannot be blank")
    @Size(max = 255, message = "name cannot exceed 255 characters")
    /** Product name presented through the API. */
    private String name;

    @NotBlank(message = "sku cannot be blank")
    @Size(max = 255, message = "sku cannot exceed 255 characters")
    /** Stock keeping unit used to identify the product. */
    private String sku;

    @NotNull(message = "weightKg cannot be null")
    @Positive(message = "weightKg must be greater than zero")
    /** Product weight expressed in kilograms. */
    private Double weightKg;

    @NotBlank(message = "category cannot be blank")
    @Size(max = 255, message = "category cannot exceed 255 characters")
    /** Catalog category assigned to the product. */
    private String category;

    @Builder
    public ProductDTO(
            Long id,
            String name,
            String sku,
            Double weightKg,
            String category) {
        this.id = id;
        this.name = name;
        this.sku = sku;
        this.weightKg = weightKg;
        this.category = category;
    }

    public static ProductDTO convertToDTO(Product entity) {
        if (entity == null) {
            return null;
        }
        return ProductDTO.builder()
.id(entity.getId())
                .name(entity.getName())
                .sku(entity.getSku())
                .weightKg(entity.getWeightKg())
                .category(entity.getCategory())
                .build();
    }

    public static List<ProductDTO> convertToDTO(List<Product> entities) {
        return entities.stream()
                .map(ProductDTO::convertToDTO)
                .collect(Collectors.toList());
    }
}
