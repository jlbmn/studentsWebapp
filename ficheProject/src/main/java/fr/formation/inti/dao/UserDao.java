package fr.formation.inti.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fr.formation.inti.entity.User;

@Repository
public interface UserDao extends JpaRepository<User, Integer>{
	
	Optional<User> findByEmailAndPassword(String email, String password);
	
}
