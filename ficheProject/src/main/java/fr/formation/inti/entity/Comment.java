package fr.formation.inti.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "comment")
public class Comment {

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id_comment", unique = true, nullable = false)
	private Integer commentId;
	@Column(name = "commentaire", nullable = false)
	private String comment;
	@Column(name = "date_comment", nullable = false)
	private Date dateComment;

	@ManyToOne
	@JoinColumn(name="id_user", nullable = false)
	private User user;
	
	@ManyToOne
	@JoinColumn(name="id_fiche", nullable = false)
	private Fiche fiche;

	public Comment() {
	}

	public Comment(Integer commentId, String comment, Date dateComment, User user, Fiche fiche) {
		super();
		this.commentId = commentId;
		this.comment = comment;
		this.dateComment = dateComment;
		this.user = user;
		this.fiche = fiche;
	}

	public Integer getCommentId() {
		return commentId;
	}

	public void setCommentId(Integer commentId) {
		this.commentId = commentId;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String commeent) {
		this.comment = commeent;
	}

	public Date getDateComment() {
		return dateComment;
	}

	public void setDateComment(Date dateComment) {
		this.dateComment = dateComment;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Fiche getFiche() {
		return fiche;
	}

	public void setFiche(Fiche fiche) {
		this.fiche = fiche;
	}

}
