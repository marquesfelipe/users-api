package br.com.ftech.users.conf;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import br.com.ftech.users.security.RestAuthenticationEntryPoint;
import br.com.ftech.users.security.RestAuthenticationFailureHandler;
import br.com.ftech.users.security.RestAuthenticationLogoutHandler;
import br.com.ftech.users.security.RestAuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
	
	@Autowired
    private RestAuthenticationEntryPoint restAuthenticationEntryPoint;
    @Autowired
    private RestAuthenticationFailureHandler restAuthenticationFailureHandler;
    @Autowired
    private RestAuthenticationSuccessHandler restAuthenticationSuccessHandler;
   

	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    	auth.inMemoryAuthentication()
        .withUser("user").password("user").roles("USER").and()
        .withUser("admin").password("admin").roles("ADMIN");
    	auth.userDetailsService(userDetailsService).
    	passwordEncoder(bCryptPasswordEncoder); 
    }
 
    @Override
    protected void configure(HttpSecurity http) throws Exception {
    	http.
    	csrf().disable()
    	.exceptionHandling()
        .authenticationEntryPoint(restAuthenticationEntryPoint)
        .and()
        .authorizeRequests()
        .anyRequest().authenticated()
       	.and()
    		.formLogin()
    		.successHandler(restAuthenticationSuccessHandler)
    		.failureHandler(restAuthenticationFailureHandler)
    	.and()
        .logout().and().sessionManagement().maximumSessions(1);
    }
    
    @Bean
	public BCryptPasswordEncoder passwordEncoder() {
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
		return bCryptPasswordEncoder;
	}

}