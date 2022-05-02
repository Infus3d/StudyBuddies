package org.springframework.studybuddies.groupEvents;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *@author Ryan Sand and Brady Heath
 */

@Repository
public interface groupEventsRepository extends JpaRepository<groupEvents, Integer> {

}
