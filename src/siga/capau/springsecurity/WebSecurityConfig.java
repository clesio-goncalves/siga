package siga.capau.springsecurity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import siga.capau.dao.UsuarioDao;

@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private UsuarioDao userDetailsService;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.
			authorizeRequests()
			//.antMatchers("/usuario/novo", "/usuario/adiciona", "/usuario/edita", "/usuario/altera", "/usuario/remove")
				//.hasRole("Administrador")
			.anyRequest()
			.authenticated()
		.and()
		.formLogin()
			.loginPage("/login")
			.permitAll()
		.and()
			.rememberMe()
		.and()
			.logout()
			.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
			.permitAll();
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());
	}
	
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/resources/**");
	}

}
