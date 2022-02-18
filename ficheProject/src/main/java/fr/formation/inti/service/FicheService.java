package fr.formation.inti.service;

import java.util.List;
import java.util.Optional;

import fr.formation.inti.entity.Comment;
import fr.formation.inti.entity.Fiche;

public interface FicheService {

	List<Fiche> findAll();

	Integer save(Fiche user);

	void UpdateFiche(Fiche user);

	void deleteFiche(Fiche user);

	void deleteFiche(Integer id);

	Optional<Fiche> findById(Integer id);
	
}
