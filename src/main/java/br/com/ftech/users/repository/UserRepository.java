package br.com.ftech.users.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.ftech.users.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
	
	Page<User> findAllByOrderByNameAsc(Pageable pageable);
	
	User findByEmail(String email);
	
	User findByName(String name);
	
	@Query("Select u from User u join fetch u.roles")
	List<User> findAllUsers();
	
	@Query("Select u from User u join fetch u.roles where u.id = ?1")
	User findUser(long id);
	

}
