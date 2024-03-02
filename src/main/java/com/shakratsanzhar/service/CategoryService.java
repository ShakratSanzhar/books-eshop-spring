package com.shakratsanzhar.service;

import com.shakratsanzhar.domain.dto.CategoryCreateEditDto;
import com.shakratsanzhar.domain.dto.CategoryReadDto;
import com.shakratsanzhar.mapper.CategoryCreateEditMapper;
import com.shakratsanzhar.mapper.CategoryReadMapper;
import com.shakratsanzhar.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryReadMapper categoryReadMapper;
    private final CategoryCreateEditMapper categoryCreateEditMapper;

    public List<CategoryReadDto> findAll() {
        return categoryRepository.findAll().stream()
                .map(categoryReadMapper::map)
                .toList();
    }

    public Optional<CategoryReadDto> findById(Integer id) {
        return categoryRepository.findById(id)
                .map(categoryReadMapper::map);
    }

    public List<CategoryReadDto> findAllSubCategories() {
        return categoryRepository.findAllByMainCategoryNotNull().stream()
                .map(categoryReadMapper::map)
                .toList();
    }

    @Transactional
    public CategoryReadDto create(CategoryCreateEditDto categoryDto) {
        return Optional.of(categoryDto)
                .map(categoryCreateEditMapper::map)
                .map(categoryRepository::save)
                .map(categoryReadMapper::map)
                .orElseThrow();
    }

    @Transactional
    public Optional<CategoryReadDto> update(Integer id, CategoryCreateEditDto categoryCreateEditDto) {
        return categoryRepository.findById(id)
                .map(entity -> categoryCreateEditMapper.map(categoryCreateEditDto, entity))
                .map(categoryRepository::saveAndFlush)
                .map(categoryReadMapper::map);
    }

    @Transactional
    public boolean delete(Integer id) {
        return categoryRepository.findById(id)
                .map(entity -> {
                    categoryRepository.delete(entity);
                    categoryRepository.flush();
                    return true;
                })
                .orElse(false);
    }
}
