package com.blog.project.config;
import com.blog.project.security.CustomUserDetailService;

import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.AuthenticationEntryPoint;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	@Autowired
    private  CustomUserDetailService customUserDetailService;
   
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//    	http
//        .csrf(csrf -> csrf.disable()) // Disable CSRF protection (can be enabled if needed)
//        .authorizeHttpRequests(auth -> auth
//            // Specify public URLs here (replace PUBLIC_URLS)
//            .requestMatchers("/public/**").permitAll() 
//            .anyRequest().authenticated() // All other requests require authentication
//        )
//        .exceptionHandling(exception -> exception
//            .authenticationEntryPoint(authenticationEntryPoint()) // Specify the entry point
//        )
//        .sessionManagement(session -> session
//            .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // Set stateless session management
//        );            
//        http.authenticationProvider(daoAuthenticationProvider());
//        
//        return http.build();
    	
//    	 http
//         .csrf(csrf -> csrf.disable())
//         .authorizeHttpRequests(auth -> auth
//             .requestMatchers("/public/**").permitAll()  // Public endpoints
//             .anyRequest().authenticated()  // Requires authentication for other requests
//         )
//         .exceptionHandling(exception -> exception
//             .authenticationEntryPoint(authenticationEntryPoint())  // Handle unauthorized access
//         )
//         .sessionManagement(session -> session
//             .sessionCreationPolicy(SessionCreationPolicy.STATELESS)  // Stateless session management
//         )
//         .authenticationProvider(daoAuthenticationProvider());  // Use custom authentication provider
//
//     return http.build();
    	
    	
            http
                .csrf(Customizer.withDefaults())
                .authorizeHttpRequests(authorize -> authorize
                    .anyRequest().authenticated()
                )
                .authenticationProvider(daoAuthenticationProvider())  // Use custom authentication provid
                .httpBasic(Customizer.withDefaults());
                
            return http.build();
        
 }

    

    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint() {
        return (request, response, authException) -> {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
        };
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }
    
    public DaoAuthenticationProvider daoAuthenticationProvider() {
    	DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
    	System.out.println(this.customUserDetailService);
    	provider.setUserDetailsService(this.customUserDetailService);
    	provider.setPasswordEncoder(passwordEncoder());
    	return provider;
    }
}
