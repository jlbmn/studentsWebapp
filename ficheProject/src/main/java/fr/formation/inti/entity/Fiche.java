package fr.formation.inti.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="fiche")
public class Fiche {
	
	private Integer ficheId;
	private String title;
	private String field;
	private Date publicationDate;
	private Date updateDate;
	private String abstractText;
	private ArrayList<String> keywords;
	private Integer like;
	private Integer level;
	private String pdfFile;
	
	@ManyToOne
	@JoinColumn(name="id_user", nullable = false)
	private User user;
	
	@OneToMany(mappedBy="fiche", targetEntity = Comment.class)
	private Set<Comment> comments = new HashSet<Comment>();
	
	public Fiche() {
	}

	public Fiche(Integer ficheId, String title, String field, Date publicationDate, Date updateDate,
			String abstractText, ArrayList<String> keywords, Integer like, Integer level, String pdfFile, User user,
			Set<Comment> comments) {
		super();
		this.ficheId = ficheId;
		this.title = title;
		this.field = field;
		this.publicationDate = publicationDate;
		this.updateDate = updateDate;
		this.abstractText = abstractText;
		this.keywords = keywords;
		this.like = like;
		this.level = level;
		this.pdfFile = pdfFile;
		this.user = user;
		this.comments = comments;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id_fiche", unique = true, nullable = false)
	public Integer getFicheId() {
		return ficheId;
	}

	public void setFicheId(Integer ficheId) {
		this.ficheId = ficheId;
	}

	@Column(name = "title", length = 45, nullable = false)
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public Set<Comment> getComments() {
		return comments;
	}

	public void setComments(Set<Comment> comments) {
		this.comments = comments;
	}

	@Column(name = "date_publication", nullable = false)
	public Date getPublicationDate() {
		return publicationDate;
	}

	public void setPublicationDate(Date publicationDate) {
		this.publicationDate = publicationDate;
	}

	@Column(name = "date_update")
	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	@Column(name = "abstract")
	public String getAbstractText() {
		return abstractText;
	}

	public void setAbstractText(String abstractText) {
		this.abstractText = abstractText;
	}

	public ArrayList<String> getKeywords() {
		return keywords;
	}

	public void setKeywords(ArrayList<String> keywords) {
		this.keywords = keywords;
	}

	@Column(name = "like")
	public Integer getLike() {
		return like;
	}

	public void setLike(Integer like) {
		this.like = like;
	}

	@Column(name = "level", nullable = false)
	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	@Column(name = "pdf_file", nullable = false)
	public String getPdfFile() {
		return pdfFile;
	}

	public void setPdfFile(String pdfFile) {
		this.pdfFile = pdfFile;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	
	

}
