package br.com.ftech.users.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.ftech.users.model.Role;
import br.com.ftech.users.model.User;
import br.com.ftech.users.repository.UserRepository;

@Service
@Transactional
public class UserServiceImpl implements UserService, UserDetailsService  {
	
	@Autowired
	private UserRepository repository;

	public List<User> findAllUsers() {
		return repository.findAllUsers();
	}

	public User findById(long id) {
		return repository.findUser(id);
	}

	public User findByName(String name) {	
		return repository.findByName(name);
	}

	public void saveUser(User user) {
		repository.save(user);
	}

	public void deleteUserById(long id) {
		repository.delete(id);		
	}

	public boolean isUserExist(User user) {
		User l_user =repository.findByEmail(user.getEmail());
		if(l_user != null){
			return true;
		}
		return false;
	}

	public void deleteAllUsers() {
		repository.deleteAll();
	}

	@Override
	public Page<User> findByPagination(int page, int size) {
		Pageable pageable = new PageRequest(page, size);		
		return repository.findAllByOrderByNameAsc(pageable);
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = repository.findByEmail(username);
		if (user == null) {
			throw new UsernameNotFoundException(username);
		}
		List<GrantedAuthority> authorities = getUserAuthority(user.getRoles());
		return buildUserForAuthentication(user, authorities);
	}
	
	private List<GrantedAuthority> getUserAuthority(Set<Role> userRoles) {
		Set<GrantedAuthority> roles = new HashSet<GrantedAuthority>();
		for (Role role : userRoles) {
			roles.add(new SimpleGrantedAuthority(role.getRole()));
		}
		List<GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority>(roles);
		return grantedAuthorities;
	}

	private UserDetails buildUserForAuthentication(User user, List<GrantedAuthority> authorities) {
		return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), user.isActive(), true, true, true, authorities);
	}

}
