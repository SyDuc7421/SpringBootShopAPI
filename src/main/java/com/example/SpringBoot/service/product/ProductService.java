package com.example.SpringBoot.service.product;

import com.example.SpringBoot.exception.ProductNotFoundException;
import com.example.SpringBoot.model.Category;
import com.example.SpringBoot.model.Product;
import com.example.SpringBoot.repository.CategoryRepository;
import com.example.SpringBoot.repository.ProductRepository;
import com.example.SpringBoot.request.AddProductRequest;
import com.example.SpringBoot.request.ProductUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService implements IProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public Product addProduct(AddProductRequest product) {
        Category category = Optional.ofNullable(categoryRepository.findByName(product.getCategory().getName()))
                .orElseGet(()->{
                    Category newCategory = new Category(product.getCategory().getName());
                        return categoryRepository.save(newCategory);
                });
        product.setCategory(category);
        return productRepository.save(createProduct(product, category));
    }

    private Product createProduct(AddProductRequest product, Category category) {
        return new Product(
                product.getName(),
                product.getBrand(),
                product.getPrice(),
                product.getDescription(),
                product.getInventory(),
                category
        );
    }

    @Override
    public Product getProductById(Long id) {
        return productRepository.findById(id).orElseThrow(()->new ProductNotFoundException("Product not found"));
    }

    @Override
    public Product updateProduct(ProductUpdateRequest updateProduct, Long productId) {
        return productRepository.findById(productId).map(existingProduct -> updateExistingProduct(existingProduct, updateProduct))
                .orElseThrow(()->new ProductNotFoundException("Product not found"));
    }

    private Product updateExistingProduct(Product product, ProductUpdateRequest updatedProduct) {
        product.setName(updatedProduct.getName());
        product.setBrand(updatedProduct.getBrand());
        product.setPrice(updatedProduct.getPrice());
        product.setDescription(updatedProduct.getDescription());
        product.setInventory(updatedProduct.getInventory());

        Category category = categoryRepository.findByName(updatedProduct.getCategory().getName());
        product.setCategory(category);
        return productRepository.save(product);

    }

    @Override
    public void deleteProduct(Long id) {
         productRepository
                .findById(id).ifPresentOrElse(productRepository::delete,
                         ()->{throw new ProductNotFoundException("Product not found!");});
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public List<Product> getProductsByCategory(String category) {
        return productRepository.findByCategoryName(category);
    }

    @Override
    public List<Product> getProductsByBrand(String brand) {
        return productRepository.findByBrand(brand);

    }

    @Override
    public List<Product> getProductsByCategoryAndBrand(String category, String brand) {
        return productRepository.findByCategoryNameAndBrand(category, brand);
    }

    @Override
    public List<Product> getProductsByName(String name) {
        return productRepository.findByName(name);
    }

    @Override
    public List<Product> getProductsByBrandAndName(String brand, String name) {
        return productRepository.findByBrandAndName(brand, name);
    }

    @Override
    public Long countProductsByBrandAndName(String brand, String name) {
        return productRepository.countByBrandAndName(brand, name);
    }
}
