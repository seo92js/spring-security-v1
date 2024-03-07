package com.cos.security1.config.auth;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.cos.security1.model.User;

// 시큐리티가 /login 주소 요청이 오 낚아채서 로그인을 진행시킨다.
// 로그인을 진행이 완료가 되면 시큐리티 Session을 만들어줍니다. (Security ContetxtHolder)
// 오브젝트 -> Authentication 타입 객체
// Authentication 안에 User 정보가 있어야 됨. 
// User 오브젝트 타입 -> UserDetails 타입 객체 

// Security Session -> Authentication -> UserDetails
public class PrincipalDetails implements UserDetails{
	
	private User user;
	
	public PrincipalDetails(User user) {
		this.user = user;
	}

	//해당 User의 권한을 리턴하는 곳 
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Collection<GrantedAuthority> collect = new ArrayList<>();
		collect.add(new GrantedAuthority() {

			@Override
			public String getAuthority() {
				// TODO Auto-generated method stub
				return user.getRole();
			}
			
		});
		return collect;
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return user.getUsername();
	}

	// 계정 만료 되었니?
	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	// 계정 잠겼니
	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// 우리사이트에서 1년동안 회원이 로그인을 안하면 , 휴면 계쩡으로 하기로 함.		
		// 예를 들어 현재시간 - 로긴시간 => 1년을 초과하면 return false;
		
		return true;
	}

}
