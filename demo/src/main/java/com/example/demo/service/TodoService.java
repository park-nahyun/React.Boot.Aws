package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.TodoEntity;
import com.example.demo.persistance.TodoRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class TodoService {
	
	@Autowired
	private TodoRepository repository;
	
	public String testService() {
		// TodoEntity 생성
		TodoEntity entity = TodoEntity.builder().title("My first todo item").build();
		// TodoEntity 저장
		repository.save(entity);
		// TodoEntity 검색
		TodoEntity savedEntity = repository.findById(entity.getId()).get();
		return savedEntity.getTitle();
	}
	
	public List<TodoEntity> create(final TodoEntity entity) {
		// 1. Validations : 넘어온 엔티티가 유효한지 검사하는 로직으로, 이 부분이 커지면 클래스를 따로 분리할 수 있다.
		validate(entity);
		
		/* 리팩토링 전
		if(entity == null) {
			log.warn("Entity cannot be null.");
			throw new RuntimeException("Entity cannot be null.");
		}
		
		if(entity.getUserId() == null) {
			log.warn("Unknown user.");
			throw new RuntimeException("Unknown user.");
		}
		*/
		
		// 2. 엔티티를 데이터베이스에 저장하고 로그를 남긴다.
		repository.save(entity);
		
		log.info("Entity Id : {} is saved.", entity.getId());
		
		
		// 3. 저장된 엔티티를 포함하는 새 리스트를 리턴한다.
		return repository.findByUserId(entity.getUserId());
		
	}
	
	// validation 부분 리팩토링 - 유지 보수가 쉽게 메소드 분리
	private void validate(final TodoEntity entity) {
		if(entity == null) {
			log.warn("Entity cannot be null.");
			throw new RuntimeException("Entity cannot be null");
		}
		if(entity.getUserId() == null) {
			log.warn("Unknown user.");
			throw new RuntimeException("Unknown user.");
		}
		
	}

}
