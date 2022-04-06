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

/**
 * @author Ryan Sand and Brady Heath
 */


@RestController
public class MembersController {

	@Autowired
    MembersRepository membersRepository;
	

    private final Logger logger = LoggerFactory.getLogger(MembersController.class);
    
    //CRUDL
    
    //CREATE Request
    @PostMapping("/members/new")
    public @ResponseBody String createAdmin(@RequestBody MembersTable membersNew) {
        System.out.println(membersNew);
        membersRepository.save(membersNew);
        return "New Admin "+ membersNew.getId() + " Saved";
    }
    
    //READ Request
    @RequestMapping(method = RequestMethod.GET, path = "/members/{membersId}")
    public Optional<MembersTable> findAdminById(@PathVariable("membersId") int id) {
        logger.info("Entered into Controller Layer");
        Optional<MembersTable> results = membersRepository.findById(id);
        return results;
    }
    
    
    //UPDATE Request
    @PutMapping("/members/{membersId}")
    public @ResponseBody String updateAdmins(@RequestBody MembersTable request,
    		@PathVariable("membersId") int id) {
        Optional<MembersTable> memberCheck = membersRepository.findById(id);
    	if(memberCheck == null) return null;
    	
    	memberCheck.get().setUserId(request.getUserId());
    	memberCheck.get().setPermission(request.getPermission());
    	memberCheck.get().setGroupId(request.getGroupId());
    	
        System.out.println(memberCheck.get());
        membersRepository.save(memberCheck.get());
        return "Member "+ memberCheck.get().getId() + " Saved";
    }
    
    //DELETE Request
    @DeleteMapping("/members/{membersId}")
    public String deleteAdminById(@PathVariable("membersId") int id) {
        logger.info("Entered into Controller Layer");
        Optional<MembersTable> memberChecks = membersRepository.findById(id);
        if(memberChecks == null) return null;
        
        
        membersRepository.deleteById(id);
        
       return "Member " + memberChecks.get().getId() + " Deleted";
    }
    
    //LIST Request
    @RequestMapping(method = RequestMethod.GET, path = "/members")
    public List<MembersTable> getAllMembers() {
        logger.info("Entered into Controller Layer");
        List<MembersTable> results = membersRepository.findAll();
        logger.info("Number of Records Fetched:" + results.size());
        return results;
    }
    
    
    
	
}
