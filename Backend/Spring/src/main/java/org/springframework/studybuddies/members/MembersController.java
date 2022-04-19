package org.springframework.studybuddies.members;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.studybuddies.user.UserRepository;
import org.springframework.studybuddies.user.Users;
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


@RestController
@Api(value = "Members Controller", description = "CRUDL Members Controller")
public class MembersController {

	@Autowired
    MembersRepository membersRepository;
	

    private final Logger logger = LoggerFactory.getLogger(MembersController.class);
    
    //CRUDL
    
    //CREATE Request
    @PostMapping("/members/new")
    @ApiOperation(value = "createMembers", notes = "Adds a new member to the database")
    public @ResponseBody MembersTable createAdmin(@RequestBody MembersTable membersNew) {
        System.out.println(membersNew);
        membersRepository.save(membersNew);
        return membersNew;
    }
    
    //READ Request
    @RequestMapping(method = RequestMethod.GET, path = "/members/{membersId}")
    @ApiOperation(value = "findMemberById", notes = "Finds the member from the database using the given ID")
    public Optional<MembersTable> findMemberById(@PathVariable("membersId") int id) {
        logger.info("Entered into Controller Layer");
        Optional<MembersTable> results = membersRepository.findById(id);
        return results;
    }
    
    
    //UPDATE Request
    @PutMapping("/members/{membersId}")
    @ApiOperation(value = "updateMembers", notes = "Updates a member from the database using the given ID")
    public @ResponseBody MembersTable updateMembers(@RequestBody MembersTable request,
    		@PathVariable("membersId") int id) {
        Optional<MembersTable> memberCheck = membersRepository.findById(id);
    	if(memberCheck == null) return null;
    	
    	memberCheck.get().setUserId(request.getUserId());
    	memberCheck.get().setPermission(request.getPermission());
    	memberCheck.get().setGroupId(request.getGroupId());
    	
        System.out.println(memberCheck.get());
        membersRepository.save(memberCheck.get());
        return memberCheck.get();
    }
    
    //DELETE Request
    @DeleteMapping("/members/{membersId}")
    @ApiOperation(value = "deleteMemberById", notes = "Finds the member from the database using the given ID and deletes it")
    public MembersTable deleteMemberById(@PathVariable("membersId") int id) {
        logger.info("Entered into Controller Layer");
        Optional<MembersTable> memberChecks = membersRepository.findById(id);
        if(memberChecks == null) return null;
        
        
        membersRepository.deleteById(id);
        
       return memberChecks.get();
    }
    
    //LIST Request
    @RequestMapping(method = RequestMethod.GET, path = "/members")
    @ApiOperation(value = "getAllMembers", notes = "Lists out all of the members in the database")
    public List<MembersTable> getAllMembers() {
        logger.info("Entered into Controller Layer");
        List<MembersTable> results = membersRepository.findAll();
        logger.info("Number of Records Fetched:" + results.size());
        return results;
    }
    
    
    
	
}
