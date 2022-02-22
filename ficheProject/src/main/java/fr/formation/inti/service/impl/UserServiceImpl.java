package fr.formation.inti.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.formation.inti.dao.UserDao;
import fr.formation.inti.entity.User;
import fr.formation.inti.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDao userDao;

	@Override
	public List<User> findAll() {
		return userDao.findAll();
	}

	@Override
	public Integer save(User user) {
		return userDao.save(user).getUserId();
	}

	@Override
	public void UpdateUser(User user) {
		userDao.save(user);
	}

	@Override
	public void deleteUser(User user) {
		userDao.delete(user);
	}

	@Override
	public void deleteUser(Integer id) {
		userDao.deleteById(id);
	}

	@Override
	public Optional<User> findById(Integer id) {
		return userDao.findById(id);
	}
	
	@Override
	public Optional<User> findByEmailAndPassword(String email, String password) {
		return userDao.findByEmailAndPassword(email, password);
	}
}
