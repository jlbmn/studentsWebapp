package fr.formation.inti.service;

import java.util.List;
import java.util.Optional;

import fr.formation.inti.entity.Fiche;
import fr.formation.inti.entity.User;

public interface FicheService {

	List<Fiche> findAll();

	Integer save(Fiche fiche);

	void UpdateFiche(Fiche fiche);

	void deleteFiche(Fiche fiche);

	void deleteFiche(Integer id);

	Optional<Fiche> findById(Integer id);
	
	List<Fiche> findByAuthor(String author);
	
	List<Fiche> findByField(String field);
	
	List<Fiche> findByUser(User user);
	
	Integer getTotalLikes(User user);
	
	List<Fiche> findByTitle(String title);

}
