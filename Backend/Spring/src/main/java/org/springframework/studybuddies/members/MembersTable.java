package org.springframework.studybuddies.members;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.springframework.core.style.ToStringCreator;
import org.springframework.studybuddies.group.Groups;
import org.springframework.studybuddies.permission.Permissions;
import org.springframework.studybuddies.user.UserRepository;
import org.springframework.studybuddies.user.Users;

/**
 * @author Ryan Sand and Brady Heath
 */

@Entity
@Table(name = "membersTable")
public class MembersTable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "memberId")
	@NotFound(action = NotFoundAction.IGNORE)
	private Integer memberId;

	@Column(name = "groupId")
	@NotFound(action = NotFoundAction.IGNORE)
	private Integer groupId;
	
	@Column(name = "userId")
	@NotFound(action = NotFoundAction.IGNORE)
	private Integer userId;
	
	@Column(name = "permission")
	@NotFound(action = NotFoundAction.IGNORE)
	private Integer permission;
	
	
	
	@ManyToOne
	@JoinColumn(name="groupId", referencedColumnName = "id", nullable=false,insertable=false, updatable=false)
	private Groups groupSave;
	
	
	@ManyToOne
	@JoinColumn(name="userId", referencedColumnName = "id", nullable=false,insertable=false, updatable=false)
	private Users userSave;
	
	@ManyToOne
	@JoinColumn(name="permission", referencedColumnName = "id", nullable=false,insertable=false, updatable=false)
	private Permissions permissionSave;
	

	public Users getMembersDetail() {
		return userSave;
	}
	
	public Permissions permissionDetail() {
		return permissionSave;
	}
	
	public Groups getGroupsDetail() {
		return groupSave;
	}
	
	public void setuserdetail(Users userDets) {
		this.userSave = userDets;
	}
	
	public MembersTable() {

	}
	
	public MembersTable(int memberId, int groupId, int userId, int permission) {
		this.memberId = memberId;
		this.userId = userId;
		this.groupId = groupId;
		this.permission = permission;

	}

	// ID
	public Integer getId() {
		return memberId;
	}

	public void setId(int memberId) {
		this.memberId = memberId;
	}
	
	public void setUserId(int usersId) {
		this.userId = usersId;
	}
	
	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}
	
	public void setPermission(int permission) {
		this.permission = permission;
	}

	public Integer getUserId() {
		return this.userId;
	}
	
	public Integer getPermission() {
		return this.permission;
	}
	
	public Integer getGroupId() {
		return this.groupId;
	}
	
//	public String getUsername() {
//		return userTemp.getUsername();
//	}
//	
//	public String getPasswd() {
//		return userTemp.getPassword();
//	}


	@Override
	public String toString() {
		return new ToStringCreator(this)
//
//				.append("admin_Id", this.getId()).append("user_Id", userTemp.getId())
//				.toString();
				
				.append("admin_Id", this.getId()).append("user_Id")
                .toString();
	}
}
