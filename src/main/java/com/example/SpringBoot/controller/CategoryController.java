package com.example.SpringBoot.controller;

import com.example.SpringBoot.exception.AlreadyExistsException;
import com.example.SpringBoot.exception.ResourceNotFoundException;
import com.example.SpringBoot.model.Category;
import com.example.SpringBoot.response.APIResponse;
import com.example.SpringBoot.service.category.ICategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/categories")
public class CategoryController {
    private final ICategoryService categoryService;

    @GetMapping
    public ResponseEntity<APIResponse> getAllCategories() {
        try {
            List<Category> categories = categoryService.getAllCategories();
            return ResponseEntity.ok(new APIResponse("Found!", categories));
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new APIResponse(e.getMessage(), null));
        }
    }

    @PostMapping
    public ResponseEntity<APIResponse> addCategory(@RequestBody Category category) {
        try{
            Category categoryAdded = categoryService.addCategory(category);
            return ResponseEntity.ok(new APIResponse("Success to added new category!", categoryAdded));
        }
        catch (AlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new APIResponse(e.getMessage(), null));
        }

    }

    @GetMapping("/{id}")
    public ResponseEntity<APIResponse> getCategoryById(@PathVariable Long id) {
        try{
            Category category = categoryService.getCategoryById(id);
            return ResponseEntity.ok(new APIResponse("Found!", category));
        }
        catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new APIResponse(e.getMessage(), null));
        }
    }


    @PutMapping("/{id}")
    public ResponseEntity<APIResponse> updateCategory(@PathVariable Long id, @RequestBody Category category) {
        try{
            Category oldCategory = categoryService.getCategoryById(id);
            if (oldCategory != null) {
                Category updatedCategory = categoryService.updateCategory(category, oldCategory.getId());
                return ResponseEntity.ok(new APIResponse("Successfully updated category!", updatedCategory));
            }

        }
        catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new APIResponse(e.getMessage(), null));
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new APIResponse("Error while updating category!",null));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<APIResponse> deleteCategoryById(@PathVariable Long id) {
        try {
            Category category = categoryService.getCategoryById(id);
            if (category != null) {
                categoryService.deleteCategory(id);
                return ResponseEntity.ok(new APIResponse("Successfully deleted!", category));
            }
        }
        catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new APIResponse("Category not found!", null));
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new APIResponse("Error while deleting category", null));
    }
}
