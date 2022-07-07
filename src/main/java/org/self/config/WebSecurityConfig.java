package org.self.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.csrf.CsrfAuthenticationStrategy;
import org.springframework.security.web.csrf.CsrfLogoutHandler;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.security.web.firewall.HttpFirewall;
import org.springframework.security.web.firewall.StrictHttpFirewall;

import lombok.extern.slf4j.Slf4j;

@Configuration
@EnableWebSecurity
@Slf4j
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	private UserDetailsService userDetailsService;
	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {

		httpSecurity
		  .addFilterAt(logoutFilter(),LogoutFilter.class)
		  .addFilterAt(authenticationFilter(),UsernamePasswordAuthenticationFilter.class)
	      .userDetailsService(this.userDetailsService)
		  .authorizeRequests()
          .antMatchers("/login*","/css/**","/js/**","/registration").permitAll()
          .anyRequest().authenticated()
	      .and()
	      .formLogin()
	      .loginPage("/login")
	      .and()
	      .logout()
	      .and()
          .sessionManagement()
		  .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	}
	@Bean HttpSessionCsrfTokenRepository csrfTokenRepository() {
		return new HttpSessionCsrfTokenRepository();
	}
	@Bean
    public SimpleLogOutFilter logoutFilter() {
		LogoutHandler[] handlers = {new CsrfLogoutHandler(csrfTokenRepository())};
		SimpleLogOutFilter filter = new SimpleLogOutFilter("/login",handlers);
		return filter;
    }
	@Bean
	public HttpFirewall configureFirewall() {
	    StrictHttpFirewall strictHttpFirewall = new StrictHttpFirewall();
	    strictHttpFirewall.setAllowBackSlash(true);
	    strictHttpFirewall.setAllowedHttpMethods(Arrays.asList("GET","POST","DELETE", "OPTIONS"));
	    strictHttpFirewall.setAllowNull(true);
	    strictHttpFirewall.setAllowSemicolon(true);
	    strictHttpFirewall.setAllowUrlEncodedDoubleSlash(true);
	    strictHttpFirewall.setAllowUrlEncodedPercent(true);
	    strictHttpFirewall.setAllowUrlEncodedSlash(true);
	    strictHttpFirewall.setAllowUrlEncodedPeriod(true);
	    return strictHttpFirewall;
	}
	@Bean
    public SimpleAuthenticationFilter authenticationFilter() {
		AuthenticationManager authManager = null;
		try {
			authManager = super.authenticationManagerBean();
		} catch (Exception ex) {
			log.error("Error creating authentication manager {}",ex);
		}
		log.info("Created authentication manager {}",authManager.toString());
		SimpleAuthenticationFilter filter = new SimpleAuthenticationFilter(authManager);
		filter.setApplicationEventPublisher(null);
		filter.setFilterProcessesUrl("/performLogin");
		filter.setPostOnly(true);
		filter.setSessionAuthenticationStrategy(new CsrfAuthenticationStrategy(csrfTokenRepository()));
		filter.setAuthenticationSuccessHandler(new CustomAuthenticationSuccessHandler());
		filter.setContinueChainBeforeSuccessfulAuthentication(false);
		filter.setAuthenticationFailureHandler(new CustomAuthenticationFailureHandler());
		filter.setUsernameParameter("username");
		filter.setPasswordParameter("password");
		filter.afterPropertiesSet();
        return filter;
    }
	@Override
    public void configure(WebSecurity web) {
		web.ignoring().antMatchers("/resources/**", "/static/**");
    }
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}