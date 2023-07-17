package com.kk.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kk.entity.Category;
import com.kk.exceptions.ResourceNotFoundException;
import com.kk.payloads.CategoryDto;
import com.kk.repository.CategoryRepository;

@Service
public class CategoryServiceImpl implements CategoryService{

	@Autowired
	private CategoryRepository catRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Override
	public CategoryDto createCategory(CategoryDto dto) {
		Category category=modelMapper.map(dto, Category.class);
		Category addedCat= catRepository.save(category);
	return modelMapper.map(addedCat, CategoryDto.class);
	}

	@Override
	public CategoryDto updateCategory(CategoryDto dto, Integer categoryid) {
		Category cat= catRepository.findById(categoryid)
		.orElseThrow(()-> new ResourceNotFoundException("Category", "category Id", categoryid));
		cat.setCategoryTitle(dto.getCategoryTitle());
		cat.setCategoryDescription(dto.getCategoryDescription());
	
		Category updatedCat= catRepository.save(cat);
		return modelMapper.map(updatedCat, CategoryDto.class);
		
	}

	@Override
	public CategoryDto getCategory(Integer categoryId) {
		Category cat= catRepository.findById(categoryId)
				.orElseThrow(()-> new ResourceNotFoundException("Category", "category Id", categoryId));
		return modelMapper.map(cat, CategoryDto.class);
	}

	@Override
	public List<CategoryDto> getAllCategory() {
		List<Category> catList= catRepository.findAll();
		List<CategoryDto> catDtos= catList.stream().map((category)->modelMapper.map(category,CategoryDto.class)).collect(Collectors.toList());
			return catDtos;
	}

	@Override
	public void deleteCategory(Integer categoryId) {
	Category cat= catRepository.findById(categoryId)
		.orElseThrow(()-> new ResourceNotFoundException("Category", "category Id", categoryId));
		catRepository.delete(cat);
	}

}
