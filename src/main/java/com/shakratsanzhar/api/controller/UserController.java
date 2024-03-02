package com.shakratsanzhar.api.controller;

import com.shakratsanzhar.domain.dto.CartCreateEditDto;
import com.shakratsanzhar.domain.dto.CartReadDto;
import com.shakratsanzhar.domain.dto.PageResponse;
import com.shakratsanzhar.domain.dto.UserCreateEditDto;
import com.shakratsanzhar.domain.dto.UserDetailCreateEditDto;
import com.shakratsanzhar.domain.dto.UserFilter;
import com.shakratsanzhar.domain.dto.UserReadDto;
import com.shakratsanzhar.domain.dto.UserSecurityDto;
import com.shakratsanzhar.domain.enums.Role;
import com.shakratsanzhar.service.CartService;
import com.shakratsanzhar.service.UserDetailService;
import com.shakratsanzhar.service.UserService;
import com.shakratsanzhar.validation.group.CreateAction;
import com.shakratsanzhar.validation.group.UpdateAction;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
@RequestMapping("/users")
@SessionAttributes({"userId", "cartId"})
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserDetailService userDetailService;
    private final CartService cartService;

    @GetMapping
    public String findAll(Model model, UserFilter filter, Pageable pageable) {
        Page<UserReadDto> page = userService.findAll(filter, pageable);
        model.addAttribute("users", PageResponse.of(page));
        model.addAttribute("filter", filter);
        return "user/users";
    }

    @GetMapping("/{id}")
    public String findById(@PathVariable("id") Long id, Model model) {
        return userService.findById(id)
                .map(user -> {
                    model.addAttribute("user", user);
                    model.addAttribute("roles", Role.values());
                    return "user/user";
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public String create(Model model, @ModelAttribute @Validated(value = {CreateAction.class}) UserCreateEditDto user, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("user", user);
            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
            return "redirect:/users/registration";
        }
        Long id = userService.create(user).getId();
        model.addAttribute("userId", id);
        return "redirect:/users/" + id + "/details";
    }

    @PostMapping("/{id}/update")
    public String update(@PathVariable("id") Long id, @ModelAttribute @Validated(value = {UpdateAction.class}) UserCreateEditDto user, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("user", user);
            redirectAttributes.addFlashAttribute("mainInfoErrors", bindingResult.getAllErrors());
            return "redirect:/users/{id}";
        }
        String mainSuccess = "Main information successfully updated";
        return userService.update(id, user)
                .map(it -> {
                    redirectAttributes.addFlashAttribute("mainSuccess", mainSuccess);
                    return "redirect:/users/{id}";
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable("id") Long id) {
        if (!userService.delete(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return "redirect:/users";
    }

    @GetMapping("/registration")
    public String registration(Model model, @ModelAttribute("user") UserCreateEditDto user) {
        model.addAttribute("user", user);
        model.addAttribute("roles", Role.values());
        return "user/registration";
    }

    @GetMapping("/{id}/details")
    public String userDetailsRegistration(@PathVariable("id") Long userId, Model model, @ModelAttribute("userDetail") UserDetailCreateEditDto userDetail) {
        model.addAttribute("userId", userId);
        model.addAttribute("userDetail", userDetail);
        return "user/details";
    }

    @PostMapping("/{id}/details")
    public String createUserDetails(@PathVariable("id") Long userId, @ModelAttribute @Validated(value = {CreateAction.class}) UserDetailCreateEditDto userDetail, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("userDetail", userDetail);
            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
            return "redirect:/users/{id}/details";
        }
        userDetail.setUserId(userId);
        userDetailService.create(userDetail);
        return "redirect:/login";
    }

    @PostMapping("/{id}/details/update")
    public String updateUserDetails(@PathVariable("id") Long id, @ModelAttribute @Validated(value = {UpdateAction.class}) UserDetailCreateEditDto userDetail, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("userDetail", userDetail);
            redirectAttributes.addFlashAttribute("detailsInfoErrors", bindingResult.getAllErrors());
            return "redirect:/users/{id}";
        }
        String detailsSuccess = "Details successfully updated";
        return userDetailService.update(id, userDetail)
                .map(it -> {
                    redirectAttributes.addFlashAttribute("detailsSuccess", detailsSuccess);
                    return "redirect:/users/{id}";
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/welcome")
    public String welcome(Model model, @AuthenticationPrincipal UserSecurityDto userSecurityDto, @AuthenticationPrincipal OidcUser oidcUser) {
        Long userId;
        if (userSecurityDto == null) {
            userId = userService.findByUsername(oidcUser.getEmail()).get().getId();
        } else {
            userId = userSecurityDto.getId();
        }
        model.addAttribute("userId", userId);
        Optional<CartReadDto> cart = cartService.findCartByUser(userId);
        if (cart.isPresent()) {
            model.addAttribute("cartId", cart.get().getId());
        } else {
            CartCreateEditDto cartCreateEditDto = new CartCreateEditDto(userId, 0);
            CartReadDto cartReadDto = cartService.create(cartCreateEditDto);
            model.addAttribute("cartId", cartReadDto.getId());
        }
        return "user/welcome";
    }
}

