package com.apress.hellorest.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.apress.hellorest.domain.Option;

public interface OptionRepository  extends JpaRepository<Option, Long> {	
}
