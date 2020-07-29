package com.avocado.fruit.config.app;

import com.avocado.fruit.config.logouthandler.LogoutSuccessHandlerImpl;
import com.avocado.fruit.security.AuthenticationFilter;
import com.avocado.fruit.security.AuthorizationFilter;
import com.avocado.fruit.service.JwtService;
import com.avocado.fruit.service.impl.EmployeeServiceImpl;
import com.avocado.fruit.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final EmployeeServiceImpl employeeService;

    private final BCryptPasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    private final JwtUtil jwtUtil;

    private final LogoutSuccessHandlerImpl logoutSuccessHandler;

    @Autowired
    public WebSecurityConfig(EmployeeServiceImpl employeeService, BCryptPasswordEncoder passwordEncoder, JwtService jwtService,
                             JwtUtil jwtUtil, LogoutSuccessHandlerImpl logoutSuccessHandler) {
        this.employeeService = employeeService;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.jwtUtil = jwtUtil;
        this.logoutSuccessHandler = logoutSuccessHandler;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors().and().csrf().disable()
                .headers().frameOptions().disable()
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.OPTIONS).permitAll()
                .antMatchers(HttpMethod.POST, "/login").permitAll()
                .antMatchers(HttpMethod.POST, "/v1/api/employees").permitAll()
                .antMatchers(HttpMethod.GET, "/v1/api/roles").permitAll()
                .antMatchers(HttpMethod.PUT, "/v1/api/employees/{employeeId}/roles/{roleId}").permitAll()
                .anyRequest().authenticated()
                .and()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessHandler(logoutSuccessHandler)
                .and()
                // Authentication filter, this will intercept request path for login ("/login").
                .addFilter(new AuthenticationFilter(authenticationManager(), jwtService, jwtUtil))
                // Authorization filter.
                .addFilter(new AuthorizationFilter(authenticationManager(), employeeService, jwtService, jwtUtil))
                // This disables session creation on Spring Security
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    /**
     * Use the {@link UserDetailsService} to verify user.
     *
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(employeeService).passwordEncoder(passwordEncoder);
    }

    @Bean
    public CorsConfigurationSource configurationSource() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        // Permit dto from different origin.
        source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
        return source;
    }
}
