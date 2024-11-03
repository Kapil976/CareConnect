package com.kg.config;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration
public class SecurityConfig {

	private final CustomAuthenticationProvider customAuthenticationProvider;

    @Autowired
    public SecurityConfig(@Lazy CustomAuthenticationProvider customAuthenticationProvider) {
        this.customAuthenticationProvider = customAuthenticationProvider;
    }
    

	@Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
            .csrf(csrf -> csrf.disable()) // Disable CSRF for simplicity (not recommended for production)
            .authorizeHttpRequests(authz -> 
                authz.requestMatchers("/", "/register","/allPatients","/allUser").permitAll() // Allow access without authentication
                    .requestMatchers("/addPatient", "/patients").hasRole("USER") // Require USER role for these endpoints
                    .anyRequest().authenticated()) // All other requests require authentication
            .formLogin(form -> form
                .loginPage("/login")
                .defaultSuccessUrl("/dashboard", true) // Redirect to /addPatient after login
                .permitAll())
            .logout(logout -> logout
                .logoutUrl("/logout") // URL to initiate logout
                .logoutSuccessUrl("/login?logout") // Redirect after successful logout
                .invalidateHttpSession(true) // Invalidate session on logout
                .deleteCookies("JSESSIONID") // Delete cookies on logout
                .permitAll()) // Allow all to access the logout URL
            .build();
    }

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(); // Use BCrypt for password encoding
	}
	

	@Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return new ProviderManager(Collections.singletonList(customAuthenticationProvider));
    }

	@Bean
	public WebSecurityCustomizer webSecurityCustomizer() {
		return (web) -> web.ignoring().requestMatchers("/h2-console/**");
	}

}
