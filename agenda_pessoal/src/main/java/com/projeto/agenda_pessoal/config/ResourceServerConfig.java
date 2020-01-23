package com.projeto.agenda_pessoal.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@EnableWebSecurity
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

	@Autowired
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication()
		.withUser("admin")
		.password("admin")
		.roles("ROLES");
	}
	
	@Bean// codigo depreciado
	public UserDetailsService userDetailsService() {
	    @SuppressWarnings("deprecation")
		User.UserBuilder builder = User.withDefaultPasswordEncoder();
	    InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
	    manager.createUser(builder.username("admin").password("admin").roles("ROLE").build());
	    return manager;
	}
	@Override//qualquer uma requisição precisa estar autenticada, com usuario e senha
	public void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
		.antMatchers("/categorias").permitAll() // a requisição categoria qualquer um pode acessar
		.anyRequest().authenticated()
		.and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
		.and().csrf().disable();
	}
	@Override
	public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
		resources.stateless(true);
	}
}
