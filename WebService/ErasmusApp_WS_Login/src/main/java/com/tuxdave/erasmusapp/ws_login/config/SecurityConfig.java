package com.tuxdave.erasmusapp.ws_login.config;

import com.tuxdave.erasmusapp.ws_login.security.AuthEntryPoint;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Bean //attiva il password encryptor
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    private static final String REALM = AuthEntryPoint.REALM;
    private static final Map<String, String[]> ROLE_MATCHER = new HashMap<String, String[]>();
    static{
        ROLE_MATCHER.put("USER", new String[]{"/api/**"});
        ROLE_MATCHER.put("ADMIN", new String[]{"/**"});
    }

    @Bean
    @Override
    public UserDetailsService userDetailsService(){
        User.UserBuilder users = User.builder();
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager(); //inseriamo hardcoded le specifiche degli utenti
        //e mantenute in ram nel processo server

        manager.createUser(
            users
                .username("ErasmusAppWebServiceUser")
                .password(passwordEncoder().encode("gz%s2QPuhr^Hz@WKUVM&6"))
                    //TODO: Scrivere queste password criptate da qualche parte, probabilmente va cambiato il AuthType
                .roles("USER")
                .build()
        );
        manager.createUser(
            users
                .username("ADMIN")
                .password(passwordEncoder().encode("REXW9zmFj7crp45HixW@c"))
                .roles("USER", "ADMIN")
                .build()
        );
        return manager;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers(ROLE_MATCHER.get("USER")).hasAnyRole("USER")
                .antMatchers(ROLE_MATCHER.get("ADMIN")).hasAnyRole("ADMIN")
                //.anyRequest().authenticated()
                .and()
                .httpBasic().realmName(REALM).authenticationEntryPoint(getBasicAuthEntryPoint())
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        //per fare in modo che alcuni pattern siano accedibili senza AUTH
        web.ignoring().antMatchers(
                "/v2/api-docs",
                "/swagger-resources",
                "/swagger-resources/**",
                "/configuration/ui",
                "/configuration/security",
                "/swagger-ui.html",
                "/webjars/**"
        );
    }

    @Bean
    public AuthEntryPoint getBasicAuthEntryPoint(){
        return new AuthEntryPoint();
    }
}
