
package org.springframework.studybuddies.user;

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
class UserController {

    @Autowired
    UserRepository usersRepository;

    private final Logger logger = LoggerFactory.getLogger(UserController.class);
    
    @PostMapping("/users/new")
    public @ResponseBody String createUsers(@RequestBody Users user) {
        System.out.println(user);
        usersRepository.save(user);
        return "New User "+ user.getUsername() + " Saved";
    }

    @RequestMapping(method = RequestMethod.GET, path = "/users")
    public List<Users> getAllUsers() {
        logger.info("Entered into Controller Layer");
        List<Users> results = usersRepository.findAll();
        logger.info("Number of Records Fetched:" + results.size());
        return results;
    }

    @RequestMapping(method = RequestMethod.GET, path = "/users/{userId}")
    public Optional<Users> findUserById(@PathVariable("userId") int id) {
        logger.info("Entered into Controller Layer");
        Optional<Users> results = usersRepository.findById(id);
        return results;
    }

}
