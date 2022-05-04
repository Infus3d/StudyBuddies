package org.springframework.studybuddies.personalEvents;

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
@Api(value = "Personal Events Rest Controller", description = "CRUDL Personal Events Controller")
class personalEventsController {

    @Autowired
    personalEventsRepository PERepository;

    private final Logger logger = LoggerFactory.getLogger(personalEventsController.class);
    
    //CRUDL
    
    //CREATE Request
    @PostMapping("/personal/event/new")
    @ApiOperation(value = "createPersonalEvent", notes = "Adds a new personal event to the database")
    public @ResponseBody personalEvents createPersonalEvent(@RequestBody personalEvents events) {
        System.out.println(events);
        PERepository.save(events);
        return events;
    }
    
    //READ Request
    @RequestMapping(method = RequestMethod.GET, path = "/personal/event/{PEId}")
    @ApiOperation(value = "findPersonalEventsById", notes = "Finds the personal events from the database using the given ID")
    public Optional<personalEvents> findPersonalEventById(@PathVariable("PEId") int id) {
        logger.info("Entered into Controller Layer");
        Optional<personalEvents> results = PERepository.findById(id);
        return results;
    }
    
  //UPDATE Request
    @PutMapping("/personal/event/{PEId}")
    @ApiOperation(value = "updatePersonalEvent", notes = "Updates a person event from the database using the given ID")
    public @ResponseBody personalEvents updatePersonalEvent(@RequestBody personalEvents request,
    		@PathVariable("PEId") int id) {
        Optional<personalEvents> PEvent = PERepository.findById(id);
    	if(PEvent == null) return null;
    	
    	PEvent.get().setTime(request.getTime());
    	PEvent.get().setMessage(request.getMessage());
    	PEvent.get().setUserId(request.getUserId());
    	
    	
        System.out.println(PEvent.get());
        PERepository.save(PEvent.get());
        return PEvent.get();
    }
    
    //DELETE Request
    @DeleteMapping("/personal/event/{PEId}")
    @ApiOperation(value = "deletePersonalEventById", notes = "Finds the personal event from the database using the given ID and deletes it")
    public personalEvents deletePersonalEventById(@PathVariable("PEId") int id) {
        logger.info("Entered into Controller Layer");
        Optional<personalEvents> PEvent = PERepository.findById(id);
        if(PEvent == null) return null;
        
        PERepository.deleteById(id);
        
        return PEvent.get();
    }
    
    //LIST Request
    @RequestMapping(method = RequestMethod.GET, path = "/personal/events")
    @ApiOperation(value = "getAllPersonalEvents", notes = "Lists out all of the personal events in the database")
    public List<personalEvents> getAllPersonalEvents() {
        logger.info("Entered into Controller Layer");
        List<personalEvents> results = PERepository.findAll();
        logger.info("Number of Records Fetched:" + results.size());
        return results;
    }

    

}