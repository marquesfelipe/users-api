package br.com.ftech.users.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.ftech.users.model.User;
import br.com.ftech.users.repository.UserRepository;

@Service
@Transactional
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserRepository repository;

	public List<User> findAllUsers() {
		return repository.findAll();
	}

	public User findById(long id) {
		return repository.findOne(id);
	}

	public User findByName(String name) {	
		return repository.findByName(name);
	}

	public void saveUser(User user) {
		repository.save(user);
	}

	public void updateUser(User user) {
		repository.save(user);
	}

	public void deleteUserById(long id) {
		repository.delete(id);		
	}

	public boolean isUserExist(User user) {
		return true;
	}

	public void deleteAllUsers() {
		repository.deleteAll();
	}

	@Override
	public Page<User> findByPagination(int page, int size) {
		Pageable pageable = new PageRequest(page, size);
		
		return repository.findAllByOrderByNameAsc(pageable);
	}

}
