package coms309;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
class WelcomeController {

    

    @GetMapping("/{number}")
    public String welcome(@PathVariable String number) {
    	
    	int count = 0;
    	
    	
    	for(int i = 0; i <= Integer.parseInt(number); i++) {
    		
    		if(prime(i)) count++;
    		
    	}
    	
    	
    	
        return "The number of prime numbers in " + number + " are: " + count;
    }
    
    
    static boolean prime(int p)
    {
    	//If smaller than or equal to 1
        if (p <= 1) return false;
  
        //Main checking
        for (int i = 2; i < p; i++)
            if (p % i == 0)
                return false;
    	
    	
  
        return true;
    }
    
    
    
}
