//package com.wetie.student.access.rest.config;
//
//import org.springframework.boot.context.properties.EnableConfigurationProperties;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.Customizer;
//import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.factory.PasswordEncoderFactories;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.provisioning.InMemoryUserDetailsManager;
//import org.springframework.security.web.SecurityFilterChain;
//
//@Configuration
//@EnableWebSecurity
//@EnableMethodSecurity
//@EnableConfigurationProperties(SecurityProperties.class)
public class BasicConfiguration {

   /* @Bean
    public PasswordEncoder passwordEncoder() {
        //PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        return new BCryptPasswordEncoder();
    }*/

    // Authentication
    /*@Bean
    public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder, SecurityProperties securityProperties) {
        UserDetails userDetails = User
                .withUsername(securityProperties.username())
                .password(passwordEncoder.encode(securityProperties.password()))
                .roles("Admin")
                .build();

        return new InMemoryUserDetailsManager(userDetails);
    }*/

//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        return http
//                .csrf(CsrfConfigurer<HttpSecurity>::disable)
//                .authorizeHttpRequests(request ->
//                request.requestMatchers("/students").permitAll()
//                        .anyRequest().authenticated())
//                .httpBasic(Customizer.withDefaults())
//                .build();
//    }
}





















