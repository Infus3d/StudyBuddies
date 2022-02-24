package testing.roundtrip.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import testing.roundtrip.demo.repository.TriviaRepository;

@RestController
public class TriviaController {
	
	@Autowired
	TriviaRepository triviaRepository;
	
	
}
