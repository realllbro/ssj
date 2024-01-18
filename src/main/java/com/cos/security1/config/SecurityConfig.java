package com.cos.security1.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


/**
 * @EnableWebSecurity 
 * 스프링 시큐리티 필터가 스프링 필터체인에 등록이 된다.
 * 
 * @EnableGlobalMethodSecurity(securedEnabled =true)
 *   - secured 어노테이션 활성화, 컨트롤러에서 접근가능
 * @EnableGlobalMethodSecurity(prePostEnabled = true)  
 *   - preAuthorize, postAuthorize 어노테이션 활성화 컨트롤러에서 접근가능
 */
@Configuration
@EnableWebSecurity 
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)   
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	/**
	 * @Bean : 빈으로 등록되어 
	 * 해당 메서드의 리턴되는 오브젝트를 IoC로 등록해준다.
	 * @return
	 */
	@Bean
	public BCryptPasswordEncoder encodePwd() {
		return new BCryptPasswordEncoder();
	}
	
	
	/**
	 * authenticated() 인증만 되면 들어갈 수 있는 주소 !! 인증필요 로그인한 사람만 들어올 수 있다.
	 * access() 권한까지 체크
	 * permitAll() 다른 요청은 그냥 허락.
	 * 권한없는데 요청이 들어오면 로그인 페이지로 이동 
	 *   - .and().formLogin().loginPage("/loginForm") 
	 *   
	 * .usernameParameter("username2") 
	 * 	- 화면에 HTML 태그 요소명을 다르게 쓸때   
	 *   
	 * .loginProcessingUrl("/login")
	 * 	- login 주소가 호출이 되면 시큐리티가 낚아채서 대신 로그인을 진행해 준다. 
	 *    (컨트롤러에서 login생략 가능)
	 * .defaultSuccessUrl("/"); 
	 * 	- // 로그인 성공하면 이동할 화면 (추가정보:로그인 화면을 뛰운 페이지로 되돌아간다.)	
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception{
		http.csrf().disable();
		http.authorizeRequests()
			.antMatchers("/user/**").authenticated()
			.antMatchers("/manager/**").access("hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER')")
			.antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')")
			.anyRequest().permitAll()
			.and()
			.formLogin()
			.loginPage("/loginForm")
			// .usernameParameter("username2")
			.loginProcessingUrl("/login")
			.defaultSuccessUrl("/"); 	   
	}
	

}
