package org.springframework.studybuddies.permission;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.springframework.core.style.ToStringCreator;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author Ryan Sand and Brady Heath
 */

@Entity
@Table(name = "permissionsTable")
public class Permissions {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	@NotFound(action = NotFoundAction.IGNORE)
	@ApiModelProperty(notes = "Autogenerated ID",name="id",required=true,value="5")
	private Integer id;

	@Column(name = "level")
	@NotFound(action = NotFoundAction.IGNORE)
	@ApiModelProperty(notes = "String that denotes the level that this permission gives a user",name="level",required=true,value="2")
	private String level;



	public Permissions() {

	}

	public Permissions(int id, String level) {
		this.id = id;
		this.level = level;

	}

	// ID
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	// Level
	public String getLevel() {
		return this.level;
	}

	public void setLevel(String level) {
		this.level = level;
	}



	@Override
	public String toString() {
		return new ToStringCreator(this)

				.append("id", this.getId()).append("level", this.getLevel())
				.toString();
	}
}
