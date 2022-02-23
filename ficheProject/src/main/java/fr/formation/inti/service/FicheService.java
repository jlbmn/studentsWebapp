package fr.formation.inti.service;

import java.util.List;
import java.util.Optional;

import fr.formation.inti.entity.Fiche;

public interface FicheService {

	List<Fiche> findAll();

	Integer save(Fiche fiche);

	void UpdateFiche(Fiche fiche);

	void deleteFiche(Fiche fiche);

	void deleteFiche(Integer id);

	Optional<Fiche> findById(Integer id);
	
	Integer likeFiche(Fiche fiche);
	
	Integer dislikeFiche(Fiche fiche);
	
	List<Fiche> findByAuthor(String author);
	
	List<Fiche> findByField(String field);
}
