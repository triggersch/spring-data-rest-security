package com.apress.hellorest.security;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.apress.hellorest.domain.User;
import com.apress.hellorest.repository.UserRepository;


@Component
public class QuickPollUserDetailsService implements UserDetailsService {

	@Inject
	private UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		User user = userRepository.findByUsername(username);
		
		if(user == null)
			throw new UsernameNotFoundException(String.format("L'utilisateur %s n'existe pas", username));
		
		List<GrantedAuthority> authorities = new ArrayList<>();
		if(user.isAdmin())
			authorities = AuthorityUtils.createAuthorityList("ROLE_ADMIN");
		
		UserDetails userDetails = new org.springframework.security.core.userdetails.User(user.getUsername(),
				                  user.getPassword(),authorities);
		return userDetails;
	}

}
