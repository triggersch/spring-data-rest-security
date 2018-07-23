package com.apress.hellorest.controller;

import javax.inject.Inject;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.apress.hellorest.domain.Vote;
import com.apress.hellorest.repository.VoteRepository;

@RestController
public class VoteController {

	@Inject
	private VoteRepository voteRepository;
	
	@RequestMapping(value="/polls/{pollId}/votes", method=RequestMethod.POST)
	public ResponseEntity<?> createVote(@PathVariable Long pollId, @RequestBody Vote vote){
		
		vote = voteRepository.save(vote);
		
		HttpHeaders respoHeaders = new HttpHeaders();
		respoHeaders.setLocation(
								ServletUriComponentsBuilder.fromCurrentRequestUri()
								.path("/{id}").buildAndExpand(vote.getId()).toUri()
				              );
		
		return new ResponseEntity<>(null, respoHeaders, HttpStatus.CREATED);	
	}
	
	
	@RequestMapping(value="/polls/{pollId}/votes", method=RequestMethod.GET)
	public Iterable<Vote> getAllVotes(@PathVariable Long pollId){
		return voteRepository.findByPoll(pollId);
	}
	
	/* testing !!!! **/
	@RequestMapping(value="/polls/votes", method=RequestMethod.GET)
	public Iterable<Vote> getAllAbsoluteVotes(){
		return voteRepository.findAll();
	}
	
}
