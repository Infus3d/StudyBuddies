package org.springframework.studybuddies.personalEvents;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *@author Ryan Sand and Brady Heath
 */

@Repository
public interface personalEventsRepository extends JpaRepository<personalEvents, Integer> {

}
