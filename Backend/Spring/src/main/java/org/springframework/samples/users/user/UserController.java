/*
 * Copyright 2002-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.samples.users.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

//import coms309.people.Person;

import java.util.List;
//import java.util.Optional;

/**
 * @author Juergen Hoeller
 * @author Ken Krebs
 * @author Arjen Poutsma
 * @author Michael Isvy
 * @Modified By Tanmay Ghosh
 * @Modified By Vivek Bengre
 */
@RestController
class UserController {

    @Autowired
    UserRepository usersRepository;

    private final Logger logger = LoggerFactory.getLogger(UserController.class);

    /*@RequestMapping(method = RequestMethod.POST, path = "/owners/new")
    public String saveOwner(Users owner) {
        ownersRepository.save(owner);
        return "New Owner "+ owner.getFirstName() + " Saved";
    }*/
    
    @PostMapping("/users/new")
    public @ResponseBody String createUsers(@RequestBody Users user) {
        System.out.println(user);
        usersRepository.save(user);
        return "New User "+ user.getFirstName() + " Saved";
    }
    
    /*
     // function just to create dummy data
    @RequestMapping(method = RequestMethod.GET, path = "/owner/create")
    public String createDummyData() {
        Users o1 = new Users(1, "John", "Doe", "404 Not found", "some numbers");
        Users o2 = new Users(2, "Jane", "Doe", "Its a secret", "you wish");
        Users o3 = new Users(3, "Some", "Pleb", "Right next to the Library", "515-345-41213");
        Users o4 = new Users(4, "Chad", "Champion", "Reddit memes corner", "420-420-4200");
        ownersRepository.save(o1);
        ownersRepository.save(o2);
        ownersRepository.save(o3);
        ownersRepository.save(o4);
        return "Successfully created dummy data";
    }*/
    /*
 //Added function to remove dummy data
    @RequestMapping(method = RequestMethod.GET, path = "/owner/delete")
    public String deleteDummyData() {
        ownersRepository.deleteAll();
        return "Successfully deleted dummy data";
    }*/
    
    /*
    @RequestMapping(method = RequestMethod.GET, path = "/owners/delete/{ownerId}")
    public String deleteOwnerById(@PathVariable("ownerId") int id) {
        logger.info("Entered into Controller Layer");
        
        ownersRepository.deleteById(id);
        
        return "Successfully deleted Owner";
    }
    */

    @RequestMapping(method = RequestMethod.GET, path = "/users")
    public List<Users> getAllUsers() {
        logger.info("Entered into Controller Layer");
        List<Users> results = usersRepository.findAll();
        logger.info("Number of Records Fetched:" + results.size());
        return results;
    }
/*
    @RequestMapping(method = RequestMethod.GET, path = "/owners/{ownerId}")
    public Optional<Users> findOwnerById(@PathVariable("ownerId") int id) {
        logger.info("Entered into Controller Layer");
        Optional<Users> results = ownersRepository.findById(id);
        return results;
    }*/

}
