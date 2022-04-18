package org.springframework.studybuddies.group;

import java.util.List;
import java.util.Optional;

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

/**
 * @author Ryan Sand and Brady Heath
 */

@Api(value = "Group Rest Controller", description = "CRUDL Group Controller")
@RestController
public class GroupController {

	@Autowired
    GroupRepository groupsRepository;

    private final Logger logger = LoggerFactory.getLogger(GroupController.class);
    
    //CRUDL
    
    //CREATE Request
    //@ApiOperation(value = "Add a new group to the table", response = Groups.class, tags = "postGroup")
    @PostMapping("/groups/new")
    public @ResponseBody Groups createGroups(@RequestBody Groups group) {
        System.out.println(group);
        groupsRepository.save(group);
        return group;
    }
    
    //READ Request
    @RequestMapping(method = RequestMethod.GET, path = "/groups/{groupId}")
    public Optional<Groups> findGroupById(@PathVariable("groupId") int id) {
        logger.info("Entered into Controller Layer");
        Optional<Groups> results = groupsRepository.findById(id);
        return results;
    }
    
    
    //UPDATE Request
    @PutMapping("/groups/{groupId}")
    public @ResponseBody Groups updateGroups(@RequestBody Groups request,
    		@PathVariable("groupId") int id) {
        Optional<Groups> group = groupsRepository.findById(id);
    	if(group == null) return null;
    	
    	group.get().setTitle(request.getTitle());
    	group.get().setIsPublic(request.getIsPublic());
    	
        System.out.println(group.get());
        groupsRepository.save(group.get());
        return group.get();
    }
    
    //DELETE Request
    @DeleteMapping("/groups/{groupId}")
    public Groups deleteGroupById(@PathVariable("groupId") int id) {
        logger.info("Entered into Controller Layer");
        Optional<Groups> group = groupsRepository.findById(id);
        if(group == null) return null;
        
        groupsRepository.deleteById(id);
        
        return group.get();
    }
    
    //LIST Request
    @RequestMapping(method = RequestMethod.GET, path = "/groups")
    public List<Groups> getAllGroups() {
        logger.info("Entered into Controller Layer");
        List<Groups> results = groupsRepository.findAll();
        logger.info("Number of Records Fetched:" + results.size());
        return results;
    }
    
    
    
	
}
