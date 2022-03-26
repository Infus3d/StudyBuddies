package org.springframework.studybuddies.user;

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
@Table(name = "users")
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @NotFound(action = NotFoundAction.IGNORE)
    private Integer id;

    @Column(name = "username")
    @NotFound(action = NotFoundAction.IGNORE)
    private String username;

    @Column(name = "password")
    @NotFound(action = NotFoundAction.IGNORE)
    private String password;

    @Column(name = "email")
    @NotFound(action = NotFoundAction.IGNORE)
    private String email;
    
    @Column(name = "location")
    @NotFound(action = NotFoundAction.IGNORE)
    private String location;
    
    public Users(){
        
    }

    public Users(int id, String username, String password){
        this.id = id;
        this.username = username;
        this.password = password;
       
    }

    //ID
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    //Username
    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    //Password
    public String getPassword() {
        return this.password;
    }

    public void setLastName(String password) {
        this.password = password;
    }

    //Email
    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
    //Location
    public String getLocation() {
        return this.location;
    }

    public void setLcoation(String location) {
        this.location = location;
    }
    
    @Override
    public String toString() {
        return new ToStringCreator(this)

                .append("id", this.getId())
                .append("username", this.getUsername())
                .append("password", this.getPassword())
                .append("email", this.getEmail())
                .append("location", this.getLocation())
               .toString();
    }
}
