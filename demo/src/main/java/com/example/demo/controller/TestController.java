package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.ResponseDTO;
import com.example.demo.dto.TestRequestBodyDTO;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController	// 이 어노테이션을 이용하면 http와 관련된 코드 및 요청/응답 매핑을 스프링이 알아서 해준다.
@RequestMapping("test") // 리소스
public class TestController {
	
	@GetMapping
	public String testController() {
		
		return "Hello World!";
	
	}
	
	// URI 정보를 꼭 RequestMapping에 담지 않아도 된다. @GetMapping에서도 설정 가능
	// 만약 RequestMapping 어노테이션을 쓰지 않는다면 얘는 localhost:8080/testGetMapping에 연결될 것
	// 비슷한 어노테이션으로 @PostMapping @PutMapping @DeleteMapping이 있다. (HTTP 메서드의 POST, PUT, DELETE 의미)
	
	@GetMapping("/testGetMapping")
	public String testControllerWithPath() {		
		return "Hello World! testGetMapping ";		
	}
	
	
	
	/* 매개 변수를 전달 받는 법*/
	
	/* 1 */
	
	// URI의 경로로 넘어오는 값을 변수로 받는 법
	// 경로로 들어오는 임의의 숫자 또는 문자를 변수 id에 매핑하라는 뜻. 
	// id가 정수이므로 test/ 뒤에 오는 정수가 id에 매핑된다.
	// required=false는 매개변수가 꼭 필요한 것은 아니라는 뜻
	// /test/123
	@GetMapping("/{id}")	
	public String testControllerWithPathVariables(@PathVariable(required = false) int id) {
		return "Hello World! ID " + id;
	}
	
	
	/* 2 */
	
	// /test/testRequestParam?id=234
	@GetMapping("/testRequestParam")
	public String testControllerRequestParam(@RequestParam(required = false) int id) {
		return "Hello World ID " + id;
	}
	
	
	
	/* 3 */
	
	// 반환하고자 하는 리소스가 복잡할 때.. 예를 들어 기본 자료형이 아니라 오브젝트를 통째로 보내고 싶을 때
	
	//										↓ RequestBody로 보내오는 JSON을 TestRequestBodyDTO 오브젝트로 변환해 가져오라는 뜻
	@GetMapping("/testRequestBody")
	public String testControllerRequestBody(@RequestBody TestRequestBodyDTO testRequestBodyDTO) { // 즉 클라이언트는 요청 바디로 JSON 형태의 문자열을 넘겨준다.
		return "Hello World! ID " + testRequestBodyDTO.getId() + " Message : " + testRequestBodyDTO.getMessage();
	}
	
	// 클라이언트가 넘겨주는 JSON의 내부는 의미적으로 TestRequestBodyDTO와 같아야 한다.
	// 포스트맨에서 테스트 해보자..
	
	
	
	/* 4 */
	
	// 문자열보다 복잡한, 오브젝트를 리턴하고 싶을 때 @RestController
	// @RestController는 @Controller와 @ResponseBody의 조합으로 이뤄져있다.
	// @Controller - @Component로 스프링이 이 클래스의 오브젝트를 알아서 생성하고 다른 오브젝트들과의 의존성을 연결한다는 뜻
	// @ResponseBody 이 클래스의 메서드가 리턴하는 것은 웹 서비스의 ResponseBody라는 뜻
	//                → 즉, 메서드가 리턴할 때 스프링은 리턴된 오브젝트를 JSON의 형태로 바꾸고 HttpResponse에 담아 반환한다는 뜻
	@GetMapping("/testResponseBody")
	public ResponseDTO<String> testControllerResponseBody() {
		List<String> list = new ArrayList<>();
		list.add("Hello World! I'm ResponseDTO");
		ResponseDTO<String> response = ResponseDTO.<String>builder().data(list).build(); // Builder 패턴 기억..!
		return response;
	
	}
	
	
	
	/* 5 */
	
	// HTTP 응답의 바디뿐만 아니라 여러 다른 매개변수들, 예를 들어 status나 header를 조작하고 싶을 때 사용한다.
	// ResponseEntity와 ResponseDTO를 리턴하는 것은.. body에는 아무 차이가 없다. 단지 헤더와 HTTP Status를 조작할 수 있다는 점이 다르다.
	
	@GetMapping("/testResponseEntity")
	public ResponseEntity<?> testControllerResponseEntity() {
		List<String> list = new ArrayList<>();
		list.add("Hello World! I'm ResponseEntity. And you got 400!");
		ResponseDTO<String> response = ResponseDTO.<String>builder().data(list).build();
		// http status를 400으로 설정.. 이게 되는구나..!
		return ResponseEntity.badRequest().body(response);
		
	}
	

}