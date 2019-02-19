package ru.kentyku.springbootactiviti;

import org.activiti.engine.IdentityService;
import org.activiti.engine.identity.User;
import org.activiti.rest.security.BasicAuthenticationProvider;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
@Order(99)
class SecurityAutoConfiguration extends WebSecurityConfigurerAdapter {

    private static final String ADMIN = "admin";

    @Bean
    InitializingBean usersAndGroupsInitializer(final IdentityService identityService) {
        return () -> {
            if (identityService.createUserQuery().userId(ADMIN).singleResult() == null) {
                User admin = identityService.newUser(ADMIN);
                admin.setPassword(ADMIN);
                identityService.saveUser(admin);
            }
        };
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        return new BasicAuthenticationProvider();
    }

    /*@Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authenticationProvider(authenticationProvider())
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/version").permitAll()
                .anyRequest().authenticated()
                .and()
                .httpBasic();
    }*/
}