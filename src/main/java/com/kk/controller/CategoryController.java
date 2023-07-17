package com.kk.controller;

import java.util.List;

import javax.validation.Valid;

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

import com.kk.payloads.ApiResponse;
import com.kk.payloads.CategoryDto;
import com.kk.service.CategoryService;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

	@Autowired
	CategoryService categoryService;
	
	@PostMapping("/")
	public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto categoryDto) {
		CategoryDto dto= categoryService.createCategory(categoryDto);
	return new ResponseEntity<CategoryDto>(dto,HttpStatus.CREATED);
	}
	
	@PutMapping("/{catId}")
	public ResponseEntity<CategoryDto> updateCategory(@Valid @RequestBody CategoryDto categoryDto, @PathVariable("catId") Integer categoryId) {
		CategoryDto dto= categoryService.updateCategory(categoryDto, categoryId);
	return new ResponseEntity<CategoryDto>(dto,HttpStatus.OK);
	}
	
	@DeleteMapping("/{catId}")
	public ResponseEntity<ApiResponse> deleteCategory(@PathVariable("catId") Integer categoryId) {
		categoryService.deleteCategory(categoryId);
	return new ResponseEntity<ApiResponse>(new ApiResponse("Category deleted successfully !!",true),HttpStatus.OK);
	}
	
	@GetMapping("/{catId}")
	public ResponseEntity<CategoryDto> getCategory(@PathVariable("catId") Integer categoryId) {
		CategoryDto dto= categoryService.getCategory(categoryId);
		return new ResponseEntity<CategoryDto>(dto,HttpStatus.OK);	
	}
	
	@GetMapping("/")
	public ResponseEntity<List<CategoryDto>> getAllCategory() {
		List<CategoryDto> dto= categoryService.getAllCategory();
		return ResponseEntity.ok(dto);	
	}
}