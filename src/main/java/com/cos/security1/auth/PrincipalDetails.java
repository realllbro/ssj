package com.cos.security1.auth;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.cos.security1.model.User;

import lombok.Data;
import lombok.Getter;

/**
 * Authentication 객체에 저장할 수 있는 유일한 타입
 * 시큐리티가 /login 주소 요청이 오면 낚아채서 로그인을 진행시킨다.
 * 로그인을 진행이 완료가 되면 시큐리티 session을 만들어준다. (SecurityContextHolder)
 * 시큐리티가 가지고 있는 세션에 들어갈 수 있는 오브젝트는 정해져 있다.
 *   => Authentication 타입 객체
 *   
 * Authentication 객체는 UserDetails 타입 객체가 들어가야 한다.
 * User 오브젝트타입 => UserDetails 타입 객체
 * 
 * 즉, Security Session <= Authentication <= UserDetails 상속받아 PrincipalDetails 을 구현한다.
 * 
 */
//@Data
public class PrincipalDetails implements UserDetails{

	private User user;

	public PrincipalDetails(User user) {
		super();
		this.user = user;
	}
	
	/**
	 * 해당유저의 권한을 리턴한다.
	 * GrantedAuthority 타입
	 */
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Collection<GrantedAuthority> collet = new ArrayList<GrantedAuthority>();
		collet.add(()->{ 
				return user.getRole();
			}
		);
		return collet;
	}	
	
	@Override
	public String getPassword() {
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		return user.getUsername();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		// 사용로직 예 
		// 우리 사이트 1년동안 회원이 로그인을 안하면 휴먼 계정으로 하기로함
		// 현재시간 - 로긴시간 => 1년을 초과하면 return false;
		return true;
	}
	



	
}
