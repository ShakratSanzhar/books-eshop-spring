package com.shakratsanzhar.repository;

import com.querydsl.core.types.Predicate;
import com.shakratsanzhar.IntegrationTestBase;
import com.shakratsanzhar.domain.dto.ProductFilter;
import com.shakratsanzhar.utils.PageableUtils;
import com.shakratsanzhar.utils.ProductPredicateBuilder;
import com.shakratsanzhar.utils.TestEntityBuilder;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import static org.assertj.core.api.Assertions.assertThat;

@RequiredArgsConstructor
public class ProductRepositoryTestIT extends IntegrationTestBase {

    private static final Integer EXISTING_CATEGORY_ID = 1;
    private static final Long EXISTING_PRODUCT_ID = 1L;
    private static final Integer MAX_COUNT_OF_EXISTING_PRODUCTS = 13;

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    @Test
    void saveNewProduct() {
        var product = TestEntityBuilder.createProduct();
        var category = categoryRepository.findById(EXISTING_CATEGORY_ID);
        product.setCategory(category.get());

        productRepository.save(product);

        assertThat(product.getId()).isNotNull();
    }

    @Test
    void deleteExistingProduct() {
        var product = productRepository.findById(EXISTING_PRODUCT_ID);

        productRepository.deleteById(EXISTING_PRODUCT_ID);
        var actualProduct = productRepository.findById(EXISTING_PRODUCT_ID);

        assertThat(actualProduct).isEmpty();
        assertThat(product).isNotEmpty();
    }

    @Test
    void updateExistingProduct() {
        var product = productRepository.findById(EXISTING_PRODUCT_ID);
        product.get().setAuthor("Jackson");

        var actualProduct = productRepository.findById(EXISTING_PRODUCT_ID);

        assertThat(actualProduct.get().getAuthor()).isEqualTo("Jackson");
    }

    @Test
    void getExistingProduct() {
        var actualProduct = productRepository.findById(EXISTING_PRODUCT_ID);

        assertThat(actualProduct).isNotEmpty();
        actualProduct.ifPresent(product -> assertThat(product.getId()).isEqualTo(EXISTING_PRODUCT_ID));
    }

    @Test
    void getAllExistingProduct() {
        var actualProducts = productRepository.findAll();

        assertThat(actualProducts).hasSize(MAX_COUNT_OF_EXISTING_PRODUCTS);
    }

    @Test
    void findAllByNameContainingIgnoreCase() {
        var products = productRepository.findAllByNameContainingIgnoreCase("менеджмент");

        assertThat(products).hasSize(2);
    }

    @Test
    void findAllByPredicateAndPageable() {
        var productFilter = ProductFilter.builder()
                .mainCategoryName("Бизнес")
                .build();
        var pr = new ProductPredicateBuilder();
        Predicate predicate = pr.build(productFilter);
        Pageable pageable = PageableUtils.getUnSortedPageable(0, 2);

        var products = productRepository.findAll(predicate,pageable);

        assertThat(products.getTotalElements()).isEqualTo(6L);
        assertThat(products.getTotalPages()).isEqualTo(3);
    }

    @Test
    void findAllByPredicateAndPageableByAscendingPrice() {
        var productFilter = ProductFilter.builder()
                .mainCategoryName("Бизнес")
                .build();
        var pr = new ProductPredicateBuilder();
        Predicate predicate = pr.build(productFilter);
        Pageable pageable = PageableUtils.getSortedPageable(0, 2, Sort.Direction.ASC,"price");

        var products = productRepository.findAll(predicate,pageable);

        assertThat(products.getTotalElements()).isEqualTo(6L);
        assertThat(products.getTotalPages()).isEqualTo(3);
        assertThat(products.getContent().get(0).getId()).isEqualTo(3L);
    }

    @Test
    void findAllByPredicateAndPageableByDescendingPrice() {
        var productFilter = ProductFilter.builder()
                .mainCategoryName("Бизнес")
                .build();
        var pr = new ProductPredicateBuilder();
        Predicate predicate = pr.build(productFilter);
        Pageable pageable = PageableUtils.getSortedPageable(0, 2, Sort.Direction.DESC,"price");

        var products = productRepository.findAll(predicate,pageable);

        assertThat(products.getTotalElements()).isEqualTo(6L);
        assertThat(products.getTotalPages()).isEqualTo(3);
        assertThat(products.getContent().get(0).getId()).isEqualTo(5L);
    }
}
