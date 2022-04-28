package org.springframework.studybuddies.announcement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.studybuddies.group.Groups;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.util.List;
import java.util.Optional;


/**
 * @author Ryan Sand and Brady Heath
 */


@RestController
@Api(value = "Announcement Rest Controller", description = "CRUDL Announcement Controller")
class AnnouncementController {

    @Autowired
    AnnouncementRepository announcementsRepository;

    private final Logger logger = LoggerFactory.getLogger(AnnouncementController.class);
    
    //CRUDL
    
    //CREATE Request
    @PostMapping("/announcements/new")
    @ApiOperation(value = "createAnnouncements", notes = "Adds a new announcement to the database")
    public @ResponseBody Announcements createAnnouncements(@RequestBody Announcements user) {
        System.out.println(announcement);
        announcementsRepository.save(announcement);
        return announcement;
    }
    
    //READ Request
    @RequestMapping(method = RequestMethod.GET, path = "/announcements/{announcementId}")
    @ApiOperation(value = "findAnnouncementById", notes = "Finds the announcement from the database using the given ID")
    public Optional<Announcements> findAnnouncementById(@PathVariable("announcementId") int id) {
        logger.info("Entered into Controller Layer");
        Optional<Announcements> results = announcementsRepository.findById(id);
        return results;
    }
    
  //UPDATE Request
    @PutMapping("/announcements/{announcementId}")
    @ApiOperation(value = "updateAnnouncements", notes = "Updates a announcement from the database using the given ID")
    public @ResponseBody Announcements updateAnnouncements(@RequestBody Announcements request,
    		@PathVariable("announcementId") int id) {
        Optional<Announcements> announcement = announcementsRepository.findById(id);
    	if(announcement == null) return null;
    	
    	announcement.get().setUsername(request.getUsername());
    	announcement.get().setPassword(request.getPassword());
    	announcement.get().setLocation(request.getLocation());
    	announcement.get().setEmail(request.getEmail());
    	
    	
        System.out.println(announcement.get());
        announcementsRepository.save(announcement.get());
        return announcement.get();
    }
    
    //DELETE Request
    @DeleteMapping("/announcements/{announcementId}")
    @ApiOperation(value = "deleteAnnouncementById", notes = "Finds the announcement from the database using the given ID and deletes it")
    public Announcements deleteAnnouncementById(@PathVariable("announcementId") int id) {
        logger.info("Entered into Controller Layer");
        Optional<Announcements> announcement = announcementsRepository.findById(id);
        if(announcement == null) return null;
        
        announcementsRepository.deleteById(id);
        
        return announcement.get();
    }
    
    //LIST Request
    @RequestMapping(method = RequestMethod.GET, path = "/announcements")
    @ApiOperation(value = "getAllAnnouncements", notes = "Lists out all of the announcements in the database")
    public List<Announcements> getAllAnnouncements() {
        logger.info("Entered into Controller Layer");
        List<Announcements> results = announcementsRepository.findAll();
        logger.info("Number of Records Fetched:" + results.size());
        return results;
    }

    

}
