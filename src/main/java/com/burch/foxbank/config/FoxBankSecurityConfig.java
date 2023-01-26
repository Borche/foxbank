package com.burch.foxbank.config;

import com.burch.foxbank.filter.CsrfCookieFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;
import org.springframework.web.cors.CorsConfiguration;

import java.util.List;

@Configuration
public class FoxBankSecurityConfig {

  @Bean
  SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
    CsrfTokenRequestAttributeHandler requestHandler = new CsrfTokenRequestAttributeHandler();
    requestHandler.setCsrfRequestAttributeName("_csrf");

  /**
   *  From Spring Security 6, below actions will not happen by default,
   *  1) The Authentication details will not be saved automatically into SecurityContextHolder. To change this behaviour either we need to save
   *      these details explicitly into SecurityContextHolder or we can configure securityContext().requireExplicitSave(false) like shown below.
   *  2) The Session & JSessionID will not be created by default. Inorder to create a session after intial login, we need to configure
   *      sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.ALWAYS)) like shown below.
   */
    http
        .securityContext().requireExplicitSave(false)
      .and()
        .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.ALWAYS))
        .cors().configurationSource(request -> {
            CorsConfiguration config = new CorsConfiguration();
            config.setAllowedOrigins(List.of("http://localhost:4200"));
            config.setAllowedMethods(List.of("*"));
            config.setAllowCredentials(true);
            config.setAllowedHeaders(List.of("*"));
            config.setMaxAge(3600L);
            return config;
        })
    /**
     *  In Spring Security 5, the default behavior is that the CsrfToken will be loaded on every request. Where as with
     *  Spring Security 6, the default is that the lookup of the CsrfToken will be deferred until it is needed. The developer
     *  has to write logic to read the CSRF token and send it as part of the response. When framework sees the CSRF token
     *  in the response header, it takes care of sending the same as Cookie as well. For the same, we need to use CsrfTokenRequestAttributeHandler
     *  and create a filter with the name CsrfCookieFilter which runs every time after the Spring Security in built filter BasicAuthenticationFilter
     *  like shown below. More details about Filters, are discussed inside the Section 8 of the course.
     */
      .and()
        .csrf(csrf -> csrf
            .csrfTokenRequestHandler(requestHandler)
            .ignoringRequestMatchers("/register")
            .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
        )
        .addFilterAfter(new CsrfCookieFilter(), BasicAuthenticationFilter.class)
        .authorizeHttpRequests()
          /*.requestMatchers("/myAccount").hasAuthority("VIEWACCOUNT")
          .requestMatchers("/myBalance").hasAnyAuthority("VIEWACCOUNT", "VIEWBALANCE")
          .requestMatchers("/myLoans").hasAuthority("VIEWLOANS")
          .requestMatchers("/myCards").hasAuthority("VIEWCARDSX")*/
          .requestMatchers("/myAccount").hasRole("MANAGER")
          .requestMatchers("/myBalance").hasAnyRole("USER", "ADMIN")
          .requestMatchers("/myLoans").hasRole("USER")
          .requestMatchers("/myCards").hasRole("USER")
          .requestMatchers("/user").authenticated()
          .requestMatchers("/notices", "/contact", "/register").permitAll()
      .and().formLogin()
      .and().httpBasic();
    return http.build();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    // For approach 2 above
    return new BCryptPasswordEncoder();
  }
}
