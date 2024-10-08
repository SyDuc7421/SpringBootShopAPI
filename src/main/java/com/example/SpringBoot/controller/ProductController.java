package com.example.SpringBoot.controller;

import com.example.SpringBoot.exception.ResourceNotFoundException;
import com.example.SpringBoot.model.Product;
import com.example.SpringBoot.response.APIResponse;
import com.example.SpringBoot.service.product.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix/products")
public class ProductController {
    private final IProductService productService;

    @GetMapping
    public ResponseEntity<APIResponse> getAllProducts() {
        try{
            List<Product> products = productService.getAllProducts();
            return ResponseEntity.ok(new APIResponse("Success!", products));
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new APIResponse("Error while get all product!", null));
        }
    }

    @GetMapping("{id}")
    public ResponseEntity<APIResponse> getProductById(@PathVariable Long id) {
        try{
            Product product = productService.getProductById(id);
            return ResponseEntity.ok(new APIResponse("Success!", product));
        }
        catch (ResourceNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new APIResponse(e.getMessage(), null));
        }
    }

    @GetMapping
    public ResponseEntity<APIResponse> getProductByBrand(@RequestParam String brand) {
        try{
            List<Product> products = productService.getProductsByBrand(brand);
            return ResponseEntity.ok(new APIResponse("Success!", products));
        }
        catch (ResourceNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new APIResponse(e.getMessage(), null));
        }
    }

    @GetMapping
    public ResponseEntity<APIResponse> getProductByCategory(@RequestParam String category) {
        try{
            List<Product> products = productService.getProductsByCategory(category);
            return ResponseEntity.ok(new APIResponse("Success!", products));
        }
        catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new APIResponse(e.getMessage(), null));
        }
    }

    @GetMapping
    public ResponseEntity<APIResponse> getProductByName(@RequestParam String name) {
        try {
            List<Product> products = productService.getProductsByName(name);
            return ResponseEntity.ok(new APIResponse("Success!", products));
        }
        catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new APIResponse(e.getMessage(), null));
        }
    }

    
}
