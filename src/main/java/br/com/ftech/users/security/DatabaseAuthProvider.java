package br.com.ftech.users.security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import br.com.ftech.users.model.Role;
import br.com.ftech.users.model.User;
import br.com.ftech.users.repository.UserRepository;
@Component
public class DatabaseAuthProvider extends AbstractUserDetailsAuthenticationProvider {

	@Autowired
	private UserRepository userRepository;

	@Override
	protected void additionalAuthenticationChecks(UserDetails userDetails,
			UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
	}

	@Override
	protected UserDetails retrieveUser(String email, UsernamePasswordAuthenticationToken authentication)
			throws AuthenticationException {
		User user = userRepository.findByEmail(email);
		if (user == null) {
			throw new UsernameNotFoundException(email);
		}

		UserLogged userLogged = new UserLogged();
		userLogged.setId(user.getId());
		userLogged.setName(user.getName());
		userLogged.setUsername(user.getEmail());
		userLogged.setPassword(user.getPassword());
		userLogged.setAccountNonExpired(true);
		userLogged.setAccountNonLocked(true);
		userLogged.setCredentialsNonExpired(true);
		userLogged.setEnabled(true);
		List<GrantedAuthority> authorities = new ArrayList<>();
		for(Role role :user.getRoles()){
			authorities.add(role);
		}
		userLogged.setAuthorities(authorities);
 

		return userLogged;
	}

}
