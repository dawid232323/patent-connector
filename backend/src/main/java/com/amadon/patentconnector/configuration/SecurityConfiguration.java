package com.amadon.patentconnector.configuration;

import com.amadon.patentconnector.security.service.JWTRequestFilter;
import com.amadon.patentconnector.shared.constants.AppEndpoints;
import com.amadon.patentconnector.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Slf4j
@EnableWebSecurity
@EnableScheduling
@Configuration
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfiguration
{
	private final UserService userService;
	private final JWTRequestFilter requestFilter;

	@Bean
	public SecurityFilterChain securityFilterChain( final HttpSecurity aHttp ) throws Exception
	{
		disableCsrf( aHttp );
		excludeUrlsFromAuthentication( aHttp );
		setAuthorizedRequestWithStatelessSessions( aHttp );
		setJWTBeforeFilter( aHttp );

		return aHttp.build();
	}

	private void disableCsrf( final HttpSecurity aHttp ) throws Exception
	{
		aHttp.csrf( AbstractHttpConfigurer::disable );
	}

	private void excludeUrlsFromAuthentication( final HttpSecurity aHttp ) throws Exception
	{
		AppEndpoints.getExcludedEndpoints()
				.forEach( endpoint ->
				{
					try
					{
						aHttp.authorizeHttpRequests( request -> request.requestMatchers( new AntPathRequestMatcher( endpoint ) )
								.permitAll() );
						log.info( "Added {} to urls excluded from authentication", endpoint );
					} catch ( Exception aE )
					{
						throw new RuntimeException( aE );
					}
				} );
	}

	private void setAuthorizedRequestWithStatelessSessions( final HttpSecurity aHttp ) throws Exception
	{
		aHttp.authorizeHttpRequests( request -> request.anyRequest()
						.authenticated() )
				.sessionManagement( session -> session.sessionCreationPolicy( SessionCreationPolicy.STATELESS ) );
	}

	private void setJWTBeforeFilter( final HttpSecurity aHttp )
	{
		aHttp.addFilterBefore( requestFilter, UsernamePasswordAuthenticationFilter.class );
	}
}
