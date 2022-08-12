package com.example.demo.persistance;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.example.demo.model.TodoEntity;

@Repository
public interface TodoRepository extends JpaRepository<TodoEntity, String>{

	
	// JpaRepository 가 제공하는 기본 쿼리가 아닌 복잡한 쿼리들은 @Query 어노테이션을 사용해 지정할 수 있다.
	// ?1은 메서드 매개변수의 순서 위치다.
	@Query("select * from Todo t where t.userId = ?1")
	List<TodoEntity> findByUserId(String userId);
	
}


/*
 * JpaRepository는 인터페이스. 이 인터페이스를 사용하려면 새 인터페이스로 얘를 확장해야한다.
 * 이 때 JpaRepository<T, ID> 에서 T는 테이블에 매핑될 엔티티 클래스이고, ID는 이 엔티티의 기본 키의 타입이다.
 * 우리의 경우 TodoEntity, 그리고 TodoEntity의 기본 키인 id의 타입인 String을 넣어준다. 
 * 
 * 
 * JpaRepository 의 sava/findAll/findById 등의 메소드들이 INSERT/SELECT ALL/SELECT WHERE ~ 등의 역할을 한다고 보면 된다.
 *
 *
 * 클래스로 구현을 하지 않아도 작동하는 이유?
 * AOP의 관점을 알아야 한다.
 * 스프링은 MethodInterceptor라는 AOP 인터페이스를 사용한다.
 * 얘가 우리가 JpaRepository의 메서드를 부를 때마다 이 메서드 콜을 가로채서,
 * 가로챈 메서드의 이름을 확인하고 메서드 이름을 기반으로 쿼리를 작성한다.
 * */
 