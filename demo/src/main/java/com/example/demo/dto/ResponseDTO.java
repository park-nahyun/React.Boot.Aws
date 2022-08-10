package com.example.demo.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

// HTTP 응답으로 사용할 DTO

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ResponseDTO<T> {

	private String error;
	
	// List<T> objects가 의미하는 것은 매개 인자의 자리에 List라는 interface의 객체가 올 수 있다는 뜻
	// List<T>이므로 List가 담는 내용물을 data type 은 T타입이므로 사용자가 정의하는 data type 뭐든 올 수 있다.
	private List<T> data;
	
	// TodoDTO 뿐 아니라 다른 모델의 DTO도 ResponseDTO를 이용해 리턴할 수 있도록 자바 Generic을 사용
	// 이 프로젝트 경우 Todo를 여러 개 반환하는 경우가 많으므로 데이터를 리스트로 반환
}
