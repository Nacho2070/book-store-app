package com.Bookstore.book_store.web.config;

import com.Bookstore.book_store.utils.JwtUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EnableWebMvc
public class WebConfig{

    @Autowired
    private JwtUtils jwtUtils;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .cors(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorizeRequests -> authorizeRequests
                                .requestMatchers("/auth/**").permitAll()
                                .requestMatchers("/public/cart/").hasAnyAuthority("ROLE_ADMIN", "ROLE_USER")
                                .requestMatchers("/public/cart/**").permitAll()
                                .requestMatchers("/public/book/**").permitAll()
                                .requestMatchers("/public/genre/**").permitAll()
                                .anyRequest().authenticated()
                        )
                .addFilterBefore(new JwtTokenFilter(jwtUtils), BasicAuthenticationFilter.class);

        return http.build();
    }


    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
