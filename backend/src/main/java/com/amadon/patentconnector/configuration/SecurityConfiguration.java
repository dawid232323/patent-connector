package com.amadon.patentconnector.configuration;

import com.amadon.patentconnector.security.service.JWTRequestFilter;
import com.amadon.patentconnector.security.service.permissionEvaluator.EntityTypePermissionEvaluatorStrategy;
import com.amadon.patentconnector.security.service.permissionEvaluator.ObjectBasedPermissionEvaluator;
import com.amadon.patentconnector.shared.constants.AppEndpoints;
import com.amadon.patentconnector.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;
import java.util.List;

@Slf4j
@EnableWebSecurity
@EnableScheduling
@Configuration
@EnableMethodSecurity
public class SecurityConfiguration
{
	private final UserService userService;
	private final JWTRequestFilter requestFilter;
	private final List< String > allowedHosts;
	private final List< EntityTypePermissionEvaluatorStrategy > permissionEvaluatorStrategies;

	public SecurityConfiguration( @Value( "${spring.application.cors.allowed-origins}" ) final List< String > aAllowedHosts, final UserService aUserService,
								  final JWTRequestFilter aRequestFilter,
								  final List< EntityTypePermissionEvaluatorStrategy > permissionEvaluatorStrategies )
	{
		allowedHosts = aAllowedHosts;
		userService = aUserService;
		requestFilter = aRequestFilter;
		this.permissionEvaluatorStrategies = permissionEvaluatorStrategies;
	}

	@Bean
	public SecurityFilterChain securityFilterChain( final HttpSecurity aHttp ) throws Exception
	{
		setCorsConfiguration( aHttp );
		disableCsrf( aHttp );
		setExceptionHandling( aHttp );
		excludeUrlsFromAuthentication( aHttp );
		setAuthorizedRequestWithStatelessSessions( aHttp );
		setJWTBeforeFilter( aHttp );

		return aHttp.build();
	}

	@Bean
	public CorsConfigurationSource corsConfigurationSource()
	{
		final CorsConfiguration corsConfiguration = new CorsConfiguration();
		corsConfiguration.setAllowedOrigins( allowedHosts );
		corsConfiguration.setAllowedMethods( Arrays.asList( HttpMethod.GET.name(), HttpMethod.POST.name(),
															HttpMethod.PUT.name(),
															HttpMethod.DELETE.name() ) );
		corsConfiguration.setAllowedHeaders( Arrays.asList( "*" ) );
		corsConfiguration.setAllowCredentials( true );

		final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration( "/**", corsConfiguration );

		return source;
	}

	@Bean
	public MethodSecurityExpressionHandler permissionEvaluator() {
		final DefaultMethodSecurityExpressionHandler expressionHandler =
				new DefaultMethodSecurityExpressionHandler();
		expressionHandler.setPermissionEvaluator( new ObjectBasedPermissionEvaluator( permissionEvaluatorStrategies ) );
		return expressionHandler;
	}

	private void setCorsConfiguration( final HttpSecurity aSecurity ) throws Exception
	{
		log.info( "Setting allowed hosts to: {}", allowedHosts );
		aSecurity.addFilterBefore( new CorsFilter( corsConfigurationSource() ), CorsFilter.class );
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

	private void setExceptionHandling( final HttpSecurity aSecurity ) throws Exception
	{
		aSecurity.exceptionHandling( request -> request.authenticationEntryPoint( new AppAuthenticationEntryPoint() ) )
				.exceptionHandling( request -> request.accessDeniedHandler( new AppAccessDeniedHandler() ) );
	}
}
