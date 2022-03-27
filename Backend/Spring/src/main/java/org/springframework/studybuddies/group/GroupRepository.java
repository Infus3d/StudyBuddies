package org.springframework.studybuddies.group;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *@author Ryan Sand and Brady Heath
 */

@Repository
public interface GroupRepository extends JpaRepository<Group, Integer> {

}
