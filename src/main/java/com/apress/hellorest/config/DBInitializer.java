package com.apress.hellorest.config;


import java.util.HashSet;
import java.util.Set;
import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apress.hellorest.domain.Option;
import com.apress.hellorest.domain.Poll;
import com.apress.hellorest.repository.PollRepository;

@Service
public class DBInitializer {
	
	@Autowired
	private PollRepository pollRepository;

	@PostConstruct
	public void initDB(){
		
		/*
		Poll poll = new Poll();
		poll.setQuestion("what is your question?");
		
		Option option1 = new Option();
		option1.setValue("no question");
		
		Option option2 = new Option();
		option2.setValue("this is my answer");
			
		Set<Option> options = new HashSet<>();
		options.add(option2);
		options.add(option1);
		
		poll.setOptions(options);
		
		pollRepository.save(poll);
		*/
	}
	

}
