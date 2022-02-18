package fr.formation.inti.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.formation.inti.dao.FicheDao;
import fr.formation.inti.entity.Fiche;
import fr.formation.inti.service.FicheService;

@Service
public class FicheServiceImpl implements FicheService{

	@Autowired
	private FicheDao ficheDao;
	
	@Override
	public List<Fiche> findAll() {
		return ficheDao.findAll();
	}

	@Override
	public Integer save(Fiche fiche) {
		return ficheDao.save(fiche).getFicheId();
	}

	@Override
	public void UpdateFiche(Fiche user) {
		ficheDao.save(user);
	}

	@Override
	public void deleteFiche(Fiche user) {
		ficheDao.delete(user);
	}

	@Override
	public void deleteFiche(Integer id) {
		ficheDao.deleteById(id);
	}

	@Override
	public Optional<Fiche> findById(Integer id) {
		return ficheDao.findById(id);
	}
	
	
}
