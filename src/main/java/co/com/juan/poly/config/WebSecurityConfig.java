package co.com.juan.poly.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.thymeleaf.extras.springsecurity4.dialect.SpringSecurityDialect;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private CustomUserDetailsService customUserDetailsService;
	
	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	// Adds SpringSecurity dialect to the Template Engine (Thymeleaf)
	@Bean
	public SpringSecurityDialect securityDialect() {
	    return new SpringSecurityDialect();
	}
	
	@Autowired
	public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(customUserDetailsService).passwordEncoder(bCryptPasswordEncoder());
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.authorizeRequests()
				.antMatchers("/resources/**").permitAll()
				.antMatchers("/css/**").permitAll()
				.antMatchers("/js/**").permitAll()
				//.antMatchers("/**").hasRole("ADMIN")
				//.antMatchers("/menu", "/administrative/**", "/district/**", "/operative/**", "/position/**", "/rank/**").hasRole("USER")
				.anyRequest().authenticated()
				.and()
			.formLogin()
				.loginPage("/login")
				.usernameParameter("username")
				.passwordParameter("password")
				.defaultSuccessUrl("/menu", true)
				.permitAll()
				.and()
			.logout()
				.permitAll()
				.and()
			.exceptionHandling()
				.accessDeniedPage("/access-denied")
				.and()
			.csrf();
	}
	
/*	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {		
		  auth
          .inMemoryAuthentication()
              .withUser("user").password("password").roles("USER");
		  auth
          .inMemoryAuthentication()
              .withUser("admin").password("password").roles("USER", "ADMIN");
	 }*/
	
}
