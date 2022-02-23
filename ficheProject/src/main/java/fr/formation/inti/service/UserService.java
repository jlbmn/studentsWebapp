package fr.formation.inti.service;

import java.util.List;
import java.util.Optional;

import fr.formation.inti.entity.User;

public interface UserService {

	List<User> findAll();
	
	Integer save(User user);

	void UpdateUser(User user);

	void deleteUser(User user);

	void deleteUser(Integer id);

	Optional<User> findById(Integer id);
	
	Optional<User> findByEmailAndPassword(String email, String password);
}
