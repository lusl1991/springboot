package com.softel.springboot.provider;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
import com.softel.springboot.entity.UserEntity;
import com.softel.springboot.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class CustomAuthenticationProvider implements AuthenticationProvider {
	
	@Autowired
    private HttpSession session;
	
	@Autowired
	private UserRepository userRepository;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String username = authentication.getName();
        String password = authentication.getCredentials().toString();
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        // 检查用户名密码是否正确
        UserEntity user = userRepository.findByUsername(username);
        if(user == null){
            log.error("{} login failed, username or password is wrong", username);
            throw new BadCredentialsException("Username or password is not correct");
        }
        // 用户信息有效时将其放入 session 中
        session.setAttribute("user", user);
        Authentication auth = new UsernamePasswordAuthenticationToken(username, password, grantedAuthorities);
        return auth;
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}

}
