package fr.mspr.webshop.security.utils;

import fr.mspr.webshop.security.jwt.ApiKeyFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.DispatcherType;


@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private static final String[] AUTH_WHITELIST = {
            "/swagger-resources/**",
            "/swagger-ui.html/**",
            "/swagger-ui/**",
            "/v2/api-docs",
            "/api/auth/**"
    };

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        ApiKeyFilter apiKeyFilter = new ApiKeyFilter();

        // Enable CORS and disable CSRF
        http = http.cors().and().csrf().disable();

        // Set session management to stateless
        http = http
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and();

        http.authorizeRequests()
                .anyRequest().permitAll();

        //token filter
        http.addFilterAfter(apiKeyFilter, BasicAuthenticationFilter.class);

    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web -> web.ignoring()
                .dispatcherTypeMatchers(DispatcherType.ERROR)
                .antMatchers(AUTH_WHITELIST);
    }
}

