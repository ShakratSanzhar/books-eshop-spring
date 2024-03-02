package com.shakratsanzhar.config;

import com.shakratsanzhar.domain.dto.UserCreateEditDto;
import com.shakratsanzhar.domain.dto.UserDetailCreateEditDto;
import com.shakratsanzhar.domain.enums.Role;
import com.shakratsanzhar.service.UserDetailService;
import com.shakratsanzhar.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.time.LocalDateTime;
import java.util.Set;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final UserService userService;
    private final UserDetailService userDetailService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(urlConfig -> urlConfig
                        .requestMatchers(new AntPathRequestMatcher("/login")).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/users/registration")).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/users", HttpMethod.POST.name())).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/users/{\\d+}/details", HttpMethod.GET.name())).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/users/{\\d+}/details", HttpMethod.POST.name())).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/users", HttpMethod.GET.name())).hasAuthority(Role.ADMIN.getAuthority())
                        .requestMatchers(new AntPathRequestMatcher("/users/{\\d+}/delete")).hasAuthority(Role.ADMIN.getAuthority())
                        .requestMatchers(new AntPathRequestMatcher("/orders", HttpMethod.GET.name())).hasAuthority(Role.ADMIN.getAuthority())
                        .requestMatchers(new AntPathRequestMatcher("/orders/{\\d+}/confirm", HttpMethod.POST.name())).hasAuthority(Role.ADMIN.getAuthority())
                        .requestMatchers(new AntPathRequestMatcher("/products/registration", HttpMethod.GET.name())).hasAuthority(Role.ADMIN.getAuthority())
                        .requestMatchers(new AntPathRequestMatcher("/products", HttpMethod.POST.name())).hasAuthority(Role.ADMIN.getAuthority())
                        .requestMatchers(new AntPathRequestMatcher("/products/{\\d+}/update", HttpMethod.POST.name())).hasAuthority(Role.ADMIN.getAuthority())
                        .requestMatchers(new AntPathRequestMatcher("/products/{\\d+}/delete", HttpMethod.POST.name())).hasAuthority(Role.ADMIN.getAuthority())
                        .anyRequest().authenticated()
                )
                //      .httpBasic(Customizer.withDefaults());
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login")
                )
                .formLogin(login -> login
                        .loginPage("/login")
                        .defaultSuccessUrl("/users/welcome"))
                .oauth2Login(config -> config
                        .loginPage("/login")
                        .defaultSuccessUrl("/users/welcome")
                        .userInfoEndpoint(userInfo -> userInfo.oidcUserService(oidcUserService()))
                );
        return http.build();
    }

    private OAuth2UserService<OidcUserRequest, OidcUser> oidcUserService() {
        return userRequest -> {
            String email = userRequest.getIdToken().getClaim("email");
            if (userService.findByUsername(email).isEmpty()) {
                var user = userService.create(UserCreateEditDto.builder()
                        .username(userRequest.getIdToken().getEmail())
                        .email(userRequest.getIdToken().getEmail())
                        .rawPassword("google")
                        .role(Role.USER)
                        .build());
                userDetailService.create(UserDetailCreateEditDto.builder()
                        .userId(user.getId())
                        .name(userRequest.getIdToken().getGivenName())
                        .registrationDate(LocalDateTime.now())
                        .build());
            }
            UserDetails userDetails = userService.loadUserByUsername(email);
            DefaultOidcUser oidcUser = new DefaultOidcUser(userDetails.getAuthorities(), userRequest.getIdToken());
            Set<Method> userDetailsMethods = Set.of(UserDetails.class.getMethods());
            return (OidcUser) Proxy.newProxyInstance(SecurityConfiguration.class.getClassLoader(),
                    new Class[]{UserDetails.class, OidcUser.class},
                    (proxy, method, args) -> userDetailsMethods.contains(method)
                            ? method.invoke(userDetails, args)
                            : method.invoke(oidcUser, args));
        };
    }
}

