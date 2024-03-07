package com.cos.security1.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity //스프링 시큐리티 필터가 스프링 필터체인에 등록이 됨
@EnableMethodSecurity(securedEnabled = true, prePostEnabled = true) // secured 어노테이션 활성화 , preAuthorize , postAuthorize 어노테이션 활성화 
public class SecurityConfig {
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		return 
				http
				.csrf(cs -> cs.disable())
				.authorizeHttpRequests(authorize -> authorize
					.requestMatchers("/user/**").authenticated()
					.requestMatchers("/manager/**").hasAnyRole("ADMIN", "MANAGER")
					.requestMatchers("/admin/**").hasRole("ADMIN")
					.anyRequest().permitAll())
				.formLogin(f -> f
						//.usernameParameter("username2") // 이렇게 변경도 가능
						.loginPage("/loginForm")
						.loginProcessingUrl("/login")// login주소가 호출이 되면 시큐리티가 낚아채서 대신 로그인을 진행해준다. 컨트롤러에 /login을 안만들어도 
						.defaultSuccessUrl("/")) 
				.build();
	}
	
	@Bean
	public BCryptPasswordEncoder encodePwd() {
		return new BCryptPasswordEncoder();
	}
}
