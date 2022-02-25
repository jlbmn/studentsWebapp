package fr.formation.inti.service.impl;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.formation.inti.dao.FicheDao;
import fr.formation.inti.entity.Fiche;
import fr.formation.inti.entity.User;
import fr.formation.inti.service.FicheService;

@Service
public class FicheServiceImpl implements FicheService{

	@PersistenceContext
	private EntityManager entityManager;
	
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

	@Override
	public List<Fiche> findByAuthor(String author) {
		String hql = "SELECT e FROM Fiche e WHERE e.user.pseudo like '%"+author+"%'";
		TypedQuery<Fiche> query = entityManager.createQuery(hql, Fiche.class);
		return query.getResultList();
	}

	@Override
	public List<Fiche> findByField(String field) {
		String hql = "SELECT e FROM Fiche e WHERE e.field like '%"+field+"%'";
		TypedQuery<Fiche> query = entityManager.createQuery(hql, Fiche.class);
		return query.getResultList();
  }
	
	@Override
	public List<Fiche> findByTitle(String title) {
		String hql = "SELECT e FROM Fiche e WHERE e.title like '%"+title+"%'";
		TypedQuery<Fiche> query = entityManager.createQuery(hql, Fiche.class);
		return query.getResultList();
  }
	
	

	@Override
	public List<Fiche> findByUser(User user) {
		return ficheDao.findByUser(user);
	}

	@Override
	public Integer getTotalLikes(User user) {
		List<Fiche> fiches = findByUser(user);
		Integer nbLikes = 0;
		if(!fiches.isEmpty()) {
			for(Fiche f:fiches) {
				nbLikes += f.getLike();
			}
		}
		return nbLikes;
	}	
	
}
