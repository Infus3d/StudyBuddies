package org.springframework.studybuddies.group;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Ryan Sand and Brady Heath
 */


@RestController
public class GroupController {

	@Autowired
    GroupRepository groupsRepository;

    private final Logger logger = LoggerFactory.getLogger(GroupController.class);
    
    //CREATE Request
    @PostMapping("/groups/new")
    public @ResponseBody String createUsers(@RequestBody Groups group) {
        System.out.println(group);
        groupsRepository.save(group);
        return "New Group "+ group.getTitle() + " Saved";
    }
    
    //READ Request
    @RequestMapping(method = RequestMethod.GET, path = "/groups/{groupId}")
    public Optional<Groups> findGroupById(@PathVariable("groupId") int id) {
        logger.info("Entered into Controller Layer");
        Optional<Groups> results = groupsRepository.findById(id);
        return results;
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
