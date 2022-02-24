package fr.formation.inti.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
        	.csrf().disable()
        	.headers().frameOptions().sameOrigin()
        	.and()
	        .authorizeRequests()
		        .anyRequest()
		        .permitAll()
//		        .and()
//		    .formLogin()
//		        .loginPage("/login")
//		        .permitAll()
//		        .and()
//		    .logout()
//		        .logoutUrl("/logout")
//		        .logoutSuccessUrl("/login")
		        ;
    }
}