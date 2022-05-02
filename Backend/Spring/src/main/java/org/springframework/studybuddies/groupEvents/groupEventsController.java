package org.springframework.studybuddies.groupEvents;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
@Api(value = "Group Events Rest Controller", description = "CRUDL Group Events Controller")
class groupEventsController {

    @Autowired
    groupEventsRepository GERepository;

    private final Logger logger = LoggerFactory.getLogger(groupEventsController.class);
    
    //CRUDL
    
    //CREATE Request
    @PostMapping("/group/event/new")
    @ApiOperation(value = "createGroupEvent", notes = "Adds a new group event to the database")
    public @ResponseBody groupEvents createGroupEvent(@RequestBody groupEvents events) {
        System.out.println(events);
        GERepository.save(events);
        return events;
    }
    
    //READ Request
    @RequestMapping(method = RequestMethod.GET, path = "/group/event/{GEId}")
    @ApiOperation(value = "findGroupEventsById", notes = "Finds the group events from the database using the given ID")
    public Optional<groupEvents> findGroupEventById(@PathVariable("GEId") int id) {
        logger.info("Entered into Controller Layer");
        Optional<groupEvents> results = GERepository.findById(id);
        return results;
    }
    
  //UPDATE Request
    @PutMapping("/group/event/{GEId}")
    @ApiOperation(value = "updateGroupEvent", notes = "Updates a group event from the database using the given ID")
    public @ResponseBody groupEvents updateGroupEvent(@RequestBody groupEvents request,
    		@PathVariable("GEId") int id) {
        Optional<groupEvents> GEvent = GERepository.findById(id);
    	if(GEvent == null) return null;
    	
    	GEvent.get().setTime(request.getTime());
    	GEvent.get().setMessage(request.getMessage());
    	GEvent.get().seteventMemberId(request.geteventMemberId());
    	GEvent.get().seteventGroupId(request.geteventGroupId());
    	
    	
        System.out.println(GEvent.get());
        GERepository.save(GEvent.get());
        return GEvent.get();
    }
    
    //DELETE Request
    @DeleteMapping("/group/event/{GEId}")
    @ApiOperation(value = "deleteGroupEventById", notes = "Finds the group event from the database using the given ID and deletes it")
    public groupEvents deleteGroupEventById(@PathVariable("GEId") int id) {
        logger.info("Entered into Controller Layer");
        Optional<groupEvents> GEvent = GERepository.findById(id);
        if(GEvent == null) return null;
        
        GERepository.deleteById(id);
        
        return GEvent.get();
    }
    
    //LIST Request
    @RequestMapping(method = RequestMethod.GET, path = "/group/events")
    @ApiOperation(value = "getAllGroupEvents", notes = "Lists out all of the group events in the database")
    public List<groupEvents> getAllGroupEvents() {
        logger.info("Entered into Controller Layer");
        List<groupEvents> results = GERepository.findAll();
        logger.info("Number of Records Fetched:" + results.size());
        return results;
    }

    

}