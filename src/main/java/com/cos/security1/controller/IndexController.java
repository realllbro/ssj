package com.cos.security1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cos.security1.model.User;
import com.cos.security1.repository.UserRepository;

//View(화면)를 리턴.
@Controller 
public class IndexController {
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;

	/**
	 * localhost:8080
	 * 머스테치 기본폴더 src/main/resources/
 	 * 뷰 리졸버 설정: 
 	 *   templates(prefix), 
 	 * 	 .mustache(suffix) 생략가능!!
	 * @return
	 */
	@GetMapping({"","/"})
	public String index() {
		return "index"; //src/main/resources/templates/index.mustache
	}
	
	@GetMapping("/user")
	public @ResponseBody String user() {
		return "user";
	}
	
	@GetMapping("/admin")
	public @ResponseBody String admin() {
		return "admin";
	}
	
	@GetMapping("/manager")
	public @ResponseBody String manager() {
		return "manager";
	}	
	
	/**
	 * 스프링 시큐리티 해당주소를 인터셉터 한다.
	 * SecurityConfig 파일 생성해서 설정을 커스텀마이징 하면
	 * 스프링에서 처리 안한다.
	 * @return
	 */
	@GetMapping("/login")
	public @ResponseBody String login() {
		return "login";
	}	
	
	@GetMapping("/loginForm")
	public String loginForm() {
		return "loginForm";
	}	
	
	@GetMapping("/joinForm")
	public String joinForm() {
		return "joinForm";
	}		
	
	@PostMapping("/join")
	public String join(User user) {
		System.out.println(user);
		
		// 패스워드 암호화가 안 되어 있으면 시큐리티로 로그인을 할 수 없어
		// 비밀번호를 암호화 해준다.
		String rawPassword = user.getPassword();
		String encPassword = bCryptPasswordEncoder.encode(rawPassword);
		
		user.setPassword(encPassword);
		user.setRole("ROLE_USER");
		
		userRepository.save(user); 
		
		return "redirect:/loginForm";
	}	
	
	/**
	 * @Secured 메서드에 권한처리 할때 
	 * @return
	 */
	@Secured("ROLE_ADMIN")
	@GetMapping("/info")
	public @ResponseBody String info() {
		return "개인정보";
	}
	
	/**
	 * @PreAuthorize 메서드 타기 전에 권한체크함
	 * @postAuthorize 메서드 타고난 후 권한체크함 
	 * @return
	 */
	//@PostAuthorize()
	@PreAuthorize("hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")
	@GetMapping("/data")
	public @ResponseBody String data() {
		return "데이터정보";
	}	
	
}