package com.example.LogisticsNetworkSystem.services;

import com.example.LogisticsNetworkSystem.entities.Product;
import com.example.LogisticsNetworkSystem.exceptions.ResourceNotFoundException;
import java.util.Date;
import java.util.List;

import com.example.LogisticsNetworkSystem.repositories.ProductRepository;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    private void validateProductData(String name, String sku, Double weightKg, String category) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be blank");
        }
        if (name.length() > 255) {
            throw new IllegalArgumentException("Name cannot exceed 255 characters");
        }
        if (sku == null || sku.trim().isEmpty()) {
            throw new IllegalArgumentException("Sku cannot be blank");
        }
        if (sku.length() > 255) {
            throw new IllegalArgumentException("Sku cannot exceed 255 characters");
        }
        if (weightKg == null || weightKg <= 0) {
            throw new IllegalArgumentException("WeightKg must be greater than zero");
        }
        if (category == null || category.trim().isEmpty()) {
            throw new IllegalArgumentException("Category cannot be blank");
        }
        if (category.length() > 255) {
            throw new IllegalArgumentException("Category cannot exceed 255 characters");
        }
    }

    public Product addProduct(Product product) {
        if (product == null) {
            throw new IllegalArgumentException("Product cannot be null");
        }
        validateProductData(product.getName(), product.getSku(), product.getWeightKg(), product.getCategory());
        product.setActive(true);
        product.setCreatedDate(new Date());
        return productRepository.save(product);
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll().stream().filter(Product::isActive).toList();
    }

    public Product getById(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Product ID must be greater than zero");
        }
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
        if (!product.isActive()) {
            throw new ResourceNotFoundException("Product not found with id: " + id);
        }
        return product;
    }

    public Product updateProduct(Long id, String name, String sku, Double weightKg, String category) {
        validateProductData(name, sku, weightKg, category);
        Product product = getById(id);
        product.setName(name);
        product.setSku(sku);
        product.setWeightKg(weightKg);
        product.setCategory(category);
        product.setUpdatedDate(new Date());
        return productRepository.save(product);
    }

    public boolean deleteById(Long id) {
        Product product = getById(id);
        product.setActive(false);
        product.setUpdatedDate(new Date());
        productRepository.save(product);
        return true;
    }
}
