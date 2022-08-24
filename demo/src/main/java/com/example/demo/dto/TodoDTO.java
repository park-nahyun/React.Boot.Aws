package com.example.demo.dto;

import com.example.demo.model.TodoEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class TodoDTO {
	
	private String id;
	private String title;
	private boolean done;
	
	public TodoDTO(final TodoEntity entity) {
		this.id = entity.getId();
		this.title = entity.getTitle();
		this.done = entity.isDone();
	}
	
	// 원래는 userId를 쓰는데, 이 프로젝트는 이후 스프링 시큐리티를 이용해 인증을 구현해야 한다.
	// 따라서 사용자가 자기 아이디를 넘겨주지 않아도 인증 가능.
	// userId는 애플리케이션과 DB에서 사용자를 구별하는 고유 식별자로 사용하기 때문에
	// 숨기는 것이 보안상 맞다. 따라서 DTO에는 userId를 포함하지 않았다.
	
	// TodoDTO를 TodoEntity로 리턴하는 메소드
	public static TodoEntity toEntity(final TodoDTO dto) {
		return TodoEntity.builder()
				.id(dto.getId())
				.title(dto.getTitle())
				.done(dto.isDone())
				.build();
		
	}

}


/*
 * HTTP 응답을 반환할 때 비즈니스 로직을 캡슐화하거나 추가적인 정보를 함께 반환하기 위해 DTO를 쓴다.
 * 따라서 컨트롤러는 사용자로부터 TodoDTO를 요청 바디로 넘겨받고 이를 TodoEntity로 변환해 저장해야 한다.
 * TodoService의 create()가 리턴하는 TodoEntity를 TodoEntity를 TodoDTO로 변환해 리턴해야 한다.
 * 
 * */
