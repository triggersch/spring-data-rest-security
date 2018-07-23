package com.apress.hellorest.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.apress.hellorest.domain.Poll;

public interface PollRepository extends  JpaRepository<Poll, Long> {	
}
