package com.shakratsanzhar.service;

import com.shakratsanzhar.domain.dto.ProductCreateEditDto;
import com.shakratsanzhar.domain.dto.ProductFilter;
import com.shakratsanzhar.domain.dto.ProductReadDto;
import com.shakratsanzhar.domain.entity.Product;
import com.shakratsanzhar.mapper.ProductCreateEditMapper;
import com.shakratsanzhar.mapper.ProductReadMapper;
import com.shakratsanzhar.repository.ProductRepository;
import com.shakratsanzhar.utils.ProductPredicateBuilder;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductReadMapper productReadMapper;
    private final ProductCreateEditMapper productCreateEditMapper;
    private final ImageService imageService;

    public Page<ProductReadDto> findAll(ProductFilter filter, Pageable pageable) {
        var predicate = new ProductPredicateBuilder().build(filter);
        return productRepository.findAll(predicate, pageable)
                .map(productReadMapper::map);
    }

    public List<ProductReadDto> findAll() {
        return productRepository.findAll().stream()
                .map(productReadMapper::map)
                .toList();
    }

    public Optional<ProductReadDto> findById(Long id) {
        return productRepository.findById(id)
                .map(productReadMapper::map);
    }

    @Transactional
    public ProductReadDto create(ProductCreateEditDto productDto) {
        productDto.setCreatedAt(LocalDateTime.now());
        return Optional.of(productDto)
                .map(dto -> {
                    uploadImage(dto.getImage());
                    return productCreateEditMapper.map(dto);
                })
                .map(productRepository::save)
                .map(productReadMapper::map)
                .orElseThrow();
    }

    @SneakyThrows
    private void uploadImage(MultipartFile image) {
        if (!image.isEmpty()) {
            imageService.upload(image.getOriginalFilename(), image.getInputStream());
        }
    }

    public Optional<byte[]> findImage(Long id) {
        return productRepository.findById(id)
                .map(Product::getImage)
                .filter(StringUtils::hasText)
                .flatMap(imageService::get);
    }

    @Transactional
    public Optional<ProductReadDto> update(Long id, ProductCreateEditDto productDto) {
        return productRepository.findById(id)
                .map(entity -> {
                    uploadImage(productDto.getImage());
                    return productCreateEditMapper.map(productDto, entity);
                })
                .map(productRepository::saveAndFlush)
                .map(productReadMapper::map);
    }

    @Transactional
    public boolean delete(Long id) {
        return productRepository.findById(id)
                .map(entity -> {
                    productRepository.delete(entity);
                    productRepository.flush();
                    return true;
                })
                .orElse(false);
    }
}

