package com.example.LogisticsNetworkSystem.controllers;

import com.example.LogisticsNetworkSystem.dtos.ProductDTO;
import com.example.LogisticsNetworkSystem.entities.Product;
import com.example.LogisticsNetworkSystem.services.ProductService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.web.bind.annotation.*;

/** Exposes REST operations for product catalog management. */
@RestController
@RequestMapping("/product")
public class ProductController {

    /** Coordinates product persistence and catalog queries through the service layer. */
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    /** Creates a product from the validated request DTO. */
    @PostMapping("/add")
    public ProductDTO add(@Valid @RequestBody ProductDTO dto) {
        Product product = new Product();
        product.setName(dto.getName());
        product.setSku(dto.getSku());
        product.setWeightKg(dto.getWeightKg());
        product.setCategory(dto.getCategory());
        return ProductDTO.convertToDTO(productService.addProduct(product));
    }

    @GetMapping("/getAll")
    public List<ProductDTO> getAll() {
        return ProductDTO.convertToDTO(productService.getAllProducts());
    }

    @GetMapping("/getById/{id}")
    public ProductDTO getById(@PathVariable Long id) {
        return ProductDTO.convertToDTO(productService.getById(id));
    }

    @PutMapping("/update/{id}")
    public ProductDTO update(@PathVariable Long id, @Valid @RequestBody ProductDTO dto) {
        return ProductDTO.convertToDTO(productService.updateProduct(id, dto.getName(), dto.getSku(), dto.getWeightKg(), dto.getCategory()));
    }

    @DeleteMapping("/delete/{id}")
    public ProductDTO delete(@PathVariable Long id) {
        ProductDTO dto = ProductDTO.convertToDTO(productService.getById(id));
        productService.deleteById(id);
        return dto;
    }
}
