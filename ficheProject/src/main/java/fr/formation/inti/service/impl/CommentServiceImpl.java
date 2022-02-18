package fr.formation.inti.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.formation.inti.dao.CommentDao;
import fr.formation.inti.entity.Comment;
import fr.formation.inti.service.CommentService;

@Service
public class CommentServiceImpl implements CommentService{
	
	@Autowired
	private CommentDao commentDao;

	@Override
	public List<Comment> findAll() {
		return commentDao.findAll();
	}

	@Override
	public Integer save(Comment comment) {
		return commentDao.save(comment).getCommentId();
	}

	@Override
	public void UpdateComment(Comment comment) {
		commentDao.save(comment);
	}

	@Override
	public void deleteComment(Comment comment) {
		commentDao.delete(comment);
	}

	@Override
	public void deleteComment(Integer id) {
		commentDao.deleteById(id);
	}

	@Override
	public Optional<Comment> findById(Integer id) {
		return commentDao.findById(id);
	}
	
	
}
