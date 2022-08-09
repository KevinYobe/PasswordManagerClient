package com.passwordmanager.client.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;

import com.passwordmanager.client.authentication.UserDetailsServiceImpl;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	public UserDetailsServiceImpl userDetailsService;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth
			.userDetailsService(userDetailsService)
			.passwordEncoder(NoOpPasswordEncoder.getInstance());
			
			
	}
	
	@Override
	public void configure(HttpSecurity http) throws Exception {
		http
				.sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
				.sessionFixation().migrateSession()
				.invalidSessionUrl("/login")
				.and()
				
				.authorizeRequests()
				.antMatchers("/login", "/createaccount", "/confirmtoken/**", "/setpassword", "/confirm/**", "/resetpassword").permitAll()
				.antMatchers("/viewpassword", "/savePassword", "/addPassword", "/showeditpassword", "/editPassword", "/preferences").authenticated()
				.antMatchers("audit", "/showUser", "/addUser", "/removeUser").hasAuthority("ROLE_ADMIN")
				.anyRequest().authenticated()
				.and()

				.formLogin()
				.loginPage("/login")
				.permitAll()
				.defaultSuccessUrl("/viewpassword")
				.and()
				.logout()
				.logoutUrl("/logout")
				.permitAll();
	}
	
	 @Override
	    public void configure(WebSecurity web) {
	        web.ignoring()
	            .antMatchers("/css/**", "/js/**", "/images/**");
	        
	    }
	
}
