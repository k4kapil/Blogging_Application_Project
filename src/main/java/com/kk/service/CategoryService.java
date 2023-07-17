package com.kk.service;

import java.util.List;

import com.kk.payloads.CategoryDto;

public interface CategoryService {
	CategoryDto createCategory(CategoryDto dto);
	CategoryDto updateCategory(CategoryDto dto, Integer categoryid);
	CategoryDto getCategory(Integer categoryid);	
	List<CategoryDto> getAllCategory();
	void deleteCategory(Integer categoryId);
}
