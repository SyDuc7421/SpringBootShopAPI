package com.example.SpringBoot.controller;

import com.example.SpringBoot.exception.ResourceNotFoundException;
import com.example.SpringBoot.model.Product;
import com.example.SpringBoot.request.AddProductRequest;
import com.example.SpringBoot.response.APIResponse;
import com.example.SpringBoot.service.product.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/products")
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

    @PostMapping
    public ResponseEntity<APIResponse> createProduct(@RequestBody AddProductRequest request) {
        try {
            Product product = productService.addProduct(request);
            return ResponseEntity.ok(new APIResponse("Success!", product));
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new APIResponse("Failed to create Product", null));
        }

    }

}
