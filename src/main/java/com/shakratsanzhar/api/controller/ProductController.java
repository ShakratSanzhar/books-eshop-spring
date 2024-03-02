package com.shakratsanzhar.api.controller;

import com.shakratsanzhar.domain.dto.PageResponse;
import com.shakratsanzhar.domain.dto.ProductCreateEditDto;
import com.shakratsanzhar.domain.dto.ProductFilter;
import com.shakratsanzhar.domain.dto.ProductReadDto;
import com.shakratsanzhar.service.CategoryService;
import com.shakratsanzhar.service.ProductService;
import com.shakratsanzhar.validation.group.CreateAction;
import com.shakratsanzhar.validation.group.UpdateAction;
import org.springframework.data.domain.Pageable;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final CategoryService categoryService;

    @GetMapping
    public String findAll(@SessionAttribute("cartId") Long cartId, @SessionAttribute("userId") Long userId, Model model, ProductFilter filter, Pageable pageable) {
        Page<ProductReadDto> page = productService.findAll(filter, pageable);
        model.addAttribute("products", PageResponse.of(page));
        model.addAttribute("filter", filter);
        model.addAttribute("cartId",cartId);
        model.addAttribute("userId",userId);
        return "product/products";
    }

    @GetMapping("/{id}")
    public String findById(@PathVariable("id") Long id, Model model) {
        return productService.findById(id)
                .map(product -> {
                    model.addAttribute("categories", categoryService.findAllSubCategories());
                    model.addAttribute("product", product);
                    return "product/product";
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/registration")
//    @PreAuthorize("hasAuthority('ADMIN')")
    public String registration(Model model, @ModelAttribute("product") ProductCreateEditDto product) {
        model.addAttribute("product", product);
        model.addAttribute("categories", categoryService.findAllSubCategories());
        return "product/product_registration";
    }

    @PostMapping
//    @PreAuthorize("hasAuthority('ADMIN')")
    public String create(@ModelAttribute @Validated(value = {CreateAction.class}) ProductCreateEditDto product, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if(bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("product",product);
            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
            return "redirect:/products/registration";
        }
        return "redirect:/products/" +productService.create(product).getId();
    }

    @PostMapping("/{id}/update")
//    @PreAuthorize("hasAuthority('ADMIN')")
    public String update(@PathVariable("id") Long id, @ModelAttribute @Validated(value = {UpdateAction.class}) ProductCreateEditDto product, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if(bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
            return "redirect:/products/{id}";
        }
        String success = "Product updated successfully";
        return productService.update(id, product)
                .map(it -> {
                    redirectAttributes.addFlashAttribute("success",success);
                    return "redirect:/products/{id}";
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/{id}/delete")
//    @PreAuthorize("hasAuthority('ADMIN')")
    public String delete(@PathVariable("id") Long id) {
        if (!productService.delete(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return "redirect:/products";
    }
}