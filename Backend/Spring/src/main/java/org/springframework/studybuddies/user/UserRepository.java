package org.springframework.studybuddies.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *@author Ryan Sand and Brady Heath
 */

@Repository
public interface UserRepository extends JpaRepository<Users, Integer> {

}
