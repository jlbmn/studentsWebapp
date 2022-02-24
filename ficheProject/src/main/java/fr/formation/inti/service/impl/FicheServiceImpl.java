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
	public Integer likeFiche(Fiche fiche) {
		Integer like = fiche.getLike()+1;
		fiche.setLike(like);
		return like;
	}

	@Override
	public Integer dislikeFiche(Fiche fiche) {
		Integer like = fiche.getLike()-1;
		fiche.setLike(like);
		return like;
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
//		select count(*) from (select * from fiche where field="anglais") as tab ;

	}
	
	
	
}
