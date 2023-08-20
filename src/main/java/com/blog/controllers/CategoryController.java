package com.blog.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blog.payloads.ApiResponse;
import com.blog.payloads.CategoryDto;
import com.blog.services.CategoryService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {
	
	@Autowired
	private CategoryService categoryService;
	
	@GetMapping("/{categoryId}")
	public ResponseEntity<CategoryDto> getCategory(@PathVariable Integer categoryId) {
		CategoryDto categoryDto = this.categoryService.getCategory(categoryId);
		return ResponseEntity.ok(categoryDto);
	}
	
	@GetMapping("/")
	public ResponseEntity<List<CategoryDto>> getAllCategory() {
		List<CategoryDto> categoryDtos = this.categoryService.getAllCategory();
		return new ResponseEntity<>(categoryDtos,HttpStatus.OK);
	}
	
	@PostMapping("/")
	public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto categoryDto) {
		CategoryDto createdCategoryDto = this.categoryService.createCategory(categoryDto);
		return new ResponseEntity<CategoryDto>(createdCategoryDto,HttpStatus.CREATED);
	}
	
	@PutMapping("/{categoryId}")
	public ResponseEntity<CategoryDto> updateCategory(@Valid @RequestBody CategoryDto categoryDto,@PathVariable Integer categoryId) {
		CategoryDto updatedCategoryDto = this.categoryService.updateCategory(categoryDto, categoryId);
		return new ResponseEntity<>(updatedCategoryDto,HttpStatus.OK);
	}
	
	@DeleteMapping("/{categoryId}")
	public ResponseEntity<ApiResponse> updateCategory(@PathVariable Integer categoryId) {
		this.categoryService.deleteCategory(categoryId);
		return new ResponseEntity<>(new ApiResponse("Category has been deleted successfully",true),HttpStatus.OK);
	}
	
	

}
