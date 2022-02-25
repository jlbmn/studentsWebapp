package fr.formation.inti.dao;

import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
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
		
	List<Fiche> findByUser(User user);

	@Query("SELECT count(ficheId) from Fiche f where f.field =:field")
	long countFiche(String field);
	
	@Transactional
	@Modifying
	@Query("update Fiche f set f.like = ?1 where f.ficheId =?2")
	void likeFiche( Integer like, Integer ficheId);
	
}
