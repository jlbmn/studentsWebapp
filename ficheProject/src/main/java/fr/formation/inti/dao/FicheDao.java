package fr.formation.inti.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fr.formation.inti.entity.Fiche;

@Repository
public interface FicheDao extends JpaRepository<Fiche, Integer>{

}
