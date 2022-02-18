package fr.formation.inti.service;

import java.util.List;
import java.util.Optional;

import fr.formation.inti.entity.Comment;

public interface CommentService {

	List<Comment> findAll();

	Integer save(Comment comment);

	void UpdateComment(Comment comment);

	void deleteComment(Comment comment);

	void deleteComment(Integer id);

	Optional<Comment> findById(Integer id);

}
