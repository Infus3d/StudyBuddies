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

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author Ryan Sand and Brady Heath
 */

@Api(value = "Permission Rest Controller", description = "CRUDL Permission Controller")
@RestController
public class PermissionController {

	@Autowired
    PermissionRepository permissionsRepository;

    private final Logger logger = LoggerFactory.getLogger(PermissionController.class);
    
    //CRUDL
    
    //CREATE Request
    @PostMapping("/permissions/new")
    @ApiOperation(value = "createPermissions", notes = "Adds a new permission to the database")
    public @ResponseBody Permissions createPermissions(@RequestBody Permissions permission) {
        System.out.println(permission);
        permissionsRepository.save(permission);
        return permission;
    }
    
    //READ Request
    @RequestMapping(method = RequestMethod.GET, path = "/permissions/{permissionId}")
    @ApiOperation(value = "findPermissionById", notes = "Finds the permission from the database using the given ID")
    public Optional<Permissions> findPermissionById(@PathVariable("permissionId") int id) {
        logger.info("Entered into Controller Layer");
        Optional<Permissions> results = permissionsRepository.findById(id);
        return results;
    }
    
    
    //UPDATE Request
    @PutMapping("/permissions/{permissionId}")
    @ApiOperation(value = "updatePermissions", notes = "Updates a permission from the database using the given ID")
    public @ResponseBody Permissions updatePermissions(@RequestBody Permissions request,
    		@PathVariable("permissionId") int id) {
        Optional<Permissions> permission = permissionsRepository.findById(id);
    	if(permission == null) return null;
    	
    	permission.get().setLevel(request.getLevel());
    	
    	
        System.out.println(permission.get());
        permissionsRepository.save(permission.get());
        return permission.get();
    }
    
    //DELETE Request
    @DeleteMapping("/permissions/{permissionId}")
    @ApiOperation(value = "deletePermissionById", notes = "Finds the permission from the database using the given ID and deletes it")
    public Permissions deletePermissionById(@PathVariable("permissionId") int id) {
        logger.info("Entered into Controller Layer");
        Optional<Permissions> permission = permissionsRepository.findById(id);
        if(permission == null) return null;
        
        permissionsRepository.deleteById(id);
        
        return permission.get();
    }
    
    //LIST Request
    @RequestMapping(method = RequestMethod.GET, path = "/permissions/")
    @ApiOperation(value = "getAllPermissions", notes = "Lists out all of the permissions in the database")
    public List<Permissions> getAllPermissions() {
        logger.info("Entered into Controller Layer");
        List<Permissions> results = permissionsRepository.findAll();
        logger.info("Number of Records Fetched:" + results.size());
        return results;
    }
    
    
    
	
}
