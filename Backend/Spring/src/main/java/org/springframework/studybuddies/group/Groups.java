package org.springframework.studybuddies.group;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.springframework.core.style.ToStringCreator;

/**
 * @author Ryan Sand and Brady Heath
 */

@Entity
@Table(name = "groups")
public class Groups {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	@NotFound(action = NotFoundAction.IGNORE)
	private Integer id;

	@Column(name = "title")
	@NotFound(action = NotFoundAction.IGNORE)
	private String title;

	@Column(name = "isPublic")
	@NotFound(action = NotFoundAction.IGNORE)
	private String isPublic;



	public Groups() {

	}

	public Groups(int id, String title, String isPublic) {
		this.id = id;
		this.title = title;
		this.isPublic = isPublic;

	}

	// ID
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	// Title
	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	// isPublic
	public String getIsPublic() {
		return this.isPublic;
	}

	public void setIsPublic(String isPublic) {
		this.isPublic = isPublic;
	}

	@Override
	public String toString() {
		return new ToStringCreator(this)

				.append("id", this.getId()).append("title", this.getTitle())
				.append("isPublic", this.getIsPublic()).toString();
	}
}
