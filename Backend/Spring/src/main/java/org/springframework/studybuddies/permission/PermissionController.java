package org.springframework.studybuddies.permission;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
public class PermissionController {

	@Autowired
    PermissionRepository permissionsRepository;

    private final Logger logger = LoggerFactory.getLogger(PermissionController.class);
    
    //CRUDL
    
    //CREATE Request
    @PostMapping("/permissions/new")
    public @ResponseBody String createPermissions(@RequestBody Permissions permission) {
        System.out.println(permission);
        permissionsRepository.save(permission);
        return "New Permission "+ permission.getLevel() + " Saved";
    }
    
    //READ Request
    @RequestMapping(method = RequestMethod.GET, path = "/permissions/{permissionId}")
    public Optional<Permissions> findPermissionById(@PathVariable("permissionId") int id) {
        logger.info("Entered into Controller Layer");
        Optional<Permissions> results = permissionsRepository.findById(id);
        return results;
    }
    
    
    //UPDATE Request
    @PutMapping("/permissions/{permissionId}")
    public @ResponseBody String updatePermissions(@RequestBody Permissions request,
    		@PathVariable("permissionId") int id) {
        Optional<Permissions> permission = permissionsRepository.findById(id);
    	if(permission == null) return null;
    	
    	permission.get().setLevel(request.getLevel());
    	
    	
        System.out.println(permission.get());
        permissionsRepository.save(permission.get());
        return "Permission "+ permission.get().getLevel() + " Saved";
    }
    
    //DELETE Request
    @DeleteMapping("/permissions/{permissionId}")
    public String deletePermissionById(@PathVariable("permissionId") int id) {
        logger.info("Entered into Controller Layer");
        Optional<Permissions> permission = permissionsRepository.findById(id);
        if(permission == null) return null;
        
        permissionsRepository.deleteById(id);
        
        return "Permission " + permission.get().getLevel() + " Deleted";
    }
    
    //LIST Request
    @RequestMapping(method = RequestMethod.GET, path = "/permissions/")
    public List<Permissions> getAllPermissions() {
        logger.info("Entered into Controller Layer");
        List<Permissions> results = permissionsRepository.findAll();
        logger.info("Number of Records Fetched:" + results.size());
        return results;
    }
    
    
    
	
}
