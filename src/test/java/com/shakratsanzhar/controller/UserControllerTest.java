package com.shakratsanzhar.controller;

import com.shakratsanzhar.IntegrationTestBase;
import com.shakratsanzhar.domain.dto.*;
import com.shakratsanzhar.domain.enums.Role;
import com.shakratsanzhar.service.CartService;
import com.shakratsanzhar.service.ProductService;
import com.shakratsanzhar.service.UserService;
import com.shakratsanzhar.utils.TestEntityBuilder;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@AutoConfigureMockMvc
@RequiredArgsConstructor
@WithMockUser(username = "test@gmail.com",password = "test",authorities = {"ADMIN","USER"})
public class UserControllerTest extends IntegrationTestBase {

    private final MockMvc mockMvc;
    private final CartService cartService;
    private final UserService userService;
    private final ProductService productService;

    @Test
    void findAll() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/users"))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andExpect(MockMvcResultMatchers.view().name("user/users"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("users"));
    }

    public UserReadDto getExistingUser() {
        var userCreateEditDto = UserCreateEditDto.builder()
                .username("Sanzhar")
                .email("shakrat.sg@gmail.com")
                .rawPassword("qwerty")
                .role(Role.ADMIN)
                .build();
        return userService.create(userCreateEditDto);
    }

    public CartReadDto getExistingCart(Long userId) {
        var cartCreateEditDto = new CartCreateEditDto(userId,0);
        return cartService.create(cartCreateEditDto);
    }

    public void addProductToExistingCart(Long userId,Long cartId,Long productId) {
        var cartProductCreateEditDto = new CartProductCreateEditDto(cartId,productId,1);
        cartService.addProduct(cartProductCreateEditDto,userId);
    }

    public ProductReadDto getExistingProduct() {
        var category = TestEntityBuilder.createMainCategory();
        category.setId(1);
        var productCreateEditDto =ProductCreateEditDto.builder()
                .categoryId(category.getId())
                .name("Psycho")
                .description("book about psychology")
                .author("Bayron")
                .image(new MockMultipartFile( "file",
                        "hello.txt",
                        MediaType.TEXT_PLAIN_VALUE,
                        "Hello, World!".getBytes()))
                .price(222)
                .remainingAmount(6)
                .build();
        return productService.create(productCreateEditDto);
    }
}
