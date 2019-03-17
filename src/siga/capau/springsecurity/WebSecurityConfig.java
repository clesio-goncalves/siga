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
			.antMatchers("/aluno/novo", "/aluno/adiciona", "/aluno/edita", "/aluno/altera")
				.hasAnyRole("Administrador", "Coordenador", "Diretor", "Psicologia", "Assistência Social", "Enfermagem", "Odontologia", "Docente", "Monitor", "Pedagogia", "Coordenação de Disciplina")
			.antMatchers("/aluno/remove")
				.hasRole("Administrador")
			.antMatchers("/atendimento/indisciplina/novo", "/atendimento/indisciplina/adiciona", "/atendimento/indisciplina/edita", "/atendimento/indisciplina/altera", "/atendimento/monitoria/remove")
				.hasRole("Coordenação de Disciplina")
			.antMatchers("/atendimento/indisciplina/lista", "/atendimento/indisciplina/exibe", "/atendimento/indisciplina/relatorio")
				.hasAnyRole("Administrador", "Coordenador", "Diretor", "Psicologia", "Assistência Social", "Enfermagem", "Odontologia", "Docente", "Pedagogia", "Coordenação de Disciplina")
			.antMatchers("/atendimento/pedagogia/aluno/novo", "/atendimento/pedagogia/aluno/adiciona", "/atendimento/pedagogia/aluno/edita", "/atendimento/pedagogia/aluno/altera", "/atendimento/pedagogia/aluno/remove")
				.hasRole("Pedagogia")
			.antMatchers("/atendimento/pedagogia/aluno/lista", "/atendimento/pedagogia/aluno/exibe", "/atendimento/pedagogia/aluno/relatorio")
				.hasAnyRole("Administrador", "Coordenador", "Diretor", "Psicologia", "Assistência Social", "Enfermagem", "Odontologia", "Docente", "Pedagogia", "Coordenação de Disciplina")
			.antMatchers("/atendimento/pedagogia/familia/novo", "/atendimento/pedagogia/familia/adiciona", "/atendimento/pedagogia/familia/edita", "/atendimento/pedagogia/familia/altera", "/atendimento/pedagogia/familia/remove")
				.hasRole("Pedagogia")
			.antMatchers("/atendimento/pedagogia/familia/lista", "/atendimento/pedagogia/familia/exibe", "/atendimento/pedagogia/familia/relatorio")
				.hasAnyRole("Administrador", "Coordenador", "Diretor", "Psicologia", "Assistência Social", "Enfermagem", "Odontologia", "Docente", "Pedagogia", "Coordenação de Disciplina")
			.antMatchers("/atendimento/monitoria/novo", "/atendimento/monitoria/adiciona", "/atendimento/monitoria/edita", "/atendimento/monitoria/altera", "/atendimento/monitoria/remove")
				.hasAnyRole("Administrador", "Coordenador", "Diretor", "Pedagogia", "Monitor")
			.antMatchers("/atendimento/monitoria/lista", "/atendimento/monitoria/exibe")
				.hasAnyRole("Administrador", "Coordenador", "Diretor", "Psicologia", "Assistência Social", "Enfermagem", "Odontologia", "Docente", "Monitor", "Pedagogia", "Coordenação de Disciplina")
			.antMatchers("/atendimento/saude/novo", "/atendimento/saude/adiciona", "/atendimento/saude/edita", "/atendimento/saude/altera", "/atendimento/saude/remove")
				.hasAnyRole("Psicologia", "Assistência Social", "Enfermagem", "Odontologia")
			.antMatchers("/atendimento/saude/lista", "/atendimento/saude/exibe", "/atendimento/saude/relatorio")
				.hasAnyRole("Administrador", "Coordenador", "Diretor", "Psicologia", "Assistência Social", "Enfermagem", "Odontologia", "Docente", "Pedagogia", "Coordenação de Disciplina")
			.antMatchers("/beneficio/novo", "/beneficio/adiciona", "/beneficio/lista", "/beneficio/exibe", "/beneficio/remove", "/beneficio/edita", "/beneficio/altera", "/beneficio/relatorio")
				.hasRole("Administrador")
			.antMatchers("/curso/novo", "/curso/adiciona", "/curso/edita", "/curso/altera", "/curso/remove")
				.hasAnyRole("Administrador", "Coordenador", "Diretor", "Pedagogia", "Coordenação de Disciplina")
			.antMatchers("/disciplina/nova", "/disciplina/adiciona", "/disciplina/edita", "/disciplina/altera", "/disciplina/remove")
				.hasAnyRole("Administrador", "Coordenador", "Diretor", "Pedagogia", "Coordenação de Disciplina")
			.antMatchers("/docente/novo", "/docente/adiciona", "/docente/edita", "/docente/altera", "/docente/remove")
				.hasAnyRole("Administrador", "Coordenador", "Diretor", "Pedagogia", "Coordenação de Disciplina")
			.antMatchers("/atendimento/extra-classe/novo", "/atendimento/extra-classe/adiciona", "/atendimento/extra-classe/edita", "/atendimento/extra-classe/altera", "/atendimento/extra-classe/remove")
				.hasAnyRole("Administrador", "Coordenador", "Diretor", "Pedagogia", "Docente")
			.antMatchers("/atendimento/extra-classe/lista", "/atendimento/extra-classe/exibe", "/atendimento/extra-classe/relatorio")
				.hasAnyRole("Administrador", "Coordenador", "Diretor", "Psicologia", "Assistência Social", "Enfermagem", "Pedagogia", "Odontologia", "Docente", "Coordenação de Disciplina")
			.antMatchers("/monitor/novo", "/monitor/adiciona", "/monitor/edita", "/monitor/altera", "/monitor/remove")
				.hasAnyRole("Administrador", "Coordenador", "Diretor", "Pedagogia", "Docente")
			.antMatchers("/perfil/lista", "/perfil/exibe")
				.hasRole("Administrador")
			.antMatchers("/profissional/novo", "/profissional/adiciona", "/profissional/edita", "/profissional/altera", "/profissional/remove")
				.hasAnyRole("Administrador", "Diretor")
			.antMatchers("/situacao/nova", "/situacao/adiciona", "/situacao/lista", "/situacao/exibe", "/situacao/remove", "/situacao/edita", "/situacao/altera", "/situacao/relatorio")
				.hasRole("Administrador")
			.antMatchers("/turma/nova", "/turma/adiciona", "/turma/edita", "/turma/altera", "/turma/remove")
				.hasAnyRole("Administrador", "Coordenador", "Diretor", "Pedagogia", "Coordenação de Disciplina")
			.antMatchers("/usuario/novo", "/usuario/adiciona", "/usuario/edita", "/usuario/altera", "/usuario/remove", "/usuario/lista", "/usuario/exibe")
				.hasAnyRole("Administrador", "Coordenador", "Diretor", "Psicologia", "Assistência Social", "Enfermagem", "Pedagogia", "Odontologia", "Docente", "Monitor", "Coordenação de Disciplina")
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
