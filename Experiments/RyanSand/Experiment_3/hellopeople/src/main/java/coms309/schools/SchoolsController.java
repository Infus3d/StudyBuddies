package coms309.schools;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.PathVariable;


import java.util.HashMap;

/**
 * Controller used to showcase Create and Read from a LIST
 *
 * @author Vivek Bengre
 */

@RestController
public class SchoolsController {

    // Note that there is only ONE instance of PeopleController in 
    // Springboot system.
    HashMap<String, School> schoolList = new  HashMap<>();

    //CRUDL (create/read/update/delete/list)
    // use POST, GET, PUT, DELETE, GET methods for CRUDL

    // THIS IS THE LIST OPERATION
    // gets all the people in the list and returns it in JSON format
    // This controller takes no input. 
    // Springboot automatically converts the list to JSON format 
    // in this case because of @ResponseBody
    // Note: To LIST, we use the GET method
    @GetMapping("/school")
    public @ResponseBody HashMap<String,School> getAllSchools() {
        return schoolList;
    }

    // THIS IS THE CREATE OPERATION
    // springboot automatically converts JSON input into a person object and 
    // the method below enters it into the list.
    // It returns a string message in THIS example.
    // in this case because of @ResponseBody
    // Note: To CREATE we use POST method
    @PostMapping("/school")
    public @ResponseBody String createSchool(@RequestBody School school) {
        System.out.println(school);
        schoolList.put(school.getName(), school);
        return "New school "+ school.getName() + " Saved";
    }

    // THIS IS THE READ OPERATION
    // Springboot gets the PATHVARIABLE from the URL
    // We extract the person from the HashMap.
    // springboot automatically converts Person to JSON format when we return it
    // in this case because of @ResponseBody
    // Note: To READ we use GET method
    @GetMapping("/school/{name}")
    public @ResponseBody School getSchool(@PathVariable String name) {
        School p = schoolList.get(name);
        return p;
    }

    // THIS IS THE UPDATE OPERATION
    // We extract the person from the HashMap and modify it.
    // Springboot automatically converts the Person to JSON format
    // Springboot gets the PATHVARIABLE from the URL
    // Here we are returning what we sent to the method
    // in this case because of @ResponseBody
    // Note: To UPDATE we use PUT method
    @PutMapping("/school/{name}")
    public @ResponseBody School updateSchool(@PathVariable String name, @RequestBody School p) {
        schoolList.replace(name, p);
        return schoolList.get(name);
    }

    // THIS IS THE DELETE OPERATION
    // Springboot gets the PATHVARIABLE from the URL
    // We return the entire list -- converted to JSON
    // in this case because of @ResponseBody
    // Note: To DELETE we use delete method
    
    @DeleteMapping("/school/{name}")
    public @ResponseBody HashMap<String, School> deleteSchool(@PathVariable String name) {
        schoolList.remove(name);
        return schoolList;
    }
}

