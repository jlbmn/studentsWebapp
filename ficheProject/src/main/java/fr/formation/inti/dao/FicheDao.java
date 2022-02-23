package fr.formation.inti.dao;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import fr.formation.inti.entity.Fiche;
import fr.formation.inti.entity.User;

@Repository
public interface FicheDao extends JpaRepository<Fiche, Integer>{
	
	@Query("SELECT user from Fiche u")
	Set<User> listAuthors();
	
	@Query("SELECT field from Fiche u")
	Set<String> listFields();
	
}
