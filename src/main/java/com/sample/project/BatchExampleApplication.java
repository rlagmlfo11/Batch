package com.sample.project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


// 2개의 테이블. 1개는 정보 1개는 업데이트정보. 업데이트정보를 다른 테이블에 적용시켜서 업데이트된 정보가 csv파일로 나온다
@SpringBootApplication
public class BatchExampleApplication {

	public static void main(String[] args) {
		SpringApplication.run(BatchExampleApplication.class, args);
	}

}


