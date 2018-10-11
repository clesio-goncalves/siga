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
			.antMatchers("/atendimento/extra-classe/novo", "/atendimento/extra-classe/adiciona", "/atendimento/extra-classe/edita", "/atendimento/extra-classe/altera", "/atendimento/extra-classe/remove")
				.hasAnyRole("Administrador", "Coordenador", "Diretor", "Docente")
			.antMatchers("/atendimento/monitoria/novo", "/atendimento/monitoria/adiciona", "/atendimento/monitoria/edita", "/atendimento/monitoria/altera", "/atendimento/monitoria/remove")
				.hasAnyRole("Administrador", "Coordenador", "Diretor", "Monitor")
			.antMatchers("/atendimento/saude/novo", "/atendimento/saude/adiciona", "/atendimento/saude/edita", "/atendimento/saude/altera", "/atendimento/saude/remove")
				.hasAnyRole("Psicologia", "Assistência Social", "Enfermagem", "Odontologia")
			.antMatchers("/usuario/novo", "/usuario/adiciona", "/usuario/edita", "/usuario/altera", "/usuario/remove")
				.hasAnyRole("Administrador", "Coordenador", "Diretor", "Psicólogo", "Assistente Social", "Enfermeiro", "Odontólogo", "Docente", "Monitor", "Pedagogia")
			.antMatchers("/aluno/novo", "/aluno/adiciona", "/aluno/edita", "/aluno/altera", "/aluno/remove")
				.hasAnyRole("Administrador", "Coordenador", "Diretor", "Psicólogo", "Assistente Social", "Enfermeiro", "Odontólogo", "Docente", "Monitor", "Pedagogia")
			.antMatchers("/docente/novo", "/docente/adiciona", "/docente/edita", "/docente/altera", "/docente/remove")
				.hasAnyRole("Administrador", "Coordenador", "Diretor")
			.antMatchers("/monitor/novo", "/monitor/adiciona", "/monitor/edita", "/monitor/altera", "/monitor/remove")
				.hasAnyRole("Administrador", "Coordenador", "Diretor", "Docente")
			.antMatchers("/profissional/novo", "/profissional/adiciona", "/profissional/edita", "/profissional/altera", "/profissional/remove")
				.hasAnyRole("Administrador", "Diretor")
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
