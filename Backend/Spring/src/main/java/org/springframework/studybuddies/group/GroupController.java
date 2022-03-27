
package org.springframework.studybuddies.group;

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
import java.util.List;
import java.util.Optional;


/**
 * @author Ryan Sand and Brady Heath
 */


@RestController
class GroupController {

    @Autowired
    GroupRepository groupRepository;

    private final Logger logger = LoggerFactory.getLogger(GroupController.class);
    
    //CREATE Request
    @PostMapping("/users/new")
    public @ResponseBody String createUsers(@RequestBody Group group) {
        System.out.println(group);
        groupRepository.save(group);
        return "New Group "+ group.getTitle() + " Saved";
    }
    
    //READ Request
    @RequestMapping(method = RequestMethod.GET, path = "/groups/{groupId}")
    public Optional<Group> findgroupById(@PathVariable("groupId") int id) {
        logger.info("Entered into Controller Layer");
        Optional<Group> results = groupRepository.findById(id);
        return results;
    }
    
    //LIST Request
    @RequestMapping(method = RequestMethod.GET, path = "/groups")
    public List<Group> getAllGroups() {
        logger.info("Entered into Controller Layer");
        List<Group> results = groupRepository.findAll();
        logger.info("Number of Records Fetched:" + results.size());
        return results;
    }

    

}
