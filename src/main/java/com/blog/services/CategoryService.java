package com.blog.services;

import java.util.List;

import com.blog.payloads.CategoryDto;

public interface CategoryService {
	CategoryDto getCategory(Integer categoryID);
	List<CategoryDto> getAllCategory();
	
	CategoryDto createCategory(CategoryDto categoryDto);
	CategoryDto updateCategory(CategoryDto categoryDto,Integer categoryID);
	
	void deleteCategory(Integer categoryId);
	
}
