package com.yourpackage.sds.config;

import com.yourpackage.sds.security.CustomAuthenticationSuccessHandler;
import com.yourpackage.sds.security.CustomUserDetailsService;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    private final CustomUserDetailsService userDetailsService;
    private final CustomAuthenticationSuccessHandler successHandler;

    public SecurityConfig(CustomUserDetailsService userDetailsService,
                          CustomAuthenticationSuccessHandler successHandler) {
        this.userDetailsService = userDetailsService;
        this.successHandler = successHandler;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .authenticationProvider(authenticationProvider())
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/", "/login", "/error", "/403", "/404").permitAll()
                .requestMatchers("/register", "/register/**").permitAll()
                .requestMatchers("/css/**", "/js/**", "/images/**", "/webjars/**", "/static/**").permitAll()
                .requestMatchers("/alerts/**", "/camps", "/camps/**").permitAll()
                .requestMatchers("/routes", "/directory", "/missing", "/missing/**", "/donations", "/safety-check", "/safety-check/**", "/guidelines", "/fact-check").permitAll()
                .requestMatchers("/api/**").permitAll()
                .requestMatchers("/dashboard", "/dashboard/").authenticated()
                .requestMatchers("/dashboard/admin", "/dashboard/admin/**", "/admin/**").hasRole("ADMIN")
                .requestMatchers("/dashboard/volunteer", "/dashboard/volunteer/**").hasRole("VOLUNTEER")
                .requestMatchers("/dashboard/citizen", "/dashboard/citizen/**").hasRole("CITIZEN")
                .requestMatchers("/citizen/**").hasAnyRole("CITIZEN", "ADMIN")
                .requestMatchers("/volunteer/**").hasAnyRole("VOLUNTEER", "ADMIN")
                .requestMatchers("/incident/**").authenticated()
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/login")
                .usernameParameter("email")
                .passwordParameter("password")
                .successHandler(successHandler)
                .failureUrl("/login?error")
                .permitAll()
            )
            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/")
                .invalidateHttpSession(true)
                .clearAuthentication(true)
                .deleteCookies("JSESSIONID")
                .permitAll()
            )
            .exceptionHandling(ex -> ex
                .accessDeniedPage("/403")
            );

        return http.build();
    }
}
