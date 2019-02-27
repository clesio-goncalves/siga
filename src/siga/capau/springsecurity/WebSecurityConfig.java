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
			.antMatchers("/administracao/novo", "/administracao/adiciona", "/administracao/edita", "/administracao/altera", "/administracao/remove")
				.hasAnyRole("Administrador", "Diretor")
			.antMatchers("/administracao/lista", "/administracao/exibe")
				.hasAnyRole("Administrador", "Diretor", "Coordenador")
			.antMatchers("/aluno/novo", "/aluno/adiciona", "/aluno/edita", "/aluno/altera", "/aluno/remove", "/aluno/lista", "/aluno/exibe")
				.hasAnyRole("Administrador", "Coordenador", "Diretor", "Psicologia", "Assistência Social", "Enfermagem", "Odontologia", "Docente", "Monitor", "Pedagogia", "Coordenação de Disciplina")
			.antMatchers("/atendimento/monitoria/novo", "/atendimento/monitoria/adiciona", "/atendimento/monitoria/edita", "/atendimento/monitoria/altera", "/atendimento/monitoria/remove")
				.hasAnyRole("Administrador", "Coordenador", "Diretor", "Pedagogia", "Monitor")
			.antMatchers("/atendimento/monitoria/lista", "/atendimento/monitoria/exibe")
				.hasAnyRole("Administrador", "Coordenador", "Diretor", "Psicologia", "Assistência Social", "Enfermagem", "Odontologia", "Docente", "Monitor", "Pedagogia", "Coordenação de Disciplina")
			.antMatchers("/atendimento/saude/novo", "/atendimento/saude/adiciona", "/atendimento/saude/edita", "/atendimento/saude/altera", "/atendimento/saude/remove")
				.hasAnyRole("Psicologia", "Assistência Social", "Enfermagem", "Odontologia")
			.antMatchers("/atendimento/saude/lista", "/atendimento/saude/exibe")
				.hasAnyRole("Administrador", "Coordenador", "Diretor", "Psicologia", "Assistência Social", "Enfermagem", "Odontologia", "Docente", "Monitor", "Pedagogia", "Coordenação de Disciplina")
				
				// parei em Atendimento Saude
				
			.antMatchers("/atendimento/extra-classe/novo", "/atendimento/extra-classe/adiciona", "/atendimento/extra-classe/edita", "/atendimento/extra-classe/altera", "/atendimento/extra-classe/remove")
				.hasAnyRole("Administrador", "Coordenador", "Diretor", "Docente")
			.antMatchers("/usuario/novo", "/usuario/adiciona", "/usuario/edita", "/usuario/altera", "/usuario/remove")
				.hasAnyRole("Administrador", "Coordenador", "Diretor", "Psicologia", "Assistência Social", "Enfermagem", "Odontologia", "Docente", "Monitor", "Pedagogia")
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
