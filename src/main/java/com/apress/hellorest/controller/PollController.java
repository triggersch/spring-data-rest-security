package com.apress.hellorest.controller;

import java.net.URI;

import javax.inject.Inject;
import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.apress.hellorest.domain.Poll;
import com.apress.hellorest.exception.ResourceNotFoundException;
import com.apress.hellorest.repository.PollRepository;

@RestController
public class PollController {
	
	@Inject
	private PollRepository pollRepository;
	
	@RequestMapping(value="/polls", method=RequestMethod.GET)
	public ResponseEntity<Page<Poll>> getAllPolls(Pageable pageable){
		Page<Poll> allPolls = pollRepository.findAll(pageable);
		return new ResponseEntity<>(allPolls, HttpStatus.OK);
	}
	
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	@RequestMapping(value="/polls", method=RequestMethod.POST)
	public ResponseEntity<?> createPolls(@Valid @RequestBody Poll poll){
		try {//
			poll = pollRepository.save(poll);
		} catch (Exception e) {//
			System.out.println("cuurent pool id: " + poll.getId());
			e.printStackTrace();
		}//
		
		HttpHeaders responseHeaders = new HttpHeaders();
		URI newPollUri = ServletUriComponentsBuilder
				         .fromCurrentRequest()
				         .path("/{id}")
				         .buildAndExpand(poll.getId())
				         .toUri();
		responseHeaders.setLocation(newPollUri);
		return new ResponseEntity<>(null, responseHeaders,  HttpStatus.CREATED);
	}
	
	
	@RequestMapping(value="/polls/{pollId}", method=RequestMethod.GET)
	public ResponseEntity<?> getPoll(@PathVariable Long pollId){
		Poll p = pollRepository.findById(pollId).orElse(null);
		
		if(p==null){
			throw new ResourceNotFoundException("No Poll found with id: " + pollId);
		}
		
		return new ResponseEntity<>(p, HttpStatus.OK);
	}
	
	@RequestMapping(value="/polls/{pollId}", method=RequestMethod.PUT)
	public ResponseEntity<?> updatePoll(@RequestBody Poll poll, @PathVariable Long pollId){

		verifyPoll(pollId);	
		pollRepository.save(poll);
		return new ResponseEntity<>(HttpStatus.OK);
		
	}

	@RequestMapping(value="/polls/{pollId}", method=RequestMethod.DELETE)
	public ResponseEntity<?> deletePoll(@RequestBody Poll poll, @PathVariable Long pollId){
		
		verifyPoll(pollId);
		pollRepository.delete(poll);
		return new ResponseEntity<>( HttpStatus.OK);
	}
	
	protected void verifyPoll(Long pollId) throws ResourceNotFoundException {
			
		Poll p = pollRepository.findById(pollId).orElse(null);
		
		if(p==null){
			throw new ResourceNotFoundException("No Poll found with id: " + pollId);
		}
	}

}
