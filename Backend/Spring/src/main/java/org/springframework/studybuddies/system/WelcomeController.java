package org.springframework.studybuddies.system;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
class WelcomeController {

    @GetMapping("/")
    public String welcome() {
        return "Welcome</br> Go to localhost:8080 or coms-309-011.class.las.iastate.edu:8080 </br>";
    }
}
