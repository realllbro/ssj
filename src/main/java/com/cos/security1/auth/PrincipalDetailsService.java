package com.cos.security1.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.cos.security1.model.User;
import com.cos.security1.repository.UserRepository;

/**
 * 시큐리티 설정에서 loginProcessingUrl("/login");
 * "/login" 요청이 오면 자동으로 UserDetailsService 타입으로 IoC되어 있는 loadUserByUsername 함수가 실행된다.
 */
@Service
public class PrincipalDetailsService implements UserDetailsService{

	@Autowired
	private UserRepository userRepository;
	
	/**
	 *  (String username) 화면에서 넘어오는 HTML 태그 요소 명칭
	 *  시큐리티 session = Authentication = UserDetails
	 *  리턴되면 만들어 지는 객체 => 시큐리티 session(내부 Authentication(내부 UserDetails[PrincipalDetails])) 
	 */
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUsername(username);
		if(user == null) {
			return null;
		}
		return new PrincipalDetails(user);
	}

}
