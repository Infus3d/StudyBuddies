package coms309;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
class WelcomeController {

    @GetMapping("/")
    public String welcome() {
        return "Hello and welcome to COMS 309";
    }

    @GetMapping("/{name}")
    public String welcome(@PathVariable String name) {
    	return "Hello and welcome to COMS 309: " + name;
    }
    
    @GetMapping("/groups")
    //public String welcomeTest(@PathVariable String name) {
    public String welcomeTest() {
        return "Hello and welcome to COMS 309 Group 3: ";
    }
    
    @GetMapping("/{name}/groups")
    public String groupname(@PathVariable String name) {
    	return "Hello and welcome: " + name;
    }
    
    
}

