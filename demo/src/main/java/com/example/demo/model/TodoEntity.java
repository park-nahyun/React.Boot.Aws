package com.example.demo.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity(name = "Todo") // 이 클래스를 엔티티로 지정한다. 이름을 지정하고 싶다면 @Entity("이름") 이렇게..
@Table(name = "Todo") // 데이터베이스의  Todo 테이블에 매핑된다는 뜻
public class TodoEntity {

	@Id // 기본 키가 될 필드
	@GeneratedValue(generator="system-uuid") // id 필드를 오브젝트를 db에 장할 때마다 자동으로 생성되게 하는 것, 매개변수 generator로 어떤 방식으로 ID를 생성할 지 지정 가능
	@GenericGenerator(name="system-uuid", strategy = "uuid") // Hibernate가 제공하는 기본 Generator가 아니라 나만의 Generator를 사용하고 싶을 경우 이용.
	private String id; // 이 오브젝트의 아이디
	private String userId; // 이 오브젝트를 생성한 사용자의 아이디
	private String title; // Todo 타이틀(예: 운동하기)
	private boolean done; // true - todo를 완료한 경우(checked)

	
}

/*
 * GenericGenerator 종류로는 INCREMENTAL, SEQUENCE, IDENTITY 등이 있는데 우리는 문자열 형태의 UUID의 사용을 위해 커스텀 generator를 만들었다.
 * uuid 사용을 위해 generator의 매개변수 strategy로 uuid를 넘겼다.
 * 이렇게 uuid를 사용하는 system-uuid 라는 이름의 GenericGenerator를 만들었고,
 * 이 Generator는 @GeneratedValue가 참조해 사용한다.
 * */
