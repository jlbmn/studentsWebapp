package fr.formation.inti.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "user")
public class User {

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id_user", unique = true, nullable = false)
	private Integer userId;
	@Column(name = "email", length = 45, nullable = false)
	private String email;
	@Column(name = "pseudo", length = 45, nullable = false)
	private String pseudo;
	@Column(name = "password", length = 45, nullable = false)
	private String password;
	@Column(name = "subscribe_date", nullable = false)
	private Date subscribeDate;
	@Column(name = "nb_publication")
	private Integer publicationNb;
	@Column(name = "profile_pic")
	private String profilPic;

	@OneToMany(mappedBy = "user", targetEntity = Fiche.class)
	private Set<Fiche> fiches = new HashSet<Fiche>();
	
	@OneToMany(mappedBy="user", targetEntity = Comment.class)
	private Set<Comment> comments = new HashSet<Comment>();
	
	@ManyToMany
	@JoinTable(name="user_join_role",
				joinColumns = {@JoinColumn(name="id_user")},
				inverseJoinColumns = {@JoinColumn(name="id_role")})
	private Set<Role> roles = new HashSet<Role>();

	public User() {
	}

	public User(Integer userId, String email, String pseudo, String password, Date subscribeDate, Integer publicationNb,
			String profilPic, Set<Fiche> fiches, Set<Comment> comments) {
		super();
		this.userId = userId;
		this.email = email;
		this.pseudo = pseudo;
		this.password = password;
		this.subscribeDate = subscribeDate;
		this.publicationNb = publicationNb;
		this.profilPic = profilPic;
		this.fiches = fiches;
		this.comments = comments;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPseudo() {
		return pseudo;
	}

	public void setPseudo(String pseudo) {
		this.pseudo = pseudo;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Date getSubscribeDate() {
		return subscribeDate;
	}

	public void setSubscribeDate(Date subscribeDate) {
		this.subscribeDate = subscribeDate;
	}

	public Integer getPublicationNb() {
		return publicationNb;
	}

	public void setPublicationNb(Integer publicationNb) {
		this.publicationNb = publicationNb;
	}

	public String getProfilPic() {
		return profilPic;
	}

	public void setProfilPic(String profilPic) {
		this.profilPic = profilPic;
	}

	public Set<Fiche> getFiches() {
		return fiches;
	}

	public void setFiches(Set<Fiche> fiches) {
		this.fiches = fiches;
	}

	public Set<Comment> getComments() {
		return comments;
	}

	public void setComments(Set<Comment> comments) {
		this.comments = comments;
	}

}
