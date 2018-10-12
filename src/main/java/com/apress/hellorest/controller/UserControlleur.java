package com.apress.hellorest.controller;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.apress.hellorest.domain.User;
import com.apress.hellorest.repository.UserRepository;

@RestController
public class UserControlleur {
	
	@Inject
	private UserRepository userRepository;
	
	@RequestMapping(value="/polls/user", method=RequestMethod.POST)
	public ResponseEntity<?> createPolls(@Valid @RequestBody User user){
		try {//
			user = userRepository.save(user);
		} catch (Exception e) {//
			System.out.println("cuurent usr id: " + user.getId());
			e.printStackTrace();
		}//
		
		return new ResponseEntity<>(user,  HttpStatus.CREATED);
	}
	
	@RequestMapping(value="/polls/user", method=RequestMethod.GET)
	public ResponseEntity<?> getPolls(){
		List<User> users = new ArrayList<>();
		try {//
			users = userRepository.findAll();
		} catch (Exception e) {//
			System.out.println("error users");
			e.printStackTrace();
		}//
		
		return new ResponseEntity<>(users,  HttpStatus.OK);
	}

}
