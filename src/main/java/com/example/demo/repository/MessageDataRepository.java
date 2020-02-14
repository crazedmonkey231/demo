package com.example.demo.repository;

import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.MessageData;

@Repository
@Transactional
public interface MessageDataRepository extends CrudRepository<MessageData, Long> {
	
	MessageData findById(long id);
	
	MessageData findByPublished(Date published);
	
	MessageData findByProcessedDate(Date processedDate);
	
}
