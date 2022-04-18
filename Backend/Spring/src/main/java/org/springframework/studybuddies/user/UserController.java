package org.springframework.studybuddies.user;

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
import java.util.List;
import java.util.Optional;


/**
 * @author Ryan Sand and Brady Heath
 */


@RestController
class UserController {

    @Autowired
    UserRepository usersRepository;

    private final Logger logger = LoggerFactory.getLogger(UserController.class);
    
    //CRUDL
    
    //CREATE Request
    @PostMapping("/users/new")
    public @ResponseBody Users createUsers(@RequestBody Users user) {
        System.out.println(user);
        usersRepository.save(user);
        return user;
    }
    
    //READ Request
    @RequestMapping(method = RequestMethod.GET, path = "/users/{userId}")
    public Optional<Users> findUserById(@PathVariable("userId") int id) {
        logger.info("Entered into Controller Layer");
        Optional<Users> results = usersRepository.findById(id);
        return results;
    }
    
  //UPDATE Request
    @PutMapping("/users/{userId}")
    public @ResponseBody Users updateUsers(@RequestBody Users request,
    		@PathVariable("userId") int id) {
        Optional<Users> user = usersRepository.findById(id);
    	if(user == null) return null;
    	
    	user.get().setUsername(request.getUsername());
    	user.get().setPassword(request.getPassword());
    	user.get().setLocation(request.getLocation());
    	user.get().setEmail(request.getEmail());
    	
    	
        System.out.println(user.get());
        usersRepository.save(user.get());
        return user.get();
    }
    
    //DELETE Request
    @DeleteMapping("/users/{userId}")
    public Users deleteUserById(@PathVariable("userId") int id) {
        logger.info("Entered into Controller Layer");
        Optional<Users> user = usersRepository.findById(id);
        if(user == null) return null;
        
        usersRepository.deleteById(id);
        
        return user.get();
    }
    
    //LIST Request
    @RequestMapping(method = RequestMethod.GET, path = "/users")
    public List<Users> getAllUsers() {
        logger.info("Entered into Controller Layer");
        List<Users> results = usersRepository.findAll();
        logger.info("Number of Records Fetched:" + results.size());
        return results;
    }

    

}
