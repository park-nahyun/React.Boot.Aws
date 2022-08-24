package com.example.demo.service;

import java.util.List;
import java.util.Optional;

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
	
	public List<TodoEntity> retrieve(final String userId) {
		return repository.findByUserId(userId);
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
	
	public List<TodoEntity> update(final TodoEntity entity) {
		// (1) 저장할 엔티티가 유효한지 확인한다.
		validate(entity);
		
		// (2) 넘겨받은 엔티티 id를 이용해 TodoEntity를 가져온다. 존재하지 않는 엔티티는 업데이트 할 수 없기 때문이다.
		final Optional<TodoEntity> original = repository.findById(entity.getId());
		
		if(original.isPresent()) {
		// (3) 반환된 TodoEntity가 존재하면 값을 새 entity 값으로 덮어 씌운다.
			final TodoEntity todo = original.get();
			todo.setTitle(entity.getTitle());
			todo.setDone(entity.isDone());
			
		// (4) 데이터베이스에 새 값을 저장한다.
			repository.save(todo);
		}
		
		// 2.3.3 Retrieve Todo에서 만든 메서드를 이용해 사용자의 모든 Todo 리스트를 리턴한다.
		return retrieve(entity.getUserId());
	}
	
	
	public List<TodoEntity> delete(final TodoEntity entity) {
		// (1) 저장할 엔티티가 유효한지 확인한다.
		validate(entity);
		
		try {
		// (2) 엔티티를 삭제한다.
			repository.delete(entity);
		} catch(Exception e) {
		// (3) exception 발생 시 id 와 exception을 로깅한다.
			log.error("error deleting entity", entity.getId(), e);
			
		// (4) 컨트롤러로 exception을 보낸다. 데이터베이스 내부 로직을 캡슐화하려면 e를 리턴하지 않고 새 exception 오브젝트를 리턴한다.
			throw new RuntimeException("error deleting entity" + entity.getId());
		}
		
		// (5) 새 Todo 리스트를 가져와 리턴한다.
			return retrieve(entity.getUserId());
	}

}
