package org.springframework.studybuddies.announcement;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *@author Ryan Sand and Brady Heath
 */

@Repository
public interface AnnouncementRepository extends JpaRepository<Announcements, Integer> {

}
