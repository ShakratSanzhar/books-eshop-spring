package com.shakratsanzhar.repository;

import com.shakratsanzhar.IntegrationTestBase;
import com.shakratsanzhar.domain.entity.Category;
import com.shakratsanzhar.utils.TestEntityBuilder;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@RequiredArgsConstructor
public class CategoryRepositoryTestIT extends IntegrationTestBase {

    private static final Integer MAX_COUNT_OF_EXISTING_MAIN_CATEGORIES = 3 ;
    private static final Integer EXISTING_CATEGORY_ID = 1;
    private static final Integer MAX_COUNT_OF_EXISTING_CATEGORIES = 10;

    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;

    @Test
    void shouldSaveMainCategory() {
        var category = TestEntityBuilder.createMainCategory();

        categoryRepository.save(category);

        assertThat(category.getId()).isNotNull();
    }

    @Test
    void saveNewSubCategoryWithInitializingMainCategory() {
        var mainCategory = TestEntityBuilder.createMainCategory();
        categoryRepository.save(mainCategory);
        var subCategoryWithInitializingMainCategory = Category.builder()
                .name("Doni")
                .mainCategory(mainCategory)
                .build();

        categoryRepository.save(subCategoryWithInitializingMainCategory);
        var actualSubCategory = categoryRepository.findById(subCategoryWithInitializingMainCategory.getId());

        assertThat(actualSubCategory).isNotEmpty();
        assertThat(actualSubCategory.get()).isEqualTo(subCategoryWithInitializingMainCategory);
        assertThat(mainCategory.getSubCategories()).isNotNull();
    }

    @Test
    void saveNewSubCategoryWithoutInitializingMainCategory() {
        var mainCategory = TestEntityBuilder.createMainCategory();
        var subCategoryWithoutInitializingMainCategory = Category.builder()
                .name("Doni")
                .build();
        mainCategory.addSubCategory(subCategoryWithoutInitializingMainCategory);

        categoryRepository.save(mainCategory);
        var actualSubCategory = categoryRepository.findById(subCategoryWithoutInitializingMainCategory.getId());

        assertThat(actualSubCategory.get()).isEqualTo(subCategoryWithoutInitializingMainCategory);
        assertThat(mainCategory.getSubCategories()).isNotNull();
    }

    @Test
    void deleteExistingCategory() {
        var category = categoryRepository.findById(EXISTING_CATEGORY_ID);

        categoryRepository.deleteById(EXISTING_CATEGORY_ID);
        var actualCategory = categoryRepository.findById(EXISTING_CATEGORY_ID);

        assertThat(actualCategory).isEmpty();
        assertThat(category).isNotEmpty();
        assertThat(category.get().getId()).isEqualTo(EXISTING_CATEGORY_ID);
    }

    @Test
    void updateExistingCategory() {
        var category = categoryRepository.findById(EXISTING_CATEGORY_ID);
        category.ifPresent(c -> c.setName("Math"));

        var actualCategory = categoryRepository.findById(EXISTING_CATEGORY_ID);

        assertThat(actualCategory.get().getName()).isEqualTo("Math");
    }

    @Test
    void getExistingCategory() {
        var category = categoryRepository.findById(EXISTING_CATEGORY_ID);

        assertThat(category).isNotEmpty();
    }

    @Test
    void getAllExistingCategories() {
        var categories = categoryRepository.findAll();

        assertThat(categories).hasSize(MAX_COUNT_OF_EXISTING_CATEGORIES);
    }

    @Test
    void addNewProductToCategory() {
        var category = categoryRepository.findById(EXISTING_CATEGORY_ID);
        var product = TestEntityBuilder.createProduct();
        category.get().addProduct(product);

        productRepository.save(product);
        var actualProduct = productRepository.findById(product.getId());

        assertThat(actualProduct).isNotEmpty();
    }

    @Test
    void findAllByMainCategoryNull() {
        var categories = categoryRepository.findAllByMainCategoryNull();

        assertThat(categories).hasSize(MAX_COUNT_OF_EXISTING_MAIN_CATEGORIES);
    }
}
