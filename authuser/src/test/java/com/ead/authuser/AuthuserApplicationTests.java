package com.ead.authuser;

import com.ead.authuser.dtos.ResponsePageDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@Log4j2
@SpringBootTest
class AuthuserApplicationTests {

	@Test
	void contextLoads() {
		String json =  "{" +
				"\"number\":\"10\"," +
				"\"totalElements\":\"2\"," +
				"\"size\":\"1\"" +
				"}";
		ObjectMapper mapper = new ObjectMapper();
		try {
			ResponsePageDto<Object> responsePageDto = mapper.readerFor(ResponsePageDto.class).readValue(json);
			log.info("Resultado : {}",responsePageDto);
		} catch (Exception e) {
			log.info("Error : {}",e.getMessage());
		}
	}

}
