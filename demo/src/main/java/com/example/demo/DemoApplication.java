package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication // 스프링 부트를 설정하는 클래스
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

}



/*
 * 스프링의 중요 기능 중 하나 - 의존성 주입 컨테이너로서의 기능
 * 스프링은 베이스 패키지와 그 하위 패키지에서 자바 빈을 찾아 스프링의 의존성 주입 컨테이너 오브젝트, ApplicationContext에 등록한다.
 * 그리고 앱 실행 중 오브젝트가 필요한 경우 다른 오브젝트를 찾아 연결해준다.
 * @Autowired - 자동으로 다른 오브젝트를 찾아 연결해줌.
 * @Component - 애플리케이션 컨텍스트에 등록할 자바 빈을 설정
 * */
