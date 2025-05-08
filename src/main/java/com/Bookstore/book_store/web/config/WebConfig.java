package com.Bookstore.book_store.web.config;

import com.Bookstore.book_store.exceptions.APIException;
import com.Bookstore.book_store.utils.JwtUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
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
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception, APIException {
        http
                .cors(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorizeRequests -> authorizeRequests
                                //Swagger doc
                                .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()
                                //Auth endpoints
                                .requestMatchers("/auth/**").permitAll()
                                .requestMatchers("/demo/user/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_USER")
                                .requestMatchers("/public/cart/**").permitAll()
                                .requestMatchers(HttpMethod.POST,"/public/book/**").hasAnyAuthority("ROLE_ADMIN","ROLE_DEVELOPER")
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
