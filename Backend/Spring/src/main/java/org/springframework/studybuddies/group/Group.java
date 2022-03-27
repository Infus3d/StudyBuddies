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
@Table(name = "group")
public class Group {


    @Column(name = "Title")
    @NotFound(action = NotFoundAction.IGNORE)
    private String title;

    @Column(name = "Public")
    @NotFound(action = NotFoundAction.IGNORE)
    private boolean isPublic;

    //Need composite key of title and user foreign key
    
    
    public Group(){
        
    }

    public Group(String title, boolean isPublic){
       
        this.title = title;
        this.isPublic = isPublic;
       
    }

    //Title
    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    //Public
    public boolean getIsPublic() {
        return this.isPublic;
    }

    public void setIsPublic(boolean isPublic) {
        this.isPublic = isPublic;
    }

    
    
    @Override
    public String toString() {
        return new ToStringCreator(this)

                .append("title", this.getTitle())
                .append("isPublic", this.getIsPublic())
               .toString();
    }
}
