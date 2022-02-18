package fr.formation.inti.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fr.formation.inti.entity.Comment;

@Repository
public interface CommentDao extends JpaRepository<Comment, Integer>{

}
