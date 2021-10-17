package com.server.Dotori;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.TimeZone;

@Slf4j
@EnableScheduling
@EnableJpaAuditing			//JPA Auditing 활성화
@SpringBootApplication
public class DotoriApplication {

	/**
	 * spring boot application server의 표준시를 한국 표준시로 설정합니다.
	 * @author 배태현
	 */
	@PostConstruct
	void timeZone() {
		TimeZone.setDefault(TimeZone.getTimeZone("Asia/Seoul"));
		log.info("Server Started At {}", new Date());
	}

	public static void main(String[] args) {
		SpringApplication.run(DotoriApplication.class, args);
	}

}
